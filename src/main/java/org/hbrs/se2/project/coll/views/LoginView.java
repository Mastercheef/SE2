package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.LoginControl;
import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.util.Globals;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 * ToDo: Integration einer Seite zur Registrierung von Benutzern
 */
@Route(value = "login" )
public class LoginView extends VerticalLayout {

    private LoginControl loginControl;

    public LoginView() {
        setSizeFull();
        LoginForm component = new LoginForm();

        component.addLoginListener(e -> {
            LoginResultDTO isAuthenticated = loginControl.authentificate( e.getUsername() , e.getPassword() );

            if (isAuthenticated.getResult()) {
                grabAndSetUserIntoSession();
                navigateToMainPage();
            } else {
                Dialog dialog = new Dialog();
                dialog.add(new Text( isAuthenticated.getReason()));
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
        });

        add(component);
        this.setAlignItems( Alignment.CENTER );
    }

    private void grabAndSetUserIntoSession() {
        UserDTO userDTO = loginControl.getCurrentUser();
        UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, userDTO );
    }


    private void navigateToMainPage() {
        // Navigation zur Startseite, hier auf die Teil-Komponente Show-Cars.
        // Die anzuzeigende Teil-Komponente kann man noch individualisieren, je nach Rolle,
        // die ein Benutzer besitzt
        UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);

    }
}
