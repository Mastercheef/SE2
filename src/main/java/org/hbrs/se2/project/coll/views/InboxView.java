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
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Utils;
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

    private static final Grid<MessageDTO> grid = new Grid<>(MessageDTO.class, false);
    private static  List<MessageDTO> messages  = new ArrayList<>();
    private static  Div hint;
    private static  SplitLayout splitLayout = new SplitLayout();

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
        }).setHeader("Absender").setSortable(true);

        // Resolution of Subject via control class
        grid.addColumn(message -> {
            try {
                return inboxControl.getSubject(message.getId());
            } catch (DatabaseUserException e) {
                e.printStackTrace();
            }
            return null;
        }).setHeader("Betreff").setSortable(true);

        // Date of received message
        grid.addColumn(MessageDTO::getDate).setHeader("Datum").setSortable(true);

        // Clicking a message opens it in the lower part of the window
        grid.addItemClickListener(message -> {
            try {
                if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                    toggleReply(message.getItem(), true);
                    if(!message.getItem().getRead())
                        inboxControl.setMessageAsRead(message.getItem());
                }
                else
                    toggleReply(null, false);

            } catch(DatabaseUserException e) {
                e.printStackTrace();
            }
        });

        // Fetch messages for current user and fill grid with them
        InboxView.messages = inboxControl.getMessages(Utils.getCurrentUser().getId());
        grid.setItems(messages);

        // Hint if user has no messages
        InboxView.hint = new Div();
        InboxView.hint.setText("Sie haben keine Nachrichten.");
        setDivStyle(hint);

        // Setup right side of the Layout, which works as a message Display / Answering UI
        // Hint if no message has been chosen (standard)
        Div hint2 = new Div();
        hint2.setText("Es wurde keine Nachricht ausgewählt.");
        setDivStyle(hint2);

        VerticalLayout inbox = new VerticalLayout(hint, grid);
        VerticalLayout reply = new VerticalLayout(hint2);

        // Compose both sides
        InboxView.splitLayout = new SplitLayout(inbox, reply);
        InboxView.splitLayout.setSplitterPosition(1000);
        InboxView.splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);

        InboxView.splitLayout.setSizeFull();
        InboxView.splitLayout.setHeight("100%");
        setSizeFull();
        setHeight("100%");
        add(InboxView.splitLayout);
    }

    private void toggleReply(MessageDTO message, boolean selected) throws DatabaseUserException {
        if(selected)
        {
            // "Header" for message (Data that is in grid as well)
            Label sender = new Label("Absender:");
            Label senderVal = new Label(inboxControl.getUserName(message.getSender()));
            Label subject = new Label("Betreff:");
            Label subjectVal = new Label(inboxControl.getSubject(message.getId()));
            Label date = new Label("Datum:");
            Label dateVal = new Label(message.getDate().toString());
            Label mess = new Label("Nachricht:");

            styling(sender, senderVal, subject, subjectVal, date, dateVal, mess);

            // Layout for when a message has been chosen

            // The actual message
            Paragraph messageContent = new Paragraph(message.getContent());
            messageContent.getElement().getStyle().set("background-color", "white");
            messageContent.getElement().getStyle().set("padding", "10px");
            messageContent.setWidth("80%");
            messageContent.setMinHeight("150px");

            // Visit profile button
            Button profile = new Button("Profil besuchen");
            profileButton(message, profile);

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

            replyButton(messageReply, replyButton);
            cancelButton.addClickListener(e -> messageReply.setValue(""));

            HorizontalLayout header = new HorizontalLayout(sender, senderVal, subject, subjectVal, date, dateVal);
            HorizontalLayout hButtons1 = new HorizontalLayout(profile, delete);
            HorizontalLayout hButtons2 = new HorizontalLayout(replyButton, cancelButton);

            VerticalLayout inboxReply = new VerticalLayout(header, mess, messageContent, hButtons1,
                    messageReply, hButtons2);
            inboxReply.setWidth("100%");

            splitLayout.remove(splitLayout.getSecondaryComponent());
            splitLayout.addToSecondary(inboxReply);
        }
        else
            cleanSecondary();
    }

    private void replyButton(TextArea messageReply, Button replyButton) {
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
    }

    private void profileButton(MessageDTO message, Button profile) {
        profile.addClickListener(e ->
                {
                    try {
                        UI.getCurrent().navigate(inboxControl.callProfileRoute(message.getSender()));
                    } catch (DatabaseUserException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    private void styling(Label sender, Label senderVal, Label subject, Label subjectVal, Label date, Label dateVal, Label mess) {
        // Styling
        for(Label label : new Label[] {sender, senderVal, subject, subjectVal, date, dateVal, mess})
            label.getElement().getStyle().set("font-size", "14px");

        for(Label label : new Label[] {sender, subject, date})
            label.getElement().getStyle().set("font-weight", "bold");
    }

    // Layout when a message has been deselected
    private static  void cleanSecondary() {
        hint = new Div();
        hint.setText("Es wurde keine Nachricht ausgewählt.");
        setDivStyle(hint);
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

    private static void setDivStyle(Div item) {
        item.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");
    }

    public InboxView() {
        //Required for Vaadin
    }

}
