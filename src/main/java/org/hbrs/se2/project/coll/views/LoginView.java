package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.hbrs.se2.project.coll.control.LoginControl;
import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilNavigation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.

 */
@Route(value = "login" )
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    @Autowired
    private LoginControl loginControl;

    public LoginView() {
        setSizeFull();
        LoginForm component = new LoginForm();

        addClassName("login-view");

        component.addLoginListener(e -> {
            LoginResultDTO isAuthenticated = loginControl.authentificate( e.getUsername() , e.getPassword() );

            if (isAuthenticated.getResult()) {
                grabAndSetUserIntoSession();
                navigateToMainPage();
            } else {
                component.setError(true);
            }
        });

        add(component);

        add(new RouterLink("Sie haben noch kein Konto? Registrieren Sie sich hier!", RegistrationView.class));
        this.setAlignItems( Alignment.CENTER );
    }

    private void grabAndSetUserIntoSession() {
        UserDTO userDTO = loginControl.getCurrentUser();
        UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, userDTO );
    }

    private void navigateToMainPage() {
        UtilNavigation.navigateToMain();
    }
}