package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;
import org.hbrs.se2.project.coll.util.Globals;

@Route(value = "registration_successful" , layout = LayoutAlternative.class)
@PageTitle("Registration Successful!")
public class RegistrationSuccessfulView extends HorizontalLayout {

    // TODO: Diese Seite soll NUR angezeigt werden, wenn eine Registrierung wirklich abgeschlossen wurde.
    RegistrationSuccessfulView() {
        H1 success      = new H1("Registrierung erfolgreich!");
        H3 info         = new H3("Sie können sich jetzt mit Ihrer E-Mail-Adresse einloggen.");

        Button login    = new Button("Login");
        login.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW));

        VerticalLayout finalLayout = new VerticalLayout(success, info, login);
        setSizeFull();
        add(finalLayout);
    }

}
