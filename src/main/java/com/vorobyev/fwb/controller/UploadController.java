package com.vorobyev.fwb.controller;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.command.CommandProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "uploadServlet", urlPatterns = {"*.up"})
public class UploadController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String COMMAND = "command";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "New UPLOAD POST (Url = {})", request.getRequestURL() + "?" + request.getQueryString());

        String commandString = request.getParameter(COMMAND);

        Optional<Command> commandOptional = CommandProvider.defineCommand(commandString);

        if (commandOptional.isEmpty()) {
            logger.log(Level.WARN, "Command {} not defined", commandString);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.getRequestDispatcher(WebPagePath.ERROR).forward(request, response);
            return;
        }

        Command command = commandOptional.get();
        String page =  command.preform(request, response);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
