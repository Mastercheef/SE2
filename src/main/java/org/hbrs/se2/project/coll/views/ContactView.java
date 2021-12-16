package org.hbrs.se2.project.coll.views;


import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.layout.AppView;

@Route(value = "kontakt", layout = AppView.class)
@PageTitle("Kontakt")
public class ContactView extends HorizontalLayout {

    public ContactView() {
        //Required for Vaadin
    }
}
