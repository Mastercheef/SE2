package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.hbrs.se2.project.coll.dtos.impl.*;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO.ReasonType;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route(value = "register" , layout = AppView.class)
@PageTitle(Globals.PageTitles.REGISTER_PAGE_TITLE)
public class RegistrationView extends Div {

    @Autowired
    private RegistrationControl registrationControl;
    @Autowired
    private LoginControl loginControl;

    Tab studentUser = new Tab("Student");
    Tab companyUser = new Tab("Unternehmen");
    Tabs tabs       = new Tabs(studentUser, companyUser);

    // Basic User
    TextField salutation = new TextField("Anrede");
    TextField title                 = new TextField("Titel");
    EmailField email                = new EmailField("E-Mail");
    EmailField emailRepeat          = new EmailField("E-Mail wiederholen");
    TextField firstName             = new TextField("Vorname");
    TextField lastName              = new TextField("Nachname");
    PasswordField password          = new PasswordField("Password");
    PasswordField passwordRepeat    = new PasswordField("Password wiederholen");
    DatePicker dateOfBirth          = new DatePicker("Geburtsdatum");
    TextField   phone               = new TextField("Telefon");
    // Address
    TextField   street              = new TextField("Straße");
    TextField   housenumber         = new TextField("Hausnummer");
    TextField   postalcode          = new TextField("PLZ");
    TextField   city                = new TextField("Stadt");
    ComboBox<String>   country      = new ComboBox<>("Country");

    class RegisterForm extends Div {

        RegisterForm(){



            // Set required fields option
            salutation.setRequiredIndicatorVisible(true);
            title.setRequiredIndicatorVisible(true);
            firstName.setRequiredIndicatorVisible(true);
            lastName.setRequiredIndicatorVisible(true);
            dateOfBirth.setRequiredIndicatorVisible(true);
            phone.setRequiredIndicatorVisible(true);
            email.setRequiredIndicatorVisible(true);
            emailRepeat.setRequiredIndicatorVisible(true);
            password.setRequiredIndicatorVisible(true);
            passwordRepeat.setRequiredIndicatorVisible(true);
            street.setRequiredIndicatorVisible(true);
            housenumber.setRequiredIndicatorVisible(true);
            postalcode.setRequiredIndicatorVisible(true);
            city.setRequiredIndicatorVisible(true);
            country.setRequiredIndicatorVisible(true);

            // Set input length
            password.setMinLength(5);
            passwordRepeat.setMinLength(5);

            country.setItems(Globals.Countries.getCountries());

            FormLayout formLayout = new FormLayout();
            formLayout.add(
                    salutation, title,
                    firstName, lastName,
                    dateOfBirth, phone,
                    street, housenumber,
                    postalcode, city,
                    country,
                    email, emailRepeat,
                    password, passwordRepeat
            );
            // Stretch countra textfield to full row width
            formLayout.setColspan(country, 2);
            formLayout.setSizeUndefined();
            this.add(formLayout);
        }

