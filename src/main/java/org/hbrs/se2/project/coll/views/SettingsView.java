package org.hbrs.se2.project.coll.views;


import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.SettingsControl;
import org.hbrs.se2.project.coll.layout.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "settings", layout = AppView.class)
@PageTitle("Einstellungen")
public class SettingsView extends VerticalLayout {

    @Autowired
    SettingsControl optionsControl;


    SettingsView() {
        setSizeFull();
        setAlignItems(Alignment.BASELINE);

        H1 title = new H1("Einstellungen");

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems("Benachrichtigungen aktivieren", "Option A", "Option B", "Option C", "und so weiter");
        checkboxGroup.select("Benachrichtigungen aktivieren");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        checkboxGroup.getElement().getStyle().set("padding-left", "2%");

        checkboxGroup.addValueChangeListener( valueChangeEvent -> {
            System.out.println(checkboxGroup.getValue());
            // TODO: Save options in DB for particular user
        } );

        add(title, checkboxGroup);

    }


}
