package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.ContactingControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.User;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;


@Route(value = "contacting/:companyId/:jobId", layout = AppView.class)
@RouteAlias(value = "contacting/:userId", layout = AppView.class)
@PageTitle("Kontaktaufnahme")
public class ContactingView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    ContactingControl contactingControl;
    @Autowired
    UserRepository userRepository;

    private String companyId = null;
    private String jobId = null;
    private int userId;
    private UserDTO receiverUser;
    private int contactPersonId;
    private int receiver = 0;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        try {
            event.getRouteParameters().get("companyId").ifPresent((value -> companyId = value));
            event.getRouteParameters().get("jobId").ifPresent((value -> jobId = value));
            event.getRouteParameters().get("userId").ifPresent((value -> userId = Integer.parseInt(value)));

            if(companyId == null && jobId == null && userId < 1 || companyId != null && jobId == null && userId < 1 ||
                    companyId == null && jobId != null && userId < 1) {
                Utils.triggerDialogMessage("Fehler", "Die übergebenen Parameter sind ungültig");
                UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
                event.rerouteTo(Globals.Pages.MAIN_VIEW);
            } else {
                if (getCurrentUser() != null) {
                    if (!getCurrentUser().getType().equals("cp")) {
                        Utils.triggerDialogMessage("Zugriff Verweigert", "Sie können keine Nachricht an andere Studenten schicken");
                        UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
                        event.rerouteTo(Globals.Pages.MAIN_VIEW);
                    }

                    if (companyId != null && jobId != null)
                        receiver = contactingControl.getContactPerson(Integer.parseInt(companyId));
                    else {
                        receiver = userId;
                        receiverUser = userRepository.findUserById(userId);
                    }
                    initContacting();
                }
            }
        } catch (Exception exception) {
            System.out.println("Exception" + exception);
            Utils.triggerDialogMessage("Fehler", "Es ist ein unerwarteter Fehler aufgetreten");
            UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
            event.rerouteTo(Globals.Pages.MAIN_VIEW);
        }
    }

    private H2 title;
    private H4 subtitle;

    public void initContacting() {
        setSizeFull();
        TextField subjectField = new TextField();

        // Title
        if (checkIfContactingUser()) {
            title = new H2("Nachricht an " + receiverUser.getFirstName() + " " + receiverUser.getLastName());
            subtitle = new H4();
        } else {
            String jobTitle = contactingControl.getJobTitle(Integer.parseInt(jobId));
            String companyName = contactingControl.getCompanyName(Integer.parseInt(companyId));
            title = new H2("Kontaktaufnahme bezüglich \"" + jobTitle + "\"");
            subtitle = new H4("bei \"" + companyName + "\"");
            subjectField.setValue("Frage bzgl. " + jobTitle);
        }
        subtitle.getElement().getStyle().set("margin-top", "-12px");

        // Subject
        subjectField.setLabel("Betreff");
        subjectField.setMinWidth("300px");
        subjectField.setRequired(true);
        subjectField.setErrorMessage("Bitte geben Sie einen Betreff ein.");

        // Message
        TextArea textArea = new TextArea();
        textArea.setWidthFull();
        textArea.setLabel("Geben Sie hier ihre Nachricht ein:");
        textArea.setMinHeight("250px");
        textArea.setRequired(true);
        textArea.setErrorMessage("Bitte geben Sie eine Nachricht ein.");
        textArea.setMinLength(1);

        // Create buttons to send message and cancel progress
        Button saveButton   = new Button("Senden");
        Button cancelButton = new Button("Abbrechen");
        saveButton.addClickListener(e -> {
            //String content, int sender, int recipient, int subject
            if(textArea.getValue().length() >= textArea.getMinLength())
                try {
                    contactingControl.sendMessage(
                            textArea.getValue(),
                            this.getCurrentUser().getId(),
                            receiver,
                            subjectField.getValue(),
                            LocalDate.now()
                    );
                    Dialog dialog = new Dialog();
                    dialog.add("Ihre Nachricht wurde gesendet!");
                    dialog.open();
                    if (checkIfContactingUser())
                        UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
                    else
                        UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + companyId);
                } catch (DatabaseUserException ex) {
                    ex.printStackTrace();
                    Dialog dialog = new Dialog();
                    dialog.add("Während dem Senden der Nachricht ist ein Fehler aufgetreten. Bitte kontaktieren " +
                            "Sie den Administrator dieser Webseite.");
                    dialog.open();
                }
            else
                textArea.setInvalid(true);

        });
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW +
                companyId));

        HorizontalLayout hbuttons = new HorizontalLayout(saveButton, cancelButton);

        add(title, subtitle, subjectField, textArea, hbuttons);

    }

    public UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    public boolean checkIfContactingUser() {
        return userId > 0;
    }

    ContactingView() {
        // REQUIRED BECAUSE OF VAADIN :)
    }
}
