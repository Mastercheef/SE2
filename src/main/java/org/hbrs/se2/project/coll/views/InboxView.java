package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
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
public class InboxView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    InboxControl inboxControl;

    private static List<MessageDTO> messages = new ArrayList<>();
    private static Grid<MessageDTO> grid;
    private static Div hint;

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
                return inboxControl.getSubject(message.getSubject());
            } catch (DatabaseUserException e) {
                e.printStackTrace();;
            }
            return null;
        }).setHeader("Betreff");

        // TODO: Datum

        // TODO: Beim anklicken aufklappen und Nachricht anzeigen, Antwortfeld und Senden/Cancel Buttons hinzufügen dabei
        grid.addColumn(MessageDTO::getContent).setHeader("Nachricht");
        /*grid.addColumn(person -> person.getAddress().getPhone())
                .setHeader("Phone");*/
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, message) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        try {
                            this.removeMessage(message);
                        } catch (DatabaseUserException ex) {
                            ex.printStackTrace();
                        }
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Löschen");

        messages = inboxControl.getMessages(getCurrentUser().getId());
        grid.setItems(messages);

        hint = new Div();
        hint.setText("Sie haben keine Nachrichten.");
        hint.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");

        add(hint, grid);
    }

    private void refreshGrid() {
        if (messages.size() > 0) {
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
