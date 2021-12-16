package org.hbrs.se2.project.coll.layout;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.coll.util.Globals;

import java.util.Objects;

@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainLayout extends Div implements RouterLayout {

    // Header Variables
    private Label collathbrs   = new Label("Coll@HBRS");
    private Label logout       = new Label("Logout");

    // Footer Variables
    private Label copyright = new Label("Copyright © 2021-2022");
    private Label contact   = new Label("Kontakt");
    private Label about     = new Label("Impressum");
    private Label dataProt  = new Label("Datenschutz");

    //Component to delegate content through.
    private Div content = new Div();

    private final VerticalLayout layout = new VerticalLayout();

    public MainLayout() {

        // Misc. Styling
        //logout.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW));
        logout.getElement().getStyle().set("margin-left", "auto");
        getElement().getStyle().set(Globals.View.BACKGROUND_COLOR, "#fffdeb");
        setSizeFull();

        // Header
        HorizontalLayout header = new HorizontalLayout(collathbrs, logout);
        header.setAlignItems(FlexComponent.Alignment.END);
        header.setWidth("100%");
        header.setPadding(true);
        //header.setHeight("150px");
        header.getElement().getStyle().set("color", "white");
        header.getElement().getStyle().set(Globals.View.BACKGROUND_COLOR, "#233348");
        header.getElement().getStyle().set("font-size", "24px");
        header.getElement().getStyle().set("font-weight", "bold");
        header.getElement().getStyle().set("clear", "both");
        header.getElement().getStyle().set("top", "0");
        header.getElement().getStyle().set("left", "0");
        header.getElement().getStyle().set("margin-top", "-10px");
        header.getElement().getStyle().set("position", "fixed");

        // Footer
        // TODO: Add links to each label component (copyrightview, contactview, ... )
        HorizontalLayout footer = new HorizontalLayout(copyright, contact, about, dataProt);
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        footer.setWidth("100%");
        footer.setPadding(true);
        footer.getElement().getStyle().set(Globals.View.BACKGROUND_COLOR, "#233348");
        footer.getElement().getStyle().set("color", "white");
        footer.getElement().getStyle().set("clear", "both");
        footer.getElement().getStyle().set("bottom", "0");
        footer.getElement().getStyle().set("left", "0");
        footer.getElement().getStyle().set("position", "fixed");

        // Append to main layout
        layout.add(header, content, footer);
        layout.setSizeFull();
        add(layout);
    }

    @Override
    public void showRouterLayoutContent(HasElement hasElement) {
        Objects.requireNonNull(hasElement);
        Objects.requireNonNull(hasElement.getElement());
        content.removeAll();
        content.getElement().appendChild(hasElement.getElement());
    }
}