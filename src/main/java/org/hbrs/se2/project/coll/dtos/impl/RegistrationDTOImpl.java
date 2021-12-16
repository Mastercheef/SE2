package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.RegistrationDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;

public class RegistrationDTOImpl implements RegistrationDTO {
    private UserDTO userDTO;
    private String repeatedEmail;
    private String repeatedPassword;
    private CompanyDTO companyDTO;

    public RegistrationDTOImpl () {}

    public RegistrationDTOImpl (UserDTO userDTO, String repeatEmail, String repeatedPassword) {
        this.setUserDTO(userDTO);
        this.setRepeatedEmail(repeatEmail);
        this.setRepeatedPassword(repeatedPassword);
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setRepeatedEmail(String repeatedEmail) {
        this.repeatedEmail = repeatedEmail;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }

    @Override
    public UserDTO getUserDTO() {
        return userDTO;
    }

    @Override
    public String getRepeatedEmail() {
        return repeatedEmail;
    }

    @Override
    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    @Override
    public CompanyDTO getCompanyDTO() { return companyDTO; }
}
