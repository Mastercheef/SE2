package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.UI;

import java.util.Objects;
public class UtilNavigation {

    private UtilNavigation() {
        throw new IllegalStateException("Utility Class");
    }



    // Navigation Methods
    public static void navigateToRegistration() {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.REGISTER_VIEW))
            UI.getCurrent().navigate(Globals.Pages.REGISTER_VIEW);
    }

    public static void navigateToStudentProfile(int studentId) {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.PROFILE_VIEW + studentId))
            UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + studentId);
    }

    public static void navigateToCompanyProfile(int companyId) {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.COMPANYPROFILE_VIEW + companyId))
            UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + companyId);
    }

    public static void navigateToMain() {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.MAIN_VIEW))
            UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
    }

    public static void navigateToLogin() {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.LOGIN_VIEW))
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
    }

    public static void navigateToContactFormular(int companyId, int jobId) {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.CONTACTING_VIEW))
            UI.getCurrent().navigate(Globals.Pages.CONTACTING_VIEW + companyId + "/" + jobId);
    }

    public static void navigateToJobList() {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.JOBLIST_VIEW))
            UI.getCurrent().navigate(Globals.Pages.JOBLIST_VIEW);
    }

    public static void navigateToJobList(String keyword) {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.JOBLIST_VIEW))
            UI.getCurrent().navigate(Globals.Pages.JOBLIST_VIEW + keyword);
    }

    public static void navigateToJobList(String keyword, String type) {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.JOBLIST_VIEW))
            UI.getCurrent().navigate(Globals.Pages.JOBLIST_VIEW + keyword + "/" + type);
    }

    public static void navigateToDashboard() {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.DASHBOARD_VIEW))
            UI.getCurrent().navigate(Globals.Pages.DASHBOARD_VIEW);
    }

    public static void navigateToMessages(int userId) {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.INBOX_VIEW + userId))
            UI.getCurrent().navigate(Globals.Pages.INBOX_VIEW + userId);
    }

    public static void navigateToSettings() {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.SETTINGS_VIEW))
            UI.getCurrent().navigate(Globals.Pages.SETTINGS_VIEW);
    }

}