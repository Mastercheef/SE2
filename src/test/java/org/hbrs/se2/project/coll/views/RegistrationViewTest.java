package org.hbrs.se2.project.coll.views;

import org.hbrs.se2.project.coll.control.RegistrationControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.RegistrationDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.util.Globals;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationViewTest {
    private String salutation;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private String email;
    private String emailRepeat;
    private String pw;
    private String pwRepeat;

    private String companyName;
    private String companyEmail;
    private String companyPhone;
    private String companyFax;
    private String companyHomepage;
    private String companyDescription;
    private String companyStreet;
    private String companyHouseNumber;
    private String companyPostalCode;
    private String companyCity;
    private String companyCountry;

    private String type;

    // Error messages
    private String errorSalutationMissing;
    private String errorFirstNameMissing;
    private String errorTitleMissing;
    private String errorLastNameMissing;
    private String errorDateOfBirthMissing;
    private String errorPhoneMissing;
    private String errorStreetMissing;
    private String errorHouseNumberMissing;
    private String errorPostalCodeMissing;
    private String errorCityMissing;
    private String errorCountryMissing;
    private String errorEmailInvalid;
    private String errorPWMissing;

    // Company error messages
    private String errorCompanyNameMissing;
    private String errorCompanyPhoneMissing;
    private String errorCompanyFaxMissing;
    private String errorCompanyWebsiteMissing;
    private String errorCompanyDescriptionMissing;
    private String errorCompanyStreetMissing;
    private String errorCompanyHouseNumberMissing;
    private String errorCompanyPostalCodeMissing;
    private String errorCompanyCityMissing;
    private String errorCompanyCountryMissing;
    private String errorCompanyEmailInvalid;

    @Autowired
    private RegistrationControl registrationControl;

    @Before
    public void setupData() {
        // Basic user
        salutation = "Herr";
        title = "-";
        firstName = "John";
        lastName = "Stamos";
        dateOfBirth = LocalDate.of(1963, 8, 19);
        phone = "0123456";
        street = "Volle Straße";
        houseNumber = "69";
        postalCode = "69696";
        city = "Tampa";
        country = "USA";
        email = "johnstamos@americamail.com";
        emailRepeat = email;
        pw = "whatever";
        pwRepeat = pw;

        // Company
        companyName = "The Big Company";
        companyEmail = "bigcompany@big.com";
        companyPhone = "555-BIG";
        companyFax = "555-BIGFAX";
        companyHomepage = "bigcompany.com";
        companyDescription = "The biggest company in the state of Florida.";
        companyStreet = "Heavy Duty Street";
        companyHouseNumber = "11";
        companyPostalCode = "12333";
        companyCity = "Tampa";
        companyCountry = "USA";

        // Misc.
        type = "st";

        errorSalutationMissing = "Bitte geben Sie eine Anrede ein";
        errorTitleMissing = "Bitte geben Sie einen Titel ein";
        errorFirstNameMissing = "Bitte geben Sie einen Vornamen ein";
        errorLastNameMissing = "Bitte geben Sie einen Nachnamen ein";
        errorDateOfBirthMissing = "Bitte geben Sie ein Geburtsdatum ein";
        errorPhoneMissing = "Bitte geben Sie eine Telefonnummer ein";
        errorStreetMissing = "Bitte geben Sie eine Straße ein";
        errorHouseNumberMissing = "Bitte geben Sie eine Hausnummer ein";
        errorPostalCodeMissing = "Bitte geben Sie eine Postleitzahl ein";
        errorCityMissing = "Bitte geben Sie eine Stadt ein";
        errorCountryMissing = "Bitte geben Sie ein Land ein";
        errorEmailInvalid = "Bitte geben Sie eine gültige Email ein";
        errorPWMissing = "Bitte geben Sie eine gültiges Passwort ein";
        errorCompanyNameMissing = "Bitte geben Sie einen gültigen Firmennamen ein";
        errorCompanyPhoneMissing = "Bitte geben Sie eine Telefonnummer ein";
        errorCompanyFaxMissing = "Bitte geben Sie eine Faxnummer ein";
        errorCompanyWebsiteMissing = "Bitte geben Sie eine Webseite ein";
        errorCompanyDescriptionMissing = "Bitte geben Sie eine Beschreibung ein";
        errorCompanyStreetMissing = "Bitte geben Sie eine Straße ein";
        errorCompanyHouseNumberMissing = "Bitte geben Sie eine Hausnummer ein";
        errorCompanyPostalCodeMissing = Globals.View.POSTAL_CODE;
        errorCompanyCityMissing = "Bitte geben Sie eine Stadt ein";
        errorCompanyCountryMissing = "Bitte geben Sie ein land ein";
        errorCompanyEmailInvalid = "Bitte geben Sie eine gültige Email-Adresse ein";
    }

    @Test
    public void basicRegistrationFieldsPopulatedTest() {
        RegistrationView registrationView = new RegistrationView();
        populateBasicForm(registrationView);

        Assert.assertNotNull(registrationView);
        Assert.assertEquals(this.salutation, registrationView.salutation.getValue());
        Assert.assertEquals(this.title, registrationView.title.getValue());
        Assert.assertEquals(this.firstName, registrationView.firstName.getValue());
        Assert.assertEquals(this.lastName, registrationView.lastName.getValue());
        Assert.assertEquals(this.dateOfBirth, registrationView.dateOfBirth.getValue());
        Assert.assertEquals(this.phone, registrationView.phone.getValue());
        Assert.assertEquals(this.street, registrationView.street.getValue());
        Assert.assertEquals(this.houseNumber, registrationView.housenumber.getValue());
        Assert.assertEquals(this.postalCode, registrationView.postalcode.getValue());
        Assert.assertEquals(this.city, registrationView.city.getValue());
        Assert.assertEquals(this.country, registrationView.country.getValue());
        Assert.assertEquals(this.email, registrationView.email.getValue());
        Assert.assertEquals(this.emailRepeat, registrationView.emailRepeat.getValue());
        Assert.assertEquals(this.pw, registrationView.password.getValue());
        Assert.assertEquals(this.pwRepeat, registrationView.passwordRepeat.getValue());
    }

    @Test
    public void companyRegistrationFieldsPopulatedTest() {
        RegistrationView registrationView = new RegistrationView();
        populateCompanyForm(registrationView);

        Assert.assertNotNull(registrationView);
        Assert.assertEquals(this.companyName, registrationView.companyName.getValue());
        Assert.assertEquals(this.companyEmail, registrationView.companyEmail.getValue());
        Assert.assertEquals(this.companyPhone, registrationView.companyPhone.getValue());
        Assert.assertEquals(this.companyFax, registrationView.companyFax.getValue());
        Assert.assertEquals(this.companyHomepage, registrationView.companyHomepage.getValue());
        Assert.assertEquals(this.companyDescription, registrationView.companyDescription.getValue());
        Assert.assertEquals(this.companyStreet, registrationView.companyStreet.getValue());
        Assert.assertEquals(this.companyHouseNumber, registrationView.companyHouseNumber.getValue());
        Assert.assertEquals(this.companyPostalCode, registrationView.companyPostalCode.getValue());
        Assert.assertEquals(this.companyCity, registrationView.companyCity.getValue());
        Assert.assertEquals(this.companyCountry, registrationView.companyCountry.getValue());
    }

    @Test
    public void createNewCompanyDTOTest() {
        RegistrationView registrationView = new RegistrationView();
        RegistrationView.CompanyRegisterForm companyRegisterForm = registrationView.new CompanyRegisterForm();
        populateCompanyForm(registrationView);
        CompanyDTOImpl companyDTO = companyRegisterForm.createNewCompanyDTO();

        Assert.assertNotNull(companyDTO);
        Assert.assertEquals(this.companyName, companyDTO.getCompanyName());
        Assert.assertEquals(this.companyEmail, companyDTO.getEmail());
        Assert.assertEquals(this.companyPhone, companyDTO.getPhoneNumber());
        Assert.assertEquals(this.companyFax, companyDTO.getFaxNumber());
        Assert.assertEquals(this.companyHomepage, companyDTO.getWebsite());
        Assert.assertEquals(this.companyDescription, companyDTO.getDescription());
        Assert.assertEquals(this.companyStreet, companyDTO.getAddress().getStreet());
        Assert.assertEquals(this.companyHouseNumber, companyDTO.getAddress().getHouseNumber());
        Assert.assertEquals(this.companyPostalCode, companyDTO.getAddress().getPostalCode());
        Assert.assertEquals(this.companyCity, companyDTO.getAddress().getCity());
        Assert.assertEquals(this.companyCountry, companyDTO.getAddress().getCountry());
    }

    @Test
    public void createNewUserDTOTest() {
        RegistrationView registrationView = new RegistrationView();
        RegistrationView.RegisterForm registerForm = registrationView.new RegisterForm();
        populateBasicForm(registrationView);
        UserDTOImpl userDTO = registerForm.createNewUserDTO();

        Assert.assertNotNull(userDTO);
        Assert.assertEquals(this.salutation, userDTO.getSalutation());
        Assert.assertEquals(this.title, userDTO.getTitle());
        Assert.assertEquals(this.firstName, userDTO.getFirstName());
        Assert.assertEquals(this.lastName, userDTO.getLastName());
        Assert.assertEquals(this.dateOfBirth, userDTO.getDateOfBirth());
        Assert.assertEquals(this.phone, userDTO.getPhone());
        Assert.assertEquals(this.street, userDTO.getAddress().getStreet());
        Assert.assertEquals(this.houseNumber, userDTO.getAddress().getHouseNumber());
        Assert.assertEquals(this.postalCode, userDTO.getAddress().getPostalCode());
        Assert.assertEquals(this.city, userDTO.getAddress().getCity());
        Assert.assertEquals(this.country, userDTO.getAddress().getCountry());
        Assert.assertEquals(this.email, userDTO.getEmail());
        Assert.assertEquals(this.type, userDTO.getType());
        Assert.assertEquals(this.pw, userDTO.getPassword());
    }

    @Test
    public void missingValueErrorFieldsTest() throws DatabaseUserException {
        RegistrationView registrationView = new RegistrationView();
        RegistrationView.RegisterForm registerForm = registrationView.new RegisterForm();
        populateBasicForm(registrationView);
        registrationView.firstName.setValue("");

        userDTO(registerForm, registrationView, registrationControl);
        Assert.assertEquals(errorFirstNameMissing, registrationView.firstName.getErrorMessage());
        Assert.assertTrue(registrationView.firstName.isInvalid());
    }

    private void userDTO(RegistrationView.RegisterForm registerForm, RegistrationView registrationView, RegistrationControl registrationControl) throws DatabaseUserException {
        UserDTOImpl userDTO = registerForm.createNewUserDTO();
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl(userDTO, registrationView.emailRepeat.getValue(),
                registrationView.passwordRepeat.getValue());
        RegistrationResultDTO registrationResult = registrationControl.registerUser(registrationDTO);
        registrationView.setUserErrorFields(registrationResult.getReasons());
        registrationView.setCompanyErrorFields(registrationResult.getReasons());

        Assert.assertNotNull(registrationView);
        Assert.assertNotNull(registerForm);
        Assert.assertNotNull(userDTO);
        Assert.assertNotNull(registrationDTO);
        Assert.assertNotNull(registrationResult);
    }

    @Test
    public void allMissingValuesErrorFieldsTest() throws DatabaseUserException {
        RegistrationView registrationView = new RegistrationView();
        RegistrationView.RegisterForm registerForm = registrationView.new RegisterForm();

        userDTO(registerForm, registrationView, registrationControl);
        Assert.assertEquals(errorSalutationMissing, registrationView.salutation.getErrorMessage());
        Assert.assertEquals(errorTitleMissing, registrationView.title.getErrorMessage());
        Assert.assertEquals(errorFirstNameMissing, registrationView.firstName.getErrorMessage());
        Assert.assertEquals(errorLastNameMissing, registrationView.lastName.getErrorMessage());
        Assert.assertEquals(errorDateOfBirthMissing, registrationView.dateOfBirth.getErrorMessage());
        Assert.assertEquals(errorPhoneMissing, registrationView.phone.getErrorMessage());
        Assert.assertEquals(errorStreetMissing, registrationView.street.getErrorMessage());
        Assert.assertEquals(errorHouseNumberMissing, registrationView.housenumber.getErrorMessage());
        Assert.assertEquals(errorPostalCodeMissing, registrationView.postalcode.getErrorMessage());
        Assert.assertEquals(errorCityMissing, registrationView.city.getErrorMessage());
        Assert.assertEquals(errorCountryMissing, registrationView.country.getErrorMessage());
        Assert.assertEquals(errorEmailInvalid, registrationView.email.getErrorMessage());
        Assert.assertEquals(errorEmailInvalid, registrationView.emailRepeat.getErrorMessage());
        Assert.assertEquals(errorPWMissing, registrationView.password.getErrorMessage());
        Assert.assertEquals(errorPWMissing, registrationView.passwordRepeat.getErrorMessage());

        Assert.assertTrue(registrationView.salutation.isInvalid());
        Assert.assertTrue(registrationView.title.isInvalid());
        Assert.assertTrue(registrationView.firstName.isInvalid());
        Assert.assertTrue(registrationView.lastName.isInvalid());
        Assert.assertTrue(registrationView.dateOfBirth.isInvalid());
        Assert.assertTrue(registrationView.phone.isInvalid());
        Assert.assertTrue(registrationView.street.isInvalid());
        Assert.assertTrue(registrationView.housenumber.isInvalid());
        Assert.assertTrue(registrationView.postalcode.isInvalid());
        Assert.assertTrue(registrationView.city.isInvalid());
        Assert.assertTrue(registrationView.country.isInvalid());
        Assert.assertTrue(registrationView.email.isInvalid());
        Assert.assertTrue(registrationView.emailRepeat.isInvalid());
        Assert.assertTrue(registrationView.password.isInvalid());
        Assert.assertTrue(registrationView.passwordRepeat.isInvalid());
    }

    @Test
    public void allMissingCompanyValuesErrorFieldsTest() throws DatabaseUserException {
        RegistrationView registrationView = new RegistrationView();
        RegistrationView.RegisterForm registerForm = registrationView.new RegisterForm();
        RegistrationView.CompanyRegisterForm companyRegisterForm = registrationView.new CompanyRegisterForm();

        UserDTOImpl userDTO = registerForm.createNewUserDTO();
        userDTO.setType("cp");
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl(userDTO, registrationView.emailRepeat.getValue(),
                registrationView.passwordRepeat.getValue());

        CompanyDTOImpl companyDTO = companyRegisterForm.createNewCompanyDTO();
        registrationDTO.setCompanyDTO(companyDTO);

        RegistrationResultDTO registrationResult = registrationControl.registerUser(registrationDTO);
        registrationView.setUserErrorFields(registrationResult.getReasons());
        registrationView.setCompanyErrorFields(registrationResult.getReasons());

        Assert.assertNotNull(registrationView);
        Assert.assertNotNull(registerForm);
        Assert.assertNotNull(companyRegisterForm);
        Assert.assertNotNull(userDTO);
        Assert.assertNotNull(registrationDTO);
        Assert.assertNotNull(registrationResult);
        Assert.assertEquals(errorCompanyNameMissing, registrationView.companyName.getErrorMessage());
        Assert.assertEquals(errorCompanyEmailInvalid, registrationView.companyEmail.getErrorMessage());
        Assert.assertEquals(errorCompanyPhoneMissing, registrationView.companyPhone.getErrorMessage());
        Assert.assertEquals(errorCompanyFaxMissing, registrationView.companyFax.getErrorMessage());
        Assert.assertEquals(errorCompanyWebsiteMissing, registrationView.companyHomepage.getErrorMessage());
        Assert.assertEquals(errorCompanyDescriptionMissing, registrationView.companyDescription.getErrorMessage());
        Assert.assertEquals(errorCompanyStreetMissing, registrationView.companyStreet.getErrorMessage());
        Assert.assertEquals(errorCompanyHouseNumberMissing, registrationView.companyHouseNumber.getErrorMessage());
        Assert.assertEquals(errorCompanyPostalCodeMissing, registrationView.companyPostalCode.getErrorMessage());
        Assert.assertEquals(errorCompanyCityMissing, registrationView.companyCity.getErrorMessage());
        Assert.assertEquals(errorCompanyCountryMissing, registrationView.companyCountry.getErrorMessage());

        Assert.assertTrue(registrationView.companyName.isInvalid());
        Assert.assertTrue(registrationView.companyEmail.isInvalid());
        Assert.assertTrue(registrationView.companyPhone.isInvalid());
        Assert.assertTrue(registrationView.companyFax.isInvalid());
        Assert.assertTrue(registrationView.companyHomepage.isInvalid());
        Assert.assertTrue(registrationView.companyDescription.isInvalid());
        Assert.assertTrue(registrationView.companyStreet.isInvalid());
        Assert.assertTrue(registrationView.companyHouseNumber.isInvalid());
        Assert.assertTrue(registrationView.companyPostalCode.isInvalid());
        Assert.assertTrue(registrationView.companyCity.isInvalid());
        Assert.assertTrue(registrationView.companyCountry.isInvalid());
    }

    private void populateBasicForm(RegistrationView registrationView) {
        registrationView.salutation.setValue(this.salutation);
        registrationView.title.setValue(this.title);
        registrationView.firstName.setValue(this.firstName);
        registrationView.lastName.setValue(this.lastName);
        registrationView.dateOfBirth.setValue(this.dateOfBirth);
        registrationView.phone.setValue(this.phone);
        registrationView.street.setValue(this.street);
        registrationView.housenumber.setValue(this.houseNumber);
        registrationView.postalcode.setValue(this.postalCode);
        registrationView.city.setValue(this.city);
        registrationView.country.setValue(this.country);
        registrationView.email.setValue(this.email);
        registrationView.emailRepeat.setValue(this.emailRepeat);
        registrationView.password.setValue(this.pw);
        registrationView.passwordRepeat.setValue(this.pwRepeat);
    }

    private void populateCompanyForm(RegistrationView registrationView) {
        registrationView.companyName.setValue(this.companyName);
        registrationView.companyEmail.setValue(this.companyEmail);
        registrationView.companyPhone.setValue(this.companyPhone);
        registrationView.companyFax.setValue(this.companyFax);
        registrationView.companyHomepage.setValue(this.companyHomepage);
        registrationView.companyDescription.setValue(this.companyDescription);
        registrationView.companyStreet.setValue(this.companyStreet);
        registrationView.companyHouseNumber.setValue(this.companyHouseNumber);
        registrationView.companyPostalCode.setValue(this.companyPostalCode);
        registrationView.companyCity.setValue(this.companyCity);
        registrationView.companyCountry.setValue(this.companyCountry);
    }

}
