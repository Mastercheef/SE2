package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.UI;
import org.hbrs.se2.project.coll.Generated;

import java.util.Objects;
@Generated
public class UtilNavigation {
    // Navigation Methods

    public static void navigateToCompanyProfile(int companyId) {
        if (!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.COMPANYPROFILE_VIEW))
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
}