package org.hbrs.se2.project.coll.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;

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
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO.ReasonType;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilNavigation;
import org.hbrs.se2.project.coll.util.Utils;
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
    @Autowired
    private SettingsControl settingsControl;

    Tab studentUser = new Tab("Student");
    Tab companyUser = new Tab(Globals.View.COMPANY);
    Tabs tabs       = new Tabs(studentUser, companyUser);

    // Basic User
    TextField salutation            = new TextField("Anrede");
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

    // Company
    TextField companyName           = new TextField("Name");
    EmailField companyEmail         = new EmailField("E-Mail");
    TextField companyPhone          = new TextField("Telefon");
    TextField companyFax            = new TextField("Fax");
    TextField companyHomepage       = new TextField("Webseite");
    TextField companyDescription    = new TextField("Beschreibung");

    // Company Address
    TextField   companyStreet              = new TextField("Straße");
    TextField   companyHouseNumber         = new TextField("Hausnummer");
    TextField   companyPostalCode          = new TextField("PLZ");
    TextField   companyCity                = new TextField("Stadt");
    ComboBox<String>   companyCountry      = new ComboBox<>("Country");

    class CompanyRegisterForm extends Div {
        CompanyRegisterForm() {
            // Company
            companyName.setRequiredIndicatorVisible(true);
            companyEmail.setRequiredIndicatorVisible(true);
            companyPhone.setRequiredIndicatorVisible(true);
            companyFax.setRequiredIndicatorVisible(true);
            companyHomepage.setRequiredIndicatorVisible(true);
            companyDescription.setRequiredIndicatorVisible(true);

            // Company Address
            companyStreet.setRequiredIndicatorVisible(true);
            companyHouseNumber.setRequiredIndicatorVisible(true);
            companyPostalCode.setRequiredIndicatorVisible(true);
            companyCity.setRequiredIndicatorVisible(true);
            companyCountry.setRequiredIndicatorVisible(true);

            companyCountry.setItems(Globals.Countries.getCountries());

            FormLayout companyFormLayout = new FormLayout();
            companyFormLayout.add(
                    companyName, companyEmail,
                    companyPhone, companyFax,
                    companyHomepage, companyDescription,
                    companyStreet, companyHouseNumber,
                    companyPostalCode, companyCity,
                    companyCountry
            );
            // Stretch country textfield to full row width
            companyFormLayout.setColspan(companyCountry, 2);
            companyFormLayout.setSizeUndefined();
            this.add(companyFormLayout);
        }

        public CompanyDTOImpl createNewCompanyDTO() {
            CompanyDTOImpl newCompanyDTO = new CompanyDTOImpl();
            newCompanyDTO.setCompanyName(companyName.getValue());
            newCompanyDTO.setEmail(companyEmail.getValue());
            newCompanyDTO.setPhoneNumber(companyPhone.getValue());
            newCompanyDTO.setFaxNumber(companyFax.getValue());
            newCompanyDTO.setWebsite(companyHomepage.getValue());
            newCompanyDTO.setDescription(companyDescription.getValue());

            Address companyAddress = new Address();
            companyAddress.setStreet(companyStreet.getValue());
            companyAddress.setHouseNumber(companyHouseNumber.getValue());
            companyAddress.setPostalCode(companyPostalCode.getValue());
            companyAddress.setCity(companyCity.getValue());
            companyAddress.setCountry(companyCountry.getValue());

            newCompanyDTO.setAddress(companyAddress);

            return newCompanyDTO;
        }
    }

    class RegisterForm extends Div {

        RegisterForm(){
            // Set required fields option
            // Basic User
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
            // Stretch country textfield to full row width
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

        H3 companyHeadline = new H3("Informationen des Unternehmens");
        H3 companyUserHeadline = new H3("Informationen des Administrators");
        companyUserHeadline.getElement().getStyle().set("Margin-Bottom", "30px");

        CompanyRegisterForm companyForm = new CompanyRegisterForm();
        companyForm.getElement().getStyle().set("Margin", "30px");

        companyUser.getElement().addEventListener("click", e -> {
            section.addComponentAtIndex(2, companyHeadline);
            section.addComponentAtIndex(3, companyForm);
            section.addComponentAtIndex(4, companyUserHeadline);
        });

        studentUser.getElement().addEventListener("click", e -> {
            section.remove(companyHeadline);
            section.remove(companyForm);
            section.remove(companyUserHeadline);
        });

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

                if (Objects.equals(tabs.getSelectedTab().getLabel(), "Unternehmen")) {
                    CompanyDTOImpl companyDTO = companyForm.createNewCompanyDTO();
                    registrationDTO.setCompanyDTO(companyDTO);
                }

                RegistrationResultDTO registrationResult = registrationControl.registerUser(registrationDTO);

                if (registrationResult.getResult()) {
                    // Success meldung
                    Utils.triggerDialogMessage("Registrierung abgeschlossen", "Sie haben sich erfolgreich registriert");
                    // automatischer Login
                    autoLoginAfterRegistration(userDTO);
                    // Routing auf main Seite
                    UtilNavigation.navigateToMain();
                } else {
                    // Fehlerbehandlung: Fehlerhafte TextFields mit Error Message versehen und auf invalid setzen
                    setErrorFields(registrationResult.getReasons());
                }
            } catch (DatabaseUserException databaseUserException) {
                Utils.triggerDialogMessage(Globals.View.ERROR,"Während der Registrierung ist ein Fehler aufgetreten: " + databaseUserException.getErrorCode());
            } catch (Exception exception) {
                Utils.triggerDialogMessage(Globals.View.ERROR,"Während der Registrierung ist ein unerwarteter Fehler aufgetreten: " + exception);
            }
        });
        add(siteLayout);
    }

    public void autoLoginAfterRegistration(UserDTOImpl userDTO)  {
        LoginResultDTO isAuthenticated = loginControl.authentificate(userDTO.getEmail(), userDTO.getPassword());
        if (isAuthenticated.getResult()) {
            UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, loginControl.getCurrentUser() );
        } else {
            Utils.triggerDialogMessage(Globals.View.ERROR,"Fehler beim automatischen einloggen. Bitte versuchen Sie es erneut");
        }
    }

    public void setErrorFields(List<ReasonType> reasons) {
        for (ReasonType reason : reasons) {
            if (reason == ReasonType.UNEXPECTED_ERROR) {
                Utils.triggerDialogMessage(Globals.View.ERROR, "Es ist ein unerwarteter Fehler aufgetreten");
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
                postalcode.setErrorMessage(Globals.View.POSTAL_CODE);
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

            // Company Fields
            if (reason == ReasonType.COMPANY_ALREADY_REGISTERED) {
                Utils.triggerDialogMessage(Globals.View.ERROR, "Die angegebene Firma ist bereits registriert");
            }
            if (reason == ReasonType.COMPANY_NAME_MISSING) {
                companyName.setErrorMessage("Bitte geben Sie einen gültigen Firmennamen ein");
                companyName.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_EMAIL_MISSING) {
                companyEmail.setErrorMessage("Bitte geben Sie eine Email ein");
                companyEmail.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_PHONE_MISSING) {
                companyPhone.setErrorMessage("Bitte geben Sie eine Telefonnummer ein");
                companyPhone.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_FAX_MISSING) {
                companyFax.setErrorMessage("Bitte geben Sie eine Faxnummer ein");
                companyFax.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_WEBSITE_MISSING) {
                companyHomepage.setErrorMessage("Bitte geben Sie eine Webseite ein");
                companyHomepage.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_DESCRIPTION_MISSING) {
                companyDescription.setErrorMessage("Bitte geben Sie eine Beschreibung ein");
                companyDescription.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_STREET_MISSING) {
                companyStreet.setErrorMessage("Bitte geben Sie eine Straße ein");
                companyStreet.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_HOUSENUMBER_MISSING) {
                companyHouseNumber.setErrorMessage("Bitte geben Sie eine Hausnummer ein");
                companyHouseNumber.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_POSTALCODE_MISSING) {
                companyPostalCode.setErrorMessage(Globals.View.POSTAL_CODE);
                companyPostalCode.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_CITY_MISSING) {
                companyCity.setErrorMessage("Bitte geben Sie eine Stadt ein");
                companyCity.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_COUNTRY_MISSING) {
                companyCountry.setErrorMessage("Bitte geben Sie ein land ein");
                companyCountry.setInvalid(true);
            }
            if (reason == ReasonType.COMPANY_EMAIL_INVALID) {
                companyEmail.setErrorMessage("Bitte geben Sie eine gültige Email-Adresse ein");
                companyEmail.setInvalid(true);
            }
        }
    }
}

