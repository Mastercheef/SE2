package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.util.Globals;


@Route(value = "" )
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {

    public MainView() {
        Button button = new Button("Click me",
                event -> Notification.show("Clicked!"));
        add(button);

        Button profileButton = new Button("Profile");
        profileButton.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW);
        });
        add(profileButton);
    }
}
