package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.CompanyFactory;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.*;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO.ReasonType;
import org.hbrs.se2.project.coll.dtos.impl.RegistrationResultDTOImpl;
import org.hbrs.se2.project.coll.entities.*;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegistrationControl {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentUserRepository studentUserRepository;
    @Autowired
    ContactPersonRepository contactPersonRepository;
    @Autowired
    AddressControl addressControl;
    @Autowired
    CompanyControl companyControl;

    RegistrationDTO registrationDTO;
    RegistrationResultDTOImpl registrationResult;

    public RegistrationResultDTO registerUser(RegistrationDTO registrationDTO) {
        try {
            this.registrationResult = new RegistrationResultDTOImpl();
            this.registrationDTO = registrationDTO;

            checkForRequiredUserInformation();
            validateRequiredUserInformation();
            checkRepeatedEmail();
            checkRepeatedPassword();

            if (checkIfEmailAlreadyInUse(registrationDTO.getUserDTO().getEmail())) {
                registrationResult.addReason(ReasonType.EMAIL_ALREADY_IN_USE);
            }

            if (registrationDTO.getUserDTO().getType().equals("cp")) {
                checkForRequiredCompanyInformation();
                validateRequiredCompanyInformation();
            }

            // Address Handling: Überprüfen ob Addresse bereits existiert, wenn ja id holen
            // Address Control anlegen und zentral prüfen
/*
            if(userDTO.getType() == "st") {
                StudentUserDTOImpl studentProfile = new StudentUserDTOImpl();
                studentProfile.setId(userDTO.getId());
                studentProfile.setAddress(userDTO.getAddress());
                studentProfile.setDateOfBirth(userDTO.getDateOfBirth());
                control.saveStudentUser(studentProfile);
            }*/

            if (registrationResult.getReasons().isEmpty()) {

                // Prüfen, ob Adresse bereits existiert, wenn ja Datensatz aus DB holen, sonst neu anlegen
                Address address = addressControl.checkAddressExistence(registrationDTO.getUserDTO().getAddress());

                if (registrationDTO.getUserDTO().getType().equals("st")) {
                    StudentUser studentUser = UserFactory.createStudentUser((StudentUserDTO) registrationDTO.getUserDTO());
                    studentUser.setAddress(address);
                    this.studentUserRepository.save(studentUser);
                }

                if (registrationDTO.getUserDTO().getType().equals("cp")) {
                    Company savedCompany = companyControl.saveCompany(registrationDTO.getCompanyDTO());

                    // set company for contact person
                    ContactPerson newContactPerson = UserFactory.createContactPerson((ContactPersonDTO) registrationDTO.getUserDTO());
                    newContactPerson.setAddress(address);
                    newContactPerson.setCompany(savedCompany);
                    this.userRepository.save(newContactPerson);
                }

                registrationResult.addReason(ReasonType.SUCCESS);
                registrationResult.setResult(true);
            } else {
                registrationResult.setResult(false);
            }


        } catch (Error error) {
            // TODO: Return resultDTO mit Fehler (return RegistrationResultDTO)
            registrationResult.setResult(false);
            registrationResult.addReason(ReasonType.UNEXPECTED_ERROR);
        }
        return registrationResult;
    }

    private void checkForRequiredUserInformation() {
        checkValueAndSetResponse(registrationDTO.getUserDTO().getSalutation(), RegistrationResultDTO.ReasonType.SALUTATION_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getTitle(), ReasonType.TITLE_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getPhone(), ReasonType.PHONE_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getFirstName(), ReasonType.FIRSTNAME_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getLastName(), ReasonType.LASTNAME_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getEmail(), ReasonType.EMAIL_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getPassword(), ReasonType.PASSWORD_MISSING);
        if (registrationDTO.getUserDTO().getDateOfBirth() == null)
            registrationResult.addReason(ReasonType.DATEOFBIRTH_MISSING);

        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getStreet(), ReasonType.STREET_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getHouseNumber(), ReasonType.HOUSENUMBER_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getPostalCode(), ReasonType.POSTALCODE_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getCity(), ReasonType.CITY_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getCountry(), ReasonType.COUNTRY_MISSING);
    }

    private void checkForRequiredCompanyInformation() {
        //check for empty inputs
    }

    private void checkValueAndSetResponse(String value, ReasonType reason){
        if(Utils.stringIsEmptyOrNull(value)) {
            registrationResult.addReason(reason);
        }
    }

    public boolean checkIfEmailAlreadyInUse(String email) {
        UserDTO existingUser = userRepository.findUserByEmail(email);
        return existingUser != null && existingUser.getId() > 0;
    }

    public void checkRepeatedEmail() {
        if(!registrationDTO.getUserDTO().getEmail().equals(registrationDTO.getRepeatedEmail())) {
            registrationResult.addReason(ReasonType.EMAIL_UNEQUAL);
        }
    }

    public void checkRepeatedPassword() {
        if (!registrationDTO.getUserDTO().getPassword().equals(registrationDTO.getRepeatedPassword())) {
            registrationResult.addReason(ReasonType.PASSWORD_UNEQUAL);
        }
    }

    private void validateRequiredCompanyInformation() {
        // validate input
    }

    private void validateRequiredUserInformation() {
        if (!validateEmailInput(registrationDTO.getUserDTO().getEmail())) {
            registrationResult.addReason(ReasonType.EMAIL_INVALID);
        }
        if (!validateNameInput(registrationDTO.getUserDTO().getFirstName())) {
            registrationResult.addReason(ReasonType.FIRSTNAME_INVALID);
        }
        if (!validateNameInput(registrationDTO.getUserDTO().getLastName())) {
            registrationResult.addReason(ReasonType.LASTNAME_INVALID);
        }
    }

    // REGEX for input validation
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validateEmailInput(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private static final Pattern VALID_NAME_REGEX =
            Pattern.compile("^[a-zA-ZßäöüÄÖÜ ,.'-]+$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private boolean validateNameInput(String name) {
        Matcher matcher = VALID_NAME_REGEX.matcher(name);
        return matcher.find();
    }

}
