package com.vorobyev.fwb.controller;

import com.vorobyev.fwb.controller.command.CommandProvider;

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

    public static final String ADMIN_USERS = "/admin.do?command=show_admin_panel_users";
    public static final String ADMIN_COMMENDS = "/admin.do?command=show_admin_panel_commends";
    public static final String ADMIN_PUBLICATIONS = "/admin.do?command=show_admin_panel_publications";
    public static final String EDIT_PUBLICATION = "/publication.do?command=go_edit_publication&publication_id=%s";

    public static final String REMOVE_COMMEND = "/admin.do?command=" + CommandProvider.REMOVE_COMMEND + "&id=%s";
    public static final String REMOVE_PUBLICATION = "/admin.do?command=" + CommandProvider.REMOVE_PUBLICATION + "&id=%s";
    public static final String REMOVE_USER = "/admin.do?command=" + CommandProvider.REMOVE_USER + "&login=%s";



    private WebPagePathPrepared() {}
}
