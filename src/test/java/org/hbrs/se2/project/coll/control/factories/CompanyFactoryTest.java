package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Company;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CompanyFactoryTest {

    private final String errorMessage = "class org.hbrs.se2.project.coll.control.factories.CompanyFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.CompanyFactory with modifiers \"private\"";

    @Mock
    private CompanyDTO companyDTO;

    @Test
    void createCompanyProfile() {
        Company companyProfile;

        when(companyDTO.getCompanyName()).thenReturn("Mustermann Corp.");
        when(companyDTO.getAddress()).thenReturn(mock(Address.class));
        when(companyDTO.getId()).thenReturn(100);
        when(companyDTO.getDescription()).thenReturn("Mustermann Description");
        when(companyDTO.getEmail()).thenReturn("musterman@email.de");
        when(companyDTO.getWebsite()).thenReturn("mustermann.de");
        when(companyDTO.getFaxNumber()).thenReturn(56789);
        when(companyDTO.getPhoneNumber()).thenReturn(01234);

        companyProfile = CompanyFactory.createCompany(companyDTO);

        assertEquals("Mustermann Corp." , companyProfile.getCompanyName());
        assertNotNull(companyProfile.getAddress());
        assertEquals(100, companyProfile.getId());
        assertEquals("Mustermann Description" , companyProfile.getDescription());
        assertEquals("musterman@email.de" , companyProfile.getEmail());
        assertEquals("mustermann.de" , companyProfile.getWebsite());
        assertEquals(56789 , companyProfile.getFaxNumber());
        assertEquals(01234 , companyProfile.getPhoneNumber());
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingCompanyFactory() throws NoSuchMethodException {
        Constructor<CompanyFactory> constructor = CompanyFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }
}