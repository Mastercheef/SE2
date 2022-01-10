package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
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

    private static final String ERROR_MESSAGE = "class org.hbrs.se2.project.coll.control.factories.CompanyFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.CompanyFactory with modifiers \"private\"";

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
        when(companyDTO.getFaxNumber()).thenReturn("56789");
        when(companyDTO.getPhoneNumber()).thenReturn("01234");

        companyProfile = CompanyFactory.createCompany(companyDTO);

        assertEquals("Mustermann Corp." , companyProfile.getCompanyName());
        assertNotNull(companyProfile.getAddress());
        assertEquals(100, companyProfile.getId());
        assertEquals("Mustermann Description" , companyProfile.getDescription());
        assertEquals("musterman@email.de" , companyProfile.getEmail());
        assertEquals("mustermann.de" , companyProfile.getWebsite());
        assertEquals("56789" , companyProfile.getFaxNumber());
        assertEquals("01234" , companyProfile.getPhoneNumber());
    }

    @Test
    void testCreateTestCompany() {
        CompanyDTOImpl companyDTO = new CompanyDTOImpl();
        companyDTO.setCompanyName("Firma");
        Address address = new Address();
        address.setStreet("Straße");
        address.setHouseNumber("10");
        address.setPostalCode("56789");
        address.setCity("Bonn");
        address.setCountry("Deutschland");
        companyDTO.setAddress(address);
        companyDTO.setPhoneNumber("12345");
        companyDTO.setFaxNumber("54321");
        companyDTO.setEmail("valid@email.de");
        companyDTO.setWebsite("www.website.com");
        companyDTO.setDescription("Beschreibung");

        CompanyDTOImpl factoryDTO = CompanyFactory.createTestCompany();

        assertEquals(companyDTO.getCompanyName(), factoryDTO.getCompanyName());
        assertEquals(companyDTO.getAddress().getStreet(), factoryDTO.getAddress().getStreet());
        assertEquals(companyDTO.getAddress().getHouseNumber(), factoryDTO.getAddress().getHouseNumber());
        assertEquals(companyDTO.getAddress().getPostalCode(), factoryDTO.getAddress().getPostalCode());
        assertEquals(companyDTO.getAddress().getCity(), factoryDTO.getAddress().getCity());
        assertEquals(companyDTO.getAddress().getCountry(), factoryDTO.getAddress().getCountry());
        assertEquals(companyDTO.getPhoneNumber(), factoryDTO.getPhoneNumber());
        assertEquals(companyDTO.getFaxNumber(), factoryDTO.getFaxNumber());
        assertEquals(companyDTO.getEmail(), factoryDTO.getEmail());
        assertEquals(companyDTO.getWebsite(), factoryDTO.getWebsite());
        assertEquals(companyDTO.getDescription(), factoryDTO.getDescription());
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingCompanyFactory() throws NoSuchMethodException {
        Constructor<CompanyFactory> constructor = CompanyFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(ERROR_MESSAGE, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }
}