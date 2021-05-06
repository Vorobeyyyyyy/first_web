package com.vorobyev.fwb.controller.filter;

import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.command.CommandProvider;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.model.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {"*.do"})
public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final String COMMAND = "command";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String commandId = request.getParameter(COMMAND);
        User user = (User) request.getSession().getAttribute(SessionAttributeName.USER);
        UserRole role = user != null ? user.getRole() : UserRole.GUEST;
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandId);
        if (commandOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        Command command = commandOptional.get();
        logger.log(Level.INFO, "role = {}, access = {}", role, command.getAllowedAccessLevels());

        if (command.getAllowedAccessLevels().contains(role)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.getRequestDispatcher(WebPagePath.ERROR).forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
