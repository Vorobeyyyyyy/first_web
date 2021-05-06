package com.vorobyev.fwb.controller;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.command.CommandProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "mainServlet", urlPatterns = {"*.do"})
public class MainController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private static final String COMMAND = "command";

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.INFO, "New GET (Url = {})", request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        String commandId = request.getParameter(COMMAND);
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandId);

        if (commandOptional.isEmpty()) {
            logger.log(Level.ERROR, "wrong command ({})", commandId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.getRequestDispatcher(WebPagePath.ERROR).forward(request, response);
            return;
        }

        Command command = commandOptional.get();
        logger.log(Level.INFO, "Command: {}", command);
        String page = command.perform(request, response);

        if (page.isEmpty()) {
            return;
        }

        if (page.indexOf(WebPagePath.REDIRECT) != 0) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            page = page.substring(WebPagePath.REDIRECT.length());
            logger.log(Level.INFO, "Redirected to {}", page);
            response.sendRedirect(request.getContextPath() + page);
        }
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