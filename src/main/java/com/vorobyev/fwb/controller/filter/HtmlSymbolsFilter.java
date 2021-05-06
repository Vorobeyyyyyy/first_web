package com.vorobyev.fwb.controller.filter;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.do"})
public class HtmlSymbolsFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    static class FilteredRequest extends HttpServletRequestWrapper {
        public FilteredRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String s = super.getParameter(name);
            logger.log(Level.INFO, s);
            s = StringEscapeUtils.escapeHtml4(s);
            logger.log(Level.INFO, s);
            return s;
        }
    }

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(ENCODING);
        filterChain.doFilter(new FilteredRequest((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {
    }
}
