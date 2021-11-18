package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.Component;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String LOGIN_VIEW = "login";
        public static final String MAIN_VIEW = "";
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
