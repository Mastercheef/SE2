package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.Component;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String MAIN_VIEW                    = "";
        public static final String LOGIN_VIEW                   = "login/";
        public static final String MENU_VIEW                    = "main/";
        public static final String REGISTER_VIEW                = "register/";
        public static final String REGISTRATION_SUCCESSFUL      = "registration_successful/";
        public static final String PROFILE_VIEW                 = "profile/";            // Student
        public static final String PROFILE_EDIT_VIEW            = "profile_edit/";
        public static final String COMPANYPROFILE_VIEW          = "companyprofile/";     // Company
        public static final String COMPANYPROFILE_EDIT_VIEW     = "companyprofile_edit/";
        public static final String OFFER_VIEW                   = "offer/";
        public static final String RECRUITMENT_VIEW             = "recruitment_formular/";
        // TODO: Restliche Seiten
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String STUDENT = "student";

    }

    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

}
