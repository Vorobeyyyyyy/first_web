package com.vorobyev.fwb.util.tag;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.text.Format;


public class ReferenceTag extends TagSupport {
    protected String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.write(pageContext.getRequest().getServletContext().getContextPath());
            writer.write(path);
        } catch (IOException exception) {
            throw new JspException(exception);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
