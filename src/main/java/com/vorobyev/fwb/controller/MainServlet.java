package com.vorobyev.fwb.controller;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.command.CommandProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "mainServlet", urlPatterns = {"*.do"})
public class MainServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private static final String COMMAND = "command";

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.INFO, "New GET (Url = {})", request.getRequestURI());
        String commandId = request.getParameter(COMMAND);
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandId);

        if (commandOptional.isEmpty()) {
            logger.log(Level.ERROR, "wrong command ({})", commandId);
            response.sendRedirect(request.getContextPath());
            return;
        }

        Command command = commandOptional.get();
        String page = command.preform(request, response);
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    public void destroy() {
    }
}