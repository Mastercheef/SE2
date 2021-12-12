package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.ContactingControl;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Kontaktaufnahme")
@Route(value = "contacting/:companyId/:jobId", layout = AppView.class)
public class ContactingView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    ContactingControl contactingControl;

    private String companyId = null;
    private String jobId = null;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().get("companyId").ifPresent((value -> companyId = value));
        event.getRouteParameters().get("jobId").ifPresent((value -> jobId = value));
        initContacting();
    }

    public void initContacting() {
        setSizeFull();

        // Title
        H2 title = new H2("Kontaktaufnahme bezüglich \"" +
                contactingControl.getJobTitle(Integer.parseInt(jobId)) + "\"");
        H4 subtitle = new H4("bei \"" +
                contactingControl.getCompanyName(Integer.parseInt(companyId)) + "\"");
        subtitle.getElement().getStyle().set("margin-top", "-12px");

        // Textfield
        TextArea textArea = new TextArea();
        textArea.setWidthFull();
        textArea.setLabel("Geben Sie hier ihre Nachricht ein:");
        textArea.setMinHeight("250px");
        textArea.setMinLength(100);
        textArea.setRequired(true);
        textArea.setErrorMessage("Bitte geben Sie eine Nachricht mit mindestens 100 Zeichen ein.");

        // Create buttons to send message and cancel progress
        Button saveButton   = new Button("Senden");
        Button cancelButton = new Button("Abbrechen");
        saveButton.addClickListener(e -> {
            if(textArea.getValue().length() >= textArea.getMinLength()) {
                Dialog dialog = new Dialog();
                dialog.add("Ihre Nachricht wurde versandt!");
                dialog.open();
                UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + companyId);
            }
            else
                textArea.setInvalid(true);
        });
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW +
                companyId));

        HorizontalLayout hbuttons = new HorizontalLayout(saveButton, cancelButton);

        add(title, subtitle, textArea, hbuttons);

    }

    ContactingView() {
        // REQUIRED BECAUSE OF VAADIN :)
    }
}
