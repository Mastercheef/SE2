package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.UI;
import org.hbrs.se2.project.coll.dtos.UserDTO;


public class UtilCurrent {// Getters

    public static String getCurrentLocation() {
        return UI.getCurrent().getInternals().getActiveViewLocation().getPath();
    }

    public static UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}