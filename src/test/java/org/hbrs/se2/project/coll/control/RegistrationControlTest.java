package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.builder.UserDTOBuilder;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.CompanyFactory;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;

import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.RegistrationDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationControlTest {


    @InjectMocks
    private RegistrationControl registrationControl = new RegistrationControl();

    @Mock
    UserRepository userRepository;
    @Mock
    CompanyControl companyControl;
    @Mock
    StudentUserControl studentUserControl;
    @Mock
    ContactPersonControl contactPersonControl;

    @Mock
    UserDTO userDTO = new UserDTOImpl();
    @Mock
    CompanyRepository companyRepository;
    String email = "test@mail.de";
    String companyName ="Mustermann GMBH";
    String website = "mustermann.de";
    @Mock
    CompanyDTO companyDTO;

    UserDTOImpl user;

    @Test
    void checkIfEmailAlreadyInUse() {

        doReturn(userDTO).when(userRepository).findUserByEmail(email);
        when(userDTO.getId()).thenReturn(100);
        assertTrue(registrationControl.checkIfEmailAlreadyInUse(email));

    }

    @Test
    void checkIfEmailAlreadyInUseNoID() {
        doReturn(userDTO).when(userRepository).findUserByEmail(email);
        when(userDTO.getId()).thenReturn(0);
        assertFalse(registrationControl.checkIfEmailAlreadyInUse(email));
    }

    @Test
    void checkIfEmailAlreadyNull() {
        doReturn(null).when(userRepository).findUserByEmail(email);
        assertFalse(registrationControl.checkIfEmailAlreadyInUse(email));

    }
    @Test
    void checkIfCompanyAlreadyRegistered() {
        when(companyDTO.getCompanyName()).thenReturn(companyName);
        when(companyDTO.getEmail()).thenReturn(email);
        when(companyDTO.getWebsite()).thenReturn(website);

        when(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                companyDTO.getCompanyName(),
                companyDTO.getEmail(),
                companyDTO.getWebsite()
        )).thenReturn(companyDTO);

        when(companyDTO.getId()).thenReturn(100);
        assertTrue(registrationControl.checkIfCompanyAlreadyRegistered(companyDTO));
        when(companyDTO.getId()).thenReturn(0);
        assertFalse(registrationControl.checkIfCompanyAlreadyRegistered(companyDTO));

        when(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                companyDTO.getCompanyName(),
                companyDTO.getEmail(),
                companyDTO.getWebsite()
        )).thenReturn(null);
        assertFalse(registrationControl.checkIfCompanyAlreadyRegistered(companyDTO));

    }

    @Test
    void testRegisterUserPositiveStudent() throws DatabaseUserException {
        doReturn(null).when(userRepository).findUserByEmail(any());

        UserDTO userDTOPosStudent = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withType("st")
                .done();
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();
        registrationDTO.setUserDTO(userDTOPosStudent);
        registrationDTO.setRepeatedEmail(userDTOPosStudent.getEmail());
        registrationDTO.setRepeatedPassword(userDTOPosStudent.getPassword());

        RegistrationResultDTO result = registrationControl.registerUser(registrationDTO);
        assertTrue(result.getResult());
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.SUCCESS));
    }

    @Test
    void testRegisterUserNegativeStudentEmailInUse() throws DatabaseUserException {
        doReturn(userDTO).when(userRepository).findUserByEmail(any());
        when(userDTO.getId()).thenReturn(100);

        UserDTO userDTONegStEmail = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withType("st")
                .done();
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();
        registrationDTO.setUserDTO(userDTONegStEmail);
        registrationDTO.setRepeatedEmail(userDTONegStEmail.getEmail());
        registrationDTO.setRepeatedPassword(userDTONegStEmail.getPassword());

        RegistrationResultDTO result = registrationControl.registerUser(registrationDTO);
        assertFalse(result.getResult());
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.EMAIL_ALREADY_IN_USE));
    }

    @Test
    void testRegisterUserNegativeStudentInformtationMissing() throws DatabaseUserException {
        doReturn(null).when(userRepository).findUserByEmail(any());

        UserDTO userDTONegStInf = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withType("st")
                .withoutDateOfBirth()
                .withEmail("invalidEmail")
                .withFirstName("invalidName*-+")
                .withLastName("invalidName*-+")
                .done();
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();
        registrationDTO.setUserDTO(userDTONegStInf);
        registrationDTO.setRepeatedEmail("different");
        registrationDTO.setRepeatedPassword("different");

        RegistrationResultDTO result = registrationControl.registerUser(registrationDTO);
        assertFalse(result.getResult());
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.DATEOFBIRTH_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.EMAIL_INVALID));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.FIRSTNAME_INVALID));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.LASTNAME_INVALID));

        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.EMAIL_UNEQUAL));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.PASSWORD_UNEQUAL));
    }

    @Test
    void testRegisterUserPositiveCompany() throws DatabaseUserException {
        doReturn(null).when(companyRepository).findCompanyByCompanyNameAndEmailAndWebsite(any(),any(),any());

        UserDTO userDTOPosComp = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withType("cp")
                .done();

        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();
        registrationDTO.setUserDTO(userDTOPosComp);
        registrationDTO.setRepeatedEmail(userDTOPosComp.getEmail());
        registrationDTO.setRepeatedPassword(userDTOPosComp.getPassword());
        registrationDTO.setCompanyDTO(CompanyFactory.createTestCompany());

        RegistrationResultDTO result = registrationControl.registerUser(registrationDTO);
        assertTrue(result.getResult());
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.SUCCESS));
    }

    @Test
    void testRegisterUserNegativeCompany() throws DatabaseUserException {
        doReturn(companyDTO).when(companyRepository).findCompanyByCompanyNameAndEmailAndWebsite(any(),any(),any());
        when(companyDTO.getId()).thenReturn(100);

        UserDTO userDTONegComp = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withType("cp")
                .done();

        CompanyDTOImpl companyDTONeg = new CompanyDTOImpl();
        companyDTONeg.setCompanyName("Firma");
        Address address = new Address();
        companyDTONeg.setAddress(address);
        companyDTONeg.setPhoneNumber("");
        companyDTONeg.setFaxNumber("");
        companyDTONeg.setEmail("");
        companyDTONeg.setWebsite("");
        companyDTONeg.setDescription("");


        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();
        registrationDTO.setUserDTO(userDTONegComp);
        registrationDTO.setRepeatedEmail(userDTONegComp.getEmail());
        registrationDTO.setRepeatedPassword(userDTONegComp.getPassword());
        registrationDTO.setCompanyDTO(companyDTONeg);

        RegistrationResultDTO result = registrationControl.registerUser(registrationDTO);
        assertFalse(result.getResult());
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_ALREADY_REGISTERED));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_EMAIL_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_PHONE_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_FAX_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_WEBSITE_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_DESCRIPTION_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_STREET_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_HOUSENUMBER_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_POSTALCODE_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_CITY_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_COUNTRY_MISSING));
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.COMPANY_EMAIL_INVALID));
    }

    @Test
    void testRegisterUserNegativeUnexpectedException() throws DatabaseUserException {
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();
        registrationDTO.setUserDTO(null);

        RegistrationResultDTO result = registrationControl.registerUser(registrationDTO);
        assertFalse(result.getResult());
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.UNEXPECTED_ERROR));
    }

}