package org.hbrs.se2.project.coll.views;


import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.LoginControl;
import org.hbrs.se2.project.coll.control.SettingsControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.SettingsDTO;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "settings", layout = AppView.class)
@PageTitle("Einstellungen")
public class SettingsView extends VerticalLayout implements AfterNavigationObserver {

    @Autowired
    SettingsControl settingsControl;

    @Autowired
    LoginControl loginControl;

    SettingsView()  {
        setSizeFull();
        setAlignItems(Alignment.BASELINE);

        H1 title = new H1("Einstellungen");
        add(title);
    }

    /*  Initialize checked boxes of a checkbox group.
        Here, we can add more user-configurable settings later if needed.
     */
    private CheckboxGroup<String> initCheckboxGroup(SettingsDTO settingsDTO) {
        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems("Benachrichtigungen aktivieren", "Option A", "Option B", "Option C", "und so weiter");   // Platzhalter

        if(settingsDTO.getNotificationIsEnabled())
            checkboxGroup.select("Benachrichtigungen aktivieren");

        return checkboxGroup;
    }

    // Used to show/update SettingsDTO/Settings of the current user
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        SettingsDTO settingsDTO = settingsControl.getUserSettings(UtilCurrent.getCurrentUser().getId());

        CheckboxGroup<String> checkboxGroup = initCheckboxGroup(settingsDTO);

        // Styling of checkbox that's used for user preferences/settings
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        checkboxGroup.getElement().getStyle().set("padding-left", "2%");
        checkboxGroup.addValueChangeListener( valueChangeEvent -> {
            try {
                settingsControl.updateUserSettings(settingsDTO, checkboxGroup);
            } catch (DatabaseUserException e) {
                e.printStackTrace();
            }
        } );
        add(checkboxGroup);
    }

}