        public UserDTOImpl createNewUserDTO() {
            UserDTOImpl newUser = new UserDTOImpl();
            newUser.setSalutation(salutation.getValue());
            newUser.setTitle(title.getValue());
            newUser.setFirstName(firstName.getValue());
            newUser.setLastName(lastName.getValue());
            newUser.setDateOfBirth(dateOfBirth.getValue());
            newUser.setPhone(phone.getValue());
            newUser.setEmail(email.getValue());
            newUser.setPassword(password.getValue());
            if(Objects.equals(tabs.getSelectedTab().getLabel(), "Student"))
                newUser.setType("st");
            else if(Objects.equals(tabs.getSelectedTab().getLabel(), "Unternehmen"))
                newUser.setType("cp");
            else
                newUser.setType("rest");

            Address address = new Address();
            address.setStreet(street.getValue());
            address.setHouseNumber(housenumber.getValue());
            address.setPostalCode(postalcode.getValue());
            address.setCity(city.getValue());
            address.setCountry(country.getValue());

            newUser.setAddress(address);

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
        form.getElement().getStyle().set("Margin", "30px");
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
            try {
                UserDTOImpl userDTO = form.createNewUserDTO();
                RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl(userDTO, emailRepeat.getValue(), passwordRepeat.getValue());
                RegistrationResultDTO registrationResult = registrationControl.registerUser(registrationDTO);

                if (registrationResult.getResult() == true) {
                    // Success meldung
                    triggerDialogMessage("Registrierung abgeschlossen", "Sie haben sich erfolgreich registriert");
                    // automatischer Login
                    autoLoginAfterRegistration(userDTO);
                    // Routing auf main Seite
                    UI.getCurrent().navigate(Globals.Pages.MENU_VIEW);
                } else {
                    // Fehlerbehandlung: Fehlerhafte TextFields mit Error Message versehen und auf invalid setzen
                    setErrorFields(registrationResult.getReasons());
                }
            } catch (DatabaseUserException databaseUserException) {
                triggerDialogMessage("Fehler","Während der Registrierung ist ein Fehler aufgetreten: " + databaseUserException.getReason());
            } catch (Exception exception) {
                triggerDialogMessage("Fehler","Während der Registrierung ist ein unerwarteter Fehler aufgetreten: " + exception);
            }
        });
        add(siteLayout);
    }

    public void autoLoginAfterRegistration(UserDTOImpl userDTO) {
        LoginResultDTO isAuthenticated = loginControl.authentificate(userDTO.getEmail(), userDTO.getPassword());
        if (isAuthenticated.getResult()) {
            UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, loginControl.getCurrentUser() );
        } else {
            triggerDialogMessage("Fehler","Fehler beim automatischen einloggen. Bitte versuchen Sie es erneut");
        }
    }

    public void setErrorFields(List<ReasonType> reasons) {
        for (ReasonType reason : reasons) {
            if (reason == ReasonType.UNEXPECTED_ERROR) {
                triggerDialogMessage("Fehler", "Es ist ein unerwarteter Fehler aufgetreten");
            }
            if (reason == ReasonType.SALUTATION_MISSING) {
                salutation.setErrorMessage("Bitte geben Sie eine Anrede ein");
                salutation.setInvalid(true);
            }
            if (reason == ReasonType.TITLE_MISSING) {
                title.setErrorMessage("Bitte geben Sie einen Titel ein");
                title.setInvalid(true);
            }
            if (reason == ReasonType.FIRSTNAME_MISSING) {
                firstName.setErrorMessage("Bitte geben Sie einen Vornamen ein");
                firstName.setInvalid(true);
            }
            if (reason == ReasonType.LASTNAME_MISSING) {
                lastName.setErrorMessage("Bitte geben Sie einen Nachnamen ein");
                lastName.setInvalid(true);
            }
            if (reason == ReasonType.DATEOFBIRTH_MISSING) {
                dateOfBirth.setErrorMessage("Bitte geben Sie ein Geburtsdatum ein");
                dateOfBirth.setInvalid(true);
            }
            if (reason == ReasonType.PHONE_MISSING) {
                phone.setErrorMessage("Bitte geben Sie eine Telefonnummer ein");
                phone.setInvalid(true);
            }
            if (reason == ReasonType.STREET_MISSING) {
                street.setErrorMessage("Bitte geben Sie eine Straße ein");
                street.setInvalid(true);
            }
            if (reason == ReasonType.HOUSENUMBER_MISSING) {
                housenumber.setErrorMessage("Bitte geben Sie eine Hausnummer ein");
                housenumber.setInvalid(true);
            }
            if (reason == ReasonType.POSTALCODE_MISSING) {
                postalcode.setErrorMessage("Bitte geben Sie eine Postleitzahl ein");
                postalcode.setInvalid(true);
            }
            if (reason == ReasonType.CITY_MISSING) {
                city.setErrorMessage("Bitte geben Sie eine Stadt ein");
                city.setInvalid(true);
            }
            if (reason == ReasonType.COUNTRY_MISSING) {
                country.setErrorMessage("Bitte geben Sie ein Land ein");
                country.setInvalid(true);
            }
            if (reason == ReasonType.EMAIL_ALREADY_IN_USE) {
                email.setErrorMessage("Die angegebene Email wird bereits verwendet");
                email.setInvalid(true);
                emailRepeat.setErrorMessage("Die angegebene Email wird bereits verwendet");
                emailRepeat.setInvalid(true);
            }
            if (reason == ReasonType.EMAIL_INVALID) {
                email.setErrorMessage("Bitte geben Sie eine gültige Email ein");
                email.setInvalid(true);
                emailRepeat.setErrorMessage("Bitte geben Sie eine gültige Email ein");
                emailRepeat.setInvalid(true);
            }
            if (reason == ReasonType.PASSWORD_INVALID) {
                password.setErrorMessage("Bitte geben Sie ein gültiges Password ein: Min 5 Zeichen");
                password.setInvalid(true);
                passwordRepeat.setErrorMessage("Bitte geben Sie ein gültiges Password ein: Min 5 Zeichen");
                passwordRepeat.setInvalid(true);
            }
            if (reason == ReasonType.EMAIL_UNEQUAL) {
                email.setErrorMessage("Die eingebene Email Adressen stimmen nicht überein");
                email.setInvalid(true);
                emailRepeat.setErrorMessage("Die eingebene Email Adressen stimmen nicht überein");
                emailRepeat.setInvalid(true);
            }
            if (reason == ReasonType.PASSWORD_UNEQUAL) {
                password.setErrorMessage("Die eingegebenen Passwörter stimmen nicht überein");
                password.setInvalid(true);
                passwordRepeat.setErrorMessage("Die eingegebenen Passwörter stimmen nicht überein");
                passwordRepeat.setInvalid(true);
            }
            if (reason == ReasonType.PASSWORD_MISSING) {
                password.setErrorMessage("Bitte geben Sie eine gültiges Passwort ein");
                password.setInvalid(true);
                passwordRepeat.setErrorMessage("Bitte geben Sie eine gültiges Passwort ein");
                passwordRepeat.setInvalid(true);
            }
        }
    }

    public void triggerDialogMessage(String header, String message) {
        Dialog dialog = new Dialog();
        dialog.add(new H3(header));
        dialog.add(new Text(message + "<br>"));
        dialog.setWidth("600px");
        dialog.setHeight("250px");
        dialog.add(new Button("OK", e -> { dialog.close(); }));
        dialog.getElement().getStyle().set("display", "flex");
        dialog.getElement().getStyle().set("flex-direction", "column");
        dialog.getElement().getStyle().set("align-items", "center");
        dialog.getElement().getStyle().set("justify-content", "space-around");
        dialog.open();
    }

}

