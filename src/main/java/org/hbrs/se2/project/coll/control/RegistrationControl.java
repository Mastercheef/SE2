package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.*;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO.ReasonType;
import org.hbrs.se2.project.coll.dtos.impl.RegistrationResultDTOImpl;
import org.hbrs.se2.project.coll.entities.*;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegistrationControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationControl.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressControl addressControl;
    @Autowired
    CompanyControl companyControl;
    @Autowired
    StudentUserControl studentUserControl;
    @Autowired
    ContactPersonControl contactPersonControl;
    @Autowired
    SettingsControl settingsControl;

    private RegistrationDTO registrationDTO;
    private RegistrationResultDTOImpl registrationResult;

    @Transactional
    public RegistrationResultDTO registerUser(RegistrationDTO registrationDTO) throws DatabaseUserException {

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
                if (checkIfCompanyAlreadyRegistered(registrationDTO.getCompanyDTO())) {
                    registrationResult.addReason(ReasonType.COMPANY_ALREADY_REGISTERED);
                }
            }

            if (registrationResult.getReasons().isEmpty()) {

                if (registrationDTO.getUserDTO().getType().equals("st")) {
                    studentUserControl.createNewStudentUser(registrationDTO.getUserDTO());
                }

                if (registrationDTO.getUserDTO().getType().equals("cp")) {
                    Company savedCompany = companyControl.saveCompany(registrationDTO.getCompanyDTO());
                    contactPersonControl.createNewContactPerson(registrationDTO.getUserDTO(), savedCompany);
                }

                registrationResult.addReason(ReasonType.SUCCESS);
                registrationResult.setResult(true);
            } else {
                registrationResult.setResult(false);
            }


        } catch (Exception exception) {
            LOGGER.info("LOG : {}" , exception.getMessage());
            registrationResult.setResult(false);
            registrationResult.addReason(ReasonType.UNEXPECTED_ERROR);

        }
        return registrationResult;
    }

    protected void checkForRequiredUserInformation() {
        checkValueAndSetResponse(registrationDTO.getUserDTO().getSalutation(), RegistrationResultDTO.ReasonType.SALUTATION_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getTitle(), ReasonType.TITLE_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getFirstName(), ReasonType.FIRSTNAME_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getLastName(), ReasonType.LASTNAME_MISSING);
        if (registrationDTO.getUserDTO().getDateOfBirth() == null)
            registrationResult.addReason(ReasonType.DATEOFBIRTH_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getPhone(), ReasonType.PHONE_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getEmail(), ReasonType.EMAIL_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getPassword(), ReasonType.PASSWORD_MISSING);

        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getStreet(), ReasonType.STREET_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getHouseNumber(), ReasonType.HOUSENUMBER_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getPostalCode(), ReasonType.POSTALCODE_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getCity(), ReasonType.CITY_MISSING);
        checkValueAndSetResponse(registrationDTO.getUserDTO().getAddress().getCountry(), ReasonType.COUNTRY_MISSING);
    }

    protected void checkForRequiredCompanyInformation() {
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getCompanyName(), ReasonType.COMPANY_NAME_MISSING);
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getEmail(), ReasonType.COMPANY_EMAIL_MISSING);
        checkValueAndSetResponse(String.valueOf(registrationDTO.getCompanyDTO().getPhoneNumber()), ReasonType.COMPANY_PHONE_MISSING);
        checkValueAndSetResponse(String.valueOf(registrationDTO.getCompanyDTO().getFaxNumber()), ReasonType.COMPANY_FAX_MISSING);
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getWebsite(), ReasonType.COMPANY_WEBSITE_MISSING);
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getDescription(), ReasonType.COMPANY_DESCRIPTION_MISSING);

        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getAddress().getStreet(), ReasonType.COMPANY_STREET_MISSING);
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getAddress().getHouseNumber(), ReasonType.COMPANY_HOUSENUMBER_MISSING);
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getAddress().getPostalCode(), ReasonType.COMPANY_POSTALCODE_MISSING);
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getAddress().getCity(), ReasonType.COMPANY_CITY_MISSING);
        checkValueAndSetResponse(registrationDTO.getCompanyDTO().getAddress().getCountry(), ReasonType.COMPANY_COUNTRY_MISSING);
    }

    protected void checkValueAndSetResponse(String value, ReasonType reason){
        if(Utils.stringIsEmptyOrNull(value)) {
            registrationResult.addReason(reason);
        }
    }

    public boolean checkIfEmailAlreadyInUse(String email) {
        UserDTO existingUser = userRepository.findUserByEmail(email);
        return existingUser != null && existingUser.getId() > 0;
    }

    public boolean checkIfCompanyAlreadyRegistered(CompanyDTO companyDTO) {
        CompanyDTO existingCompany = companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                companyDTO.getCompanyName(),
                companyDTO.getEmail(),
                companyDTO.getWebsite()
        );
        return existingCompany != null && existingCompany.getId() > 0;
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

    protected void validateRequiredUserInformation() {
        if (!Globals.Regex.validateEmailInput(registrationDTO.getUserDTO().getEmail())) {
            registrationResult.addReason(ReasonType.EMAIL_INVALID);
        }
        if (!Globals.Regex.validateNameInput(registrationDTO.getUserDTO().getFirstName())) {
            registrationResult.addReason(ReasonType.FIRSTNAME_INVALID);
        }
        if (!Globals.Regex.validateNameInput(registrationDTO.getUserDTO().getLastName())) {
            registrationResult.addReason(ReasonType.LASTNAME_INVALID);
        }
    }

    protected void validateRequiredCompanyInformation() {
        if (!Globals.Regex.validateEmailInput(registrationDTO.getCompanyDTO().getEmail())) {
            registrationResult.addReason(ReasonType.COMPANY_EMAIL_INVALID);
        }
    }
}
