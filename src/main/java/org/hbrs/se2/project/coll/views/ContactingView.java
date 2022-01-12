package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.AuthorizationControl;
import org.hbrs.se2.project.coll.control.ContactingControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.hbrs.se2.project.coll.util.UtilNavigation;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.logging.Logger;


@Route(value = "contacting/:userId/:companyId/:jobId", layout = AppView.class)
@RouteAlias(value = "contacting/:userId", layout = AppView.class)
@PageTitle("Kontaktaufnahme")
public class ContactingView extends VerticalLayout implements BeforeEnterObserver {

    private static final Logger LOGGER = Logger.getLogger(ContactingView.class.getName());

    @Autowired
    ContactingControl contactingControl;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthorizationControl authorizationControl;

    private int companyId = 0;
    private int jobId = 0;
    private int receiverId = 0;
    private UserDTO receiverUser;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        try {
            event.getRouteParameters().get("companyId").ifPresent((value -> companyId = Integer.parseInt(value)));
            event.getRouteParameters().get("jobId").ifPresent((value -> jobId = Integer.parseInt(value)));
            event.getRouteParameters().get("userId").ifPresent((value -> receiverId = Integer.parseInt(value)));

            if(contactingControl.checkUrlParameterInvalid(receiverId, companyId, jobId)) {
                Utils.triggerDialogMessage("Fehler", "Die übergebenen Parameter sind ungültig");
                UtilNavigation.navigateToMain();
                event.rerouteTo(Globals.Pages.MAIN_VIEW);
            } else {
                if (UtilCurrent.getCurrentUser() != null) {
                    if (!contactingControl.checkIfUserIsAllowedToSendMessage(UtilCurrent.getCurrentUser(), receiverId)) {
                        Utils.triggerDialogMessage("Zugriff Verweigert", "Sie können keine Nachricht an andere Studenten schicken");
                        UtilNavigation.navigateToMain();
                        event.rerouteTo(Globals.Pages.MAIN_VIEW);
                    }

                    receiverUser = userRepository.findUserById(receiverId);

                    initContacting();
                }
            }
        } catch (Exception exception) {
            LOGGER.info("Exception" + exception);
            Utils.triggerDialogMessage("Fehler", "Es ist ein unerwarteter Fehler aufgetreten");
            UtilNavigation.navigateToMain();
            event.rerouteTo(Globals.Pages.MAIN_VIEW);
        }
    }

    public void initContacting() {
        setSizeFull();
        TextField subjectField = new TextField();

        // Title
        H2 title;
        H4 subtitle;
        if (authorizationControl.isUserCompanyContactPerson(receiverUser, companyId)) {
            String jobTitle = contactingControl.getJobTitle(jobId);
            String companyName = contactingControl.getCompanyName(companyId);
            title = new H2("Kontaktaufnahme bezüglich \"" + jobTitle + "\"");
            subtitle = new H4("bei \"" + companyName + "\"");
            subjectField.setValue("Frage bzgl. " + jobTitle);
        } else {
            title = new H2("Nachricht an " + receiverUser.getFirstName() + " " + receiverUser.getLastName());
            subtitle = new H4();
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
                            UtilCurrent.getCurrentUser().getId(),
                            this.receiverUser.getId(),
                            subjectField.getValue(),
                            LocalDate.now()
                    );
                    Dialog dialog = new Dialog();
                    dialog.add("Ihre Nachricht wurde gesendet!");
                    dialog.open();
                    if (authorizationControl.isUserCompanyContactPerson(receiverUser, companyId))
                        UtilNavigation.navigateToCompanyProfile(companyId);
                    else
                        UtilNavigation.navigateToMain();
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
        cancelButton.addClickListener(e -> UtilNavigation.navigateToCompanyProfile(companyId));

        HorizontalLayout hbuttons = new HorizontalLayout(saveButton, cancelButton);

        add(title, subtitle, subjectField, textArea, hbuttons);

    }

    ContactingView() {
        // REQUIRED BECAUSE OF VAADIN :)
    }
}
