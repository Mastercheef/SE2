package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.Component;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String MAIN_VIEW            = "";
        public static final String LOGIN_VIEW           = "login";
        public static final String MENU_VIEW            = "menu";
        public static final String REGISTER_VIEW        = "register";
        public static final String PROFILE_VIEW         = "profile";
        public static final String PROFILE_EDIT_VIEW    = "profile_edit";
        public static final String OFFER_VIEW           = "offer";
        // TODO: Restliche Seiten
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String STUDENT = "student";

    }

    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERRO = "sql";
        public static final String DATABASE = "database";
    }

}
