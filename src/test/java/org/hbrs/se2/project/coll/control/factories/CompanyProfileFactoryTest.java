package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CompanyProfileFactoryTest {

    @Mock
    private CompanyProfileDTO companyProfileDTO;

    @Test
    void createCompanyProfile() {
        CompanyProfile companyProfile;

        when(companyProfileDTO.getCompanyName()).thenReturn("Mustermann Corp.");
        when(companyProfileDTO.getAddress()).thenReturn(mock(Address.class));
        when(companyProfileDTO.getId()).thenReturn(100);
        when(companyProfileDTO.getDescription()).thenReturn("Mustermann Description");
        when(companyProfileDTO.getEmail()).thenReturn("musterman@email.de");
        when(companyProfileDTO.getWebsite()).thenReturn("mustermann.de");
        when(companyProfileDTO.getFaxNumber()).thenReturn(56789);
        when(companyProfileDTO.getPhoneNumber()).thenReturn(01234);

        companyProfile = CompanyProfileFactory.createCompanyProfile(companyProfileDTO);

        assertEquals("Mustermann Corp." , companyProfile.getCompanyName());
        assertNotNull(companyProfile.getAddress());
        assertEquals(100, companyProfile.getId());
        assertEquals("Mustermann Description" , companyProfile.getDescription());
        assertEquals("musterman@email.de" , companyProfile.getEmail());
        assertEquals("mustermann.de" , companyProfile.getWebsite());
        assertEquals(56789 , companyProfile.getFaxNumber());
        assertEquals(01234 , companyProfile.getPhoneNumber());

    }
}