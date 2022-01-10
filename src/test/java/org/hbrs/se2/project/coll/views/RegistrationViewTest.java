package org.hbrs.se2.project.coll.views;

import org.hbrs.se2.project.coll.control.RegistrationControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.RegistrationDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
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
    private String errorFirstName;

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
        errorFirstName = "Bitte geben Sie einen Vornamen ein";
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
    }

    @Test
    public void autoLoginAfterRegistrationTest() {

    }

    @Test
    public void MissingValueErrorFieldsTest() throws DatabaseUserException {
        RegistrationView registrationView = new RegistrationView();
        RegistrationView.RegisterForm registerForm = registrationView.new RegisterForm();
        populateBasicForm(registrationView);
        registrationView.firstName.setValue("");

        UserDTOImpl userDTO = registerForm.createNewUserDTO();
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl(userDTO, registrationView.emailRepeat.getValue(),
                registrationView.passwordRepeat.getValue());
        RegistrationResultDTO registrationResult = registrationControl.registerUser(registrationDTO);
        registrationView.setErrorFields(registrationResult.getReasons());

        Assert.assertNotNull(registrationView);
        Assert.assertNotNull(registerForm);
        Assert.assertNotNull(userDTO);
        Assert.assertNotNull(registrationDTO);
        Assert.assertNotNull(registrationResult);
        Assert.assertEquals(errorFirstName, registrationView.firstName.getErrorMessage());
        Assert.assertTrue(registrationView.firstName.isInvalid());
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
