package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.*;
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
import org.hbrs.se2.project.coll.dtos.impl.*;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route(value = "register" , layout = LayoutAlternative.class)
@PageTitle("Coll Registration")
public class RegistrationView extends Div {

    @Autowired
    private RegistrationControl control;

    Tab studentUser = new Tab("Student");
    Tab companyUser = new Tab("Unternehmen");
    Tabs tabs       = new Tabs(studentUser, companyUser);

    class RegisterForm extends Div {

        TextField address               = new TextField("Anrede");
        TextField title                 = new TextField("Titel");
        EmailField email                = new EmailField("E-Mail");
        EmailField emailRepeat          = new EmailField("E-Mail wiederholen");
        TextField firstName             = new TextField("Name");
        TextField surname               = new TextField("Nachname");
        PasswordField password          = new PasswordField("Password");
        PasswordField passwordRepeat    = new PasswordField("Password wiederholen");

        RegisterForm(){

            // Error Handling for Fields
            email.setRequiredIndicatorVisible(true);
            email.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse ein.");
            emailRepeat.setRequiredIndicatorVisible(true);
            emailRepeat.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse ein.");
            password.setRequiredIndicatorVisible(true);
            password.setErrorMessage("Bitte geben Sie ein Passwort ein.");
            password.setMinLength(5);
            passwordRepeat.setRequiredIndicatorVisible(true);
            passwordRepeat.setErrorMessage("Bitte geben Sie ein Passwort ein.");
            passwordRepeat.setMinLength(5);

            FormLayout formLayout = new FormLayout();
            formLayout.add(
                    address, title,
                    firstName, surname,
                    email, emailRepeat,
                    password, passwordRepeat
            );
            formLayout.setSizeUndefined();
            this.add(formLayout);
        }

        public UserDTOImpl createNewUserDTO() {
            UserDTOImpl newUser = new UserDTOImpl();
            newUser.setSalutation(address.getValue());
            newUser.setTitle(title.getValue());
            newUser.setFirstName(firstName.getValue());
            newUser.setLastName(surname.getValue());
            newUser.setEmail(email.getValue());
            newUser.setPassword(password.getValue());
            if(Objects.equals(tabs.getSelectedTab().getLabel(), "Student"))
                newUser.setType("st");
            else if(Objects.equals(tabs.getSelectedTab().getLabel(), "Unternehmen"))
                newUser.setType("cp");
            else
                newUser.setType("rest");
            return newUser;
        }
    }

    public RegistrationView() {

        VerticalLayout section = new VerticalLayout();
        section.setWidth("50%");

        H1 h1 = new H1("Registrierung");

        tabs.setSizeFull();
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);

        RegisterForm form = new RegisterForm();
        Button registerButton = new Button("Registrieren");
        section.add(h1, tabs, form, registerButton);

        section.setPadding(true);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setAlignSelf(FlexComponent.Alignment.CENTER);

        HorizontalLayout siteLayout = new HorizontalLayout();
        siteLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        siteLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        siteLayout.setSizeFull();
        siteLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        siteLayout.add(section);

        registerButton.addClickListener(e -> {
            UserDTOImpl dto = form.createNewUserDTO();
            boolean exists  = control.checkUserExistence(dto);

            /*
                We have to check several things here.
                First:  Check if the email in the register form exists in the Database.
                Second: Check if the email in both email-fields coincide.
                Last:   Check if the passwords in both password-fields coincide.
                Only then we may save the created DTO in the Database.
             */
            if(!exists)
                if(Objects.equals(form.email.getValue(), form.emailRepeat.getValue()))
                    if(Objects.equals(form.password.getValue(), form.passwordRepeat.getValue()))
                    {
                        control.registerUser(dto);
                        UI.getCurrent().navigate(Globals.Pages.REGISTRATION_SUCCESSFUL);
                        // TODO: Studentprofil muss direkt angelegt werden, sonst wird ein Nullpointer error geworfen ( bei Aufruf des Profils )
                    }
                    else
                        triggerErrorMessage("Die angegebenen Passwörter stimmen nicht überein.");
                else
                    triggerErrorMessage("Die angegebenen E-Mails stimmen nicht überein.");
            else
                triggerErrorMessage("Ein Nutzer mit der angegebenen E-Mail-Adresse existiert bereits. " +
                        "Bitte wählen Sie eine andere E-Mail-Adresse.");
        });
        add(siteLayout);
    }

    public void triggerErrorMessage(String message) {
        Dialog dialog = new Dialog();
        dialog.add(new Text(message));
        dialog.setWidth("400px");
        dialog.setHeight("150px");
        dialog.open();
    }

}

