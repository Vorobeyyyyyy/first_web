package com.vorobyev.fwb.tag;

import com.vorobyev.fwb.controller.WebPagePathPrepared;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PublicationPagination extends TagSupport {
    private final static Logger logger = LogManager.getLogger();
    private int currentPage;
    private int pageCount;
    private final static String PAGE_INDEX_REGEX = "page_index=\\d+";

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public int doStartTag() throws JspException {
        String contextPath = pageContext.getRequest().getServletContext().getContextPath();
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        String url = (String)(request.getAttribute("javax.servlet.forward.request_uri")) + '?' + request.getQueryString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < pageCount; i++) {
            result.append("<a class=\"pagination_page hoverable");
            if (i == currentPage) {
                result.append(" current_page");
            }
            result.append("\" href=\"");
            if (url.contains("page_index=")) {
                result.append(url.replaceAll(PAGE_INDEX_REGEX, String.format(WebPagePathPrepared.MAIN_PAGE_INDEX, i)));
            } else {
                result.append(url).append('&').append(String.format(WebPagePathPrepared.MAIN_PAGE_INDEX, i));
            }
//                    .append(WebPagePathPrepared.MAIN)
//                    .append('&')
//                    .append(String.format(WebPagePathPrepared.MAIN_PAGE_INDEX, i));

            result.append("\">").append(i).append("</a>");
        }
        try {
            pageContext.getOut().write(result.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
