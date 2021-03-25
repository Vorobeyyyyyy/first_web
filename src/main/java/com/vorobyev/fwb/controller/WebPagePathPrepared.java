package com.vorobyev.fwb.controller;

public class WebPagePathPrepared {
    public static final String GO_CREATE_PUBLICATION = "/publication.do?command=go_create_publication";
    public static final String PUBLICATION_WITH_ID = "/publication.do?command=go_to_publication&publication_id=%s";
    public static final String TAKE_FILE = "/file.do?command=take_file&file_name=%s";

    public static final String MAIN = "/main.do?command=show_publications";
    public static final String MAIN_PAGE_INDEX = "page_index=%s";
    public static final String MAIN_PUBLISHER = "publisher=%s";
    public static final String MAIN_SEARCH = "search=%s";
    @Deprecated
    public static final String PROFILE = "/profile.do?command=go_to_profile";
    public static final String SHOW_PROFILE = "/profile.do?command=show_profile&username=%s";
    public static final String LOGIN = "/login.do?command=go_to_login";
    public static final String REGISTER = "/register.do?command=go_to_register";

    private WebPagePathPrepared() {}
}
