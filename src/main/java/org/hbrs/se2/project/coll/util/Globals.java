package org.hbrs.se2.project.coll.util;


import com.vaadin.flow.component.html.H2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Globals {

    private Globals() {
        throw new IllegalStateException(ExceptionMessage.UTILITY);
    }
    public static final String CURRENT_USER = "current_User";

    public static class Pages {
        private Pages() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }
        public static final String APPLICATION_VIEW             = "apply/";
        public static final String COMPANYPROFILE_VIEW          = "companyprofile/";     // Company
        public static final String COMPANYPROFILE_EDIT_VIEW     = "companyprofile_edit/";
        public static final String CONTACTING_VIEW              = "contacting/";
        public static final String INBOX_VIEW                   = "inbox/";
        public static final String JOBADVERTISEMENT_VIEW        = "jobadvertisement/";
        public static final String JOBAPPLICATION_VIEW          = "jobapplication/";
        public static final String JOBLIST_VIEW                 = "joblist/";
        public static final String LOGIN_VIEW                   = "login/";
        public static final String MAIN_VIEW                    = "";
        public static final String MENU_VIEW                    = "main/";
        public static final String PROFILE_VIEW                 = "profile/";            // Student
        public static final String PROFILE_EDIT_VIEW            = "profile_edit/";
        public static final String RECRUITMENT_VIEW             = "recruitment_formular/";
        public static final String REGISTER_VIEW                = "register/";
        public static final String SETTINGS_VIEW                = "settings/";
    }

    public static class PageTitles {
        private PageTitles() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }
        public static final String APPLICATION_PAGE_TITLE       = "Application";
        public static final String JOBADVERTISEMENT_PAGE_TITLE  = "Stellenangebot";
        public static final String JOBLIST_PAGE_TITLE           = "Liste der Stellenangebote";
        public static final String LOGIN_PAGE_TITLE             = "Login";
        public static final String MAIN_PAGE_TITLE              = "Main";
        public static final String REGISTER_PAGE_TITLE          = "Registration";
    }

    public static class LogMessage {
        private LogMessage() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }

        public static final String LOG          = "LOG: {}";
        public static final String CONNECTED = "Während der Verbindung zur Datenbank mit JPA ist \" +\n" +
                "                        \"ein Fehler aufgetreten.";
        public static final String ERROR              = "Es ist ein unerwarteter Fehler aufgetreten.";
    }

    public static class ExceptionMessage {
        private ExceptionMessage() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }
        public static final String UTILITY = "Utility Class";
    }

    public static class View {
        private View() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }
        public static final String BACKGROUND_COLOR = "background-color";
        public static final String COMPANY = "Unternehmen";
        public static final String ERROR = "Fehler";
        public static final String POSTAL_CODE = "Bitte geben Sie eine Postleitzahl ein";

    }


    public static class Regex {

        private Regex() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }

        // REGEX for input validation
        private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        public static boolean validateEmailInput(String email) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return matcher.find();
        }

        private static final Pattern VALID_NAME_REGEX =
                Pattern.compile("^[a-zA-ZßäöüÄÖÜ ,.'-]+$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

        public static boolean validateNameInput(String name) {
            Matcher matcher = VALID_NAME_REGEX.matcher(name);
            return matcher.find();
        }
    }

    public static class Roles {
        private Roles () {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }
        public static final String ADMIN = "admin";
        public static final String STUDENT = "student";

    }

    public static class Errors {
        private Errors() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

    public static class Countries {
        private Countries() {
            throw new IllegalStateException(ExceptionMessage.UTILITY);
        }
        private static List<String> countryNames = new ArrayList<>();
        public static final List<String> getCountries() {
            for (String countryCode : Locale.getISOCountries()) {
                Locale obj = new Locale("", countryCode);
                countryNames.add(obj.getDisplayCountry());
            }
            return countryNames;
        }
    }

}
