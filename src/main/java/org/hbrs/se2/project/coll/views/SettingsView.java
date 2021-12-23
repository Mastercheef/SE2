package org.hbrs.se2.project.coll.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.LoginControl;
import org.hbrs.se2.project.coll.control.SettingsControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.SettingsDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.SettingsDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.entities.Settings;
import org.hbrs.se2.project.coll.entities.User;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.SettingsRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route(value = "settings", layout = AppView.class)
@PageTitle("Einstellungen")
public class SettingsView extends VerticalLayout {

    @Autowired
    SettingsControl settingsControl;

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    UserRepository userRepository;

    SettingsView() throws DatabaseUserException {
        setSizeFull();
        setAlignItems(Alignment.BASELINE);

        H1 title = new H1("Einstellungen");

        //SettingsDTO settingsDTO = settingsControl.getUserSettings(getCurrentUser().getId());
        int userId = getCurrentUser().getId();
        //SettingsDTO dto = getUserSettings(userId);
        getUserSettings(userId);
        //System.out.println(dto.getNotificationIsEnabled());

       // System.out.println(settingsDTO.getNotificationIsEnabled());

        // Used to show/update SettingsDTO/Settings of the current user
       /* CheckboxGroup<String> checkboxGroup = initCheckboxGroup(settingsDTO);
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        checkboxGroup.getElement().getStyle().set("padding-left", "2%");

        checkboxGroup.addValueChangeListener( valueChangeEvent -> {
            try {
                settingsControl.updateSettings(settingsDTO, checkboxGroup);
            } catch (DatabaseUserException e) {
                e.printStackTrace();
            }
        } );

        add(title, checkboxGroup);
*/
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

    public UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    public void getUserSettings(int id) {
        UserDTO user = getCurrentUser();
        Settings settings = UserFactory.createSettingsFromBasicUser(user);
        System.out.println(settingsRepository.findSettingsById(id).getId());
//        return settingsRepository.findSettingsById(id);
    }

}
