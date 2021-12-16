package org.hbrs.se2.project.coll.dtos;

public interface RegistrationDTO {
    public UserDTO getUserDTO();
    public String getRepeatedEmail();
    public String getRepeatedPassword();
    public CompanyDTO getCompanyDTO();
}
