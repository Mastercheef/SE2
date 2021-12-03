package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.*;

import com.vaadin.flow.component.html.*;

import org.hbrs.se2.project.coll.control.*;
import org.hbrs.se2.project.coll.dtos.*;
import org.hbrs.se2.project.coll.dtos.impl.*;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;
import org.hbrs.se2.project.coll.layout.MainLayout;

@Route(value = "register" , layout = LayoutAlternative.class)
@PageTitle("Coll Registration")
public class RegistrationView extends Div {
    class RegisterForm extends Div {
        RegisterForm(){
            TextField address = new TextField("Address");
            TextField title = new TextField("Title");

            EmailField email = new EmailField("E-Mail");
            EmailField email_repeat = new EmailField("Repeat E-Mail");
            TextField firstname = new TextField("First-name");
            TextField surname = new TextField("Surname");
            PasswordField password = new PasswordField("Password");
            PasswordField password_repeat = new PasswordField("Repeat Password");

            email.setRequiredIndicatorVisible(true);
            email_repeat.setRequiredIndicatorVisible(true);
            password.setRequiredIndicatorVisible(true);
            password_repeat.setRequiredIndicatorVisible(true);

            FormLayout formLayout = new FormLayout();
            formLayout.add(
                    address, title,
                    firstname, surname,
                    email, email_repeat,
                    password, password_repeat
            );

            formLayout.setSizeUndefined();
            this.add(formLayout);

        }

        UserDTOImpl getDTO(){
            UserDTOImpl user = new UserDTOImpl();
            user.setFirstName("mike");
            user.setLastName("mike");
            return user;
        }
    }

    public RegistrationView() {

        VerticalLayout section = new VerticalLayout();
        section.setHeight("60%");
        section.setWidth("60%");

        H1 h1 = new H1("Register");

        Tab studentUser = new Tab("Student");
        Tab companyUser = new Tab("Recruiter");
        Tabs tabs = new Tabs(studentUser, companyUser);
        tabs.setSizeFull();
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);

        RegisterForm form = new RegisterForm();
        Button register_button = new Button("Register");
        section.add(h1, tabs, form, register_button);

        section.setPadding(true);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setAlignSelf(FlexComponent.Alignment.CENTER);

        HorizontalLayout siteLayout = new HorizontalLayout();
        siteLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        siteLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        siteLayout.setSizeFull();
        siteLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        siteLayout.add(section);

        RegistrationControl control = new RegistrationControl();
        register_button.addClickListener(e -> {
            if(control.validateInput(form.getDTO()) == false){
                Notification.show("invalid input");
            } else if(control.checkForExistence(form.getDTO()) == true) {
                Notification.show("user already exists");
            } else {
                RegistrationResultDTO result = control.registerUser(form.getDTO());
                if(result.getResult() == false){
                    Notification.show("connection error");
                }
            }
        });
        add(siteLayout);
    }
}

