package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.InboxControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "inbox", layout = AppView.class)
@PageTitle("Posteingang")
public class InboxView extends Div implements HasUrlParameter<String> {

    @Autowired
    InboxControl inboxControl;

    private static  List<MessageDTO> messages = new ArrayList<>();
    private static  Grid<MessageDTO> grid;
    private static  Div hint;
    private         SplitLayout splitLayout = new SplitLayout();

    private static final Logger LOGGER = Logger.getLogger(InboxView.class.getName());

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        try {
            if(!Objects.equals(parameter, "")) {
                this.setupGrid();
                this.refreshGrid();
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.toString());
        }
    }

    private void setupGrid() {

        grid = new Grid<>(MessageDTO.class, false);
        grid.setAllRowsVisible(true);

        // Read/Not read
        grid.addComponentColumn(message -> {
            if (!message.getRead())
                return VaadinIcon.ENVELOPE.create();
            else
                return VaadinIcon.CHECK.create();
        }).setHeader(" ").setFlexGrow(0).setWidth("50px");

        // Resolution of Sender via control class
        grid.addColumn(message -> {
            try {
                return inboxControl.getUserName(message.getSender());
            } catch (DatabaseUserException e) {
                e.printStackTrace();
            }
            return null;
        }).setHeader("Absender");

        // Resolution of Subject via control class
        grid.addColumn(message -> {
            try {
                return inboxControl.getSubject(message.getId());
            } catch (DatabaseUserException e) {
                e.printStackTrace();
            }
            return null;
        }).setHeader("Betreff");

        // Date of received message
        grid.addColumn(MessageDTO::getDate).setHeader("Datum");

        // Type of received message
        grid.addColumn(message -> {
            try {
                String type = inboxControl.getType(message.getId());
                if(Objects.equals(type, "Nachricht"))
                    return "Nachricht";
                if(Objects.equals(type, "Bewerbung"))
                    return "Bewerbung";
            } catch (DatabaseUserException e) {
                e.printStackTrace();
            }
            return null;
        }).setHeader("Nachrichtentyp");

        // Clicking a message opens it in the lower part of the window
        grid.addItemClickListener(message -> {
            if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                try {
                    toggleReply(message.getItem(), true);

                    // If needed: change envelope (read) icon
                    if(!message.getItem().getRead())
                        inboxControl.setMessageAsRead(message.getItem());
                } catch (DatabaseUserException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    toggleReply(null, false);
                } catch (DatabaseUserException e) {
                    e.printStackTrace();
                }
            }
        });

        // Fetch messages for current user and fill grid with them
        messages = inboxControl.getMessages(getCurrentUser().getId());
        grid.setItems(messages);

        // Hint if user has no messages
        hint = new Div();
        hint.setText("Sie haben keine Nachrichten.");
        hint.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");

        // Setup right side of the Layout, which works as a message Display / Answering UI
        // Hint if no message has been chosen (standard)
        Div hint2 = new Div();
        hint2.setText("Es wurde keine Nachricht ausgewählt.");
        hint2.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");

        VerticalLayout inbox = new VerticalLayout(hint, grid);
        VerticalLayout reply = new VerticalLayout(hint2);

        // Compose both sides
        splitLayout = new SplitLayout(inbox, reply);
        splitLayout.setSplitterPosition(1000);
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);

        splitLayout.setSizeFull();
        splitLayout.setHeight("100%");
        setSizeFull();
        setHeight("100%");
        add(splitLayout);
    }

    private void toggleReply(MessageDTO message, boolean selected) throws DatabaseUserException {
        if(selected)
        {
            // "Header" for message (Data that is in grid as well)
            Label sender = new Label("Absender:");
            sender.getElement().getStyle().set("font-size", "14px")
                    .set("font-weight", "bold");

            Label senderVal = new Label(inboxControl.getUserName(message.getSender()));
            senderVal.getElement().getStyle().set("font-size", "14px");

            Label subject = new Label("Betreff:");
            subject.getElement().getStyle().set("font-size", "14px")
                    .set("font-weight", "bold");

            Label subjectVal = new Label(inboxControl.getSubject(message.getId()));
            subjectVal.getElement().getStyle().set("font-size", "14px");

            Label date = new Label("Datum:");
            date.getElement().getStyle().set("font-size", "14px")
                    .set("font-weight", "bold");

            Label dateVal = new Label(message.getDate().toString());
            dateVal.getElement().getStyle().set("font-size", "14px");

            // Layout for when a message has been chosen
            Label label = new Label("Nachricht:");
            label.getElement().getStyle().set("font-size", "13px");

            // The actual message
            Paragraph messageContent = new Paragraph(message.getContent());
            messageContent.getElement().getStyle().set("background-color", "white");
            messageContent.getElement().getStyle().set("padding", "10px");
            messageContent.setWidth("80%");
            messageContent.setMinHeight("150px");

            // Visit profile button
            Button profile = new Button("Profil besuchen");
            profile.addClickListener(e ->
                    {
                        try {
                            UI.getCurrent().navigate(inboxControl.callProfileRoute(message.getSender()));
                        } catch (DatabaseUserException ex) {
                            ex.printStackTrace();
                        }
                    }
            );

            // Delete button for messages
            Button delete = new Button("Nachricht löschen");
            delete.addClickListener(e -> {
                // Preventing missclicks by opening a dialog box
                Dialog dialog   = new Dialog();
                Label question  = new Label("Sind sie sicher, dass Sie diese Nachricht löschen möchten?");
                Label info      = new Label("(Dieser Vorgang ist unwiderruflich.)");
                Button yesButton = new Button("Ja");
                Button noButton  = new Button ("Nein");

                yesButton.addClickListener(jo -> {
                    dialog.close();
                    try {
                        this.removeMessage(message);
                    } catch (DatabaseUserException ex) {
                        ex.printStackTrace();
                    }
                    splitLayout.setSplitterPosition(1000);
                    grid.deselectAll();
                    cleanSecondary();
                    if(grid.getColumns().isEmpty())
                        splitLayout.addToPrimary(hint);
                });
                noButton.addClickListener(no -> dialog.close());

                HorizontalLayout buttons = new HorizontalLayout(yesButton, noButton);
                VerticalLayout dialogContent = new VerticalLayout(question, info, buttons);
                dialogContent.setAlignItems(FlexComponent.Alignment.CENTER);
                dialog.add(dialogContent);
                dialog.open();
            });

            // Reply
            TextArea messageReply = new TextArea();
            messageReply.setLabel("Antwort:");
            messageReply.setWidth("80%");
            messageReply.setMinHeight("150px");
            messageReply.setErrorMessage("Bitte geben Sie eine Nachricht ein.");

            // Buttons for replying and cancelling
            Button replyButton = new Button("Senden");
            Button cancelButton = new Button("Verwerfen");

            replyButton.addClickListener(e -> {
                // Check if messageReply field is empty. If not, send an answer to the sender of the email
                if(!Objects.equals(messageReply.getValue(), ""))
                {
                    MessageDTO newMessage;
                    if(grid.getSelectionModel().getFirstSelectedItem().isPresent())
                    {
                        newMessage = inboxControl.prepareSending(
                                grid.getSelectionModel().getFirstSelectedItem().get(),
                                messageReply.getValue());
                        try {
                            // Send message, show confirmation, deselect content
                            inboxControl.sendMessage(newMessage);
                            messageReply.setValue("");
                            splitLayout.setSplitterPosition(1000);
                            grid.deselectAll();
                            cleanSecondary();

                            Dialog dialog = new Dialog();
                            dialog.add("Ihre Nachricht wurde gesendet!");
                            dialog.open();

                        } catch (DatabaseUserException ex) {
                            ex.printStackTrace();
                            Dialog dialog = new Dialog();
                            dialog.add("Während dem Senden der Nachricht ist ein Fehler aufgetreten. Bitte kontaktieren " +
                                    "Sie den Administrator dieser Webseite.");
                            dialog.open();
                        }
                    }
                    else
                    {
                        Dialog dialog = new Dialog();
                        dialog.add("Während dem Senden der Nachricht ist ein Fehler aufgetreten. Bitte kontaktieren " +
                                "Sie den Administrator dieser Webseite.");
                        dialog.open();
                    }
                }
                else
                    messageReply.setInvalid(true);
            });
            cancelButton.addClickListener(e -> messageReply.setValue(""));

            HorizontalLayout header = new HorizontalLayout(sender, senderVal, subject, subjectVal, date, dateVal);
            HorizontalLayout hButtons1 = new HorizontalLayout(profile, delete);
            HorizontalLayout hButtons2 = new HorizontalLayout(replyButton, cancelButton);

            VerticalLayout inboxReply = new VerticalLayout(header, label, messageContent, hButtons1,
                    messageReply, hButtons2);
            inboxReply.setWidth("100%");

            splitLayout.remove(splitLayout.getSecondaryComponent());
            splitLayout.addToSecondary(inboxReply);
        }
        else
            cleanSecondary();
    }

    // Layout when a message has been deselected
    private void cleanSecondary() {
        hint = new Div();
        hint.setText("Es wurde keine Nachricht ausgewählt.");
        hint.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");
        splitLayout.remove(splitLayout.getSecondaryComponent());
        VerticalLayout vHint = new VerticalLayout(hint);
        splitLayout.addToSecondary(vHint);
    }

    private void refreshGrid() {
        if (!(messages.isEmpty())) {
            grid.setVisible(true);
            hint.setVisible(false);
            grid.getDataProvider().refreshAll();
        } else {
            grid.setVisible(false);
            hint.setVisible(true);
        }
    }

    private void removeMessage(MessageDTO message) throws DatabaseUserException {
        if (message == null)
            return;
        messages.remove(message);
        inboxControl.deleteMessage(message);
        this.refreshGrid();
    }

    public InboxView() {

    }

    public UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
