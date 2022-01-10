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

    private final static String POSTALCODE = "56789";

    @Test
    void createCompanyProfile() {
        Company companyProfile;

        when(companyDTO.getCompanyName()).thenReturn("Mustermann Corp.");
        when(companyDTO.getAddress()).thenReturn(mock(Address.class));
        when(companyDTO.getId()).thenReturn(100);
        when(companyDTO.getDescription()).thenReturn("Mustermann Description");
        when(companyDTO.getEmail()).thenReturn("musterman@email.de");
        when(companyDTO.getWebsite()).thenReturn("mustermann.de");
        when(companyDTO.getFaxNumber()).thenReturn(POSTALCODE);
        when(companyDTO.getPhoneNumber()).thenReturn("01234");

        companyProfile = CompanyFactory.createCompany(companyDTO);

        assertEquals("Mustermann Corp." , companyProfile.getCompanyName());
        assertNotNull(companyProfile.getAddress());
        assertEquals(100, companyProfile.getId());
        assertEquals("Mustermann Description" , companyProfile.getDescription());
        assertEquals("musterman@email.de" , companyProfile.getEmail());
        assertEquals("mustermann.de" , companyProfile.getWebsite());
        assertEquals(POSTALCODE , companyProfile.getFaxNumber());
        assertEquals("01234" , companyProfile.getPhoneNumber());
    }

    @Test
    void testCreateTestCompany() {
        CompanyDTOImpl compDTO = new CompanyDTOImpl();
        compDTO.setCompanyName("Firma");
        Address address = new Address();
        address.setStreet("Straße");
        address.setHouseNumber("10");
        address.setPostalCode(POSTALCODE);
        address.setCity("Bonn");
        address.setCountry("Deutschland");
        compDTO.setAddress(address);
        compDTO.setPhoneNumber("12345");
        compDTO.setFaxNumber("54321");
        compDTO.setEmail("valid@email.de");
        compDTO.setWebsite("www.website.com");
        compDTO.setDescription("Beschreibung");

        CompanyDTOImpl factoryDTO = CompanyFactory.createTestCompany();

        assertEquals(compDTO.getCompanyName(), factoryDTO.getCompanyName());
        assertEquals(compDTO.getAddress().getStreet(), factoryDTO.getAddress().getStreet());
        assertEquals(compDTO.getAddress().getHouseNumber(), factoryDTO.getAddress().getHouseNumber());
        assertEquals(compDTO.getAddress().getPostalCode(), factoryDTO.getAddress().getPostalCode());
        assertEquals(compDTO.getAddress().getCity(), factoryDTO.getAddress().getCity());
        assertEquals(compDTO.getAddress().getCountry(), factoryDTO.getAddress().getCountry());
        assertEquals(compDTO.getPhoneNumber(), factoryDTO.getPhoneNumber());
        assertEquals(compDTO.getFaxNumber(), factoryDTO.getFaxNumber());
        assertEquals(compDTO.getEmail(), factoryDTO.getEmail());
        assertEquals(compDTO.getWebsite(), factoryDTO.getWebsite());
        assertEquals(compDTO.getDescription(), factoryDTO.getDescription());
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