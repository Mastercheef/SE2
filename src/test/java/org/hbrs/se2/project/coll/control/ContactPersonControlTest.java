package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.builder.UserDTOBuilder;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.ContactPersonDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Company;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DataAccessResourceFailureException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContactPersonControlTest {

    @InjectMocks
    private ContactPersonControl contactPersonControl;

    @Mock
    private ContactPersonRepository contactPersonRepository;
    @Mock
    private AddressControl addressControl;

    Address address = Mockito.mock(Address.class);
    Address addressReturn = Mockito.mock(Address.class);

    ContactPerson contactPerson = Mockito.mock(ContactPerson.class);
    ContactPerson savedContactPerson = Mockito.mock(ContactPerson.class);
    ContactPerson factoryContactPerson = Mockito.mock(ContactPerson.class);
    ContactPerson correctContactPerson = Mockito.mock(ContactPerson.class);
    ContactPersonDTO contactPersonDTO = Mockito.mock(ContactPersonDTO.class);

    UserDTO userDTO = Mockito.mock(UserDTO.class);
    Company company = Mockito.mock(Company.class);

    @Test
    void findContactPersonById() {
        when(contactPersonRepository.findContactPersonById(100)).thenReturn(contactPerson);
        assertNotNull(contactPersonControl.findContactPersonById(100));
        assertTrue(contactPersonControl.findContactPersonById(100) instanceof  ContactPerson);
    }

    @Test
    void testCreateNewContactPerson() throws DatabaseUserException {
        UserDTO userBuild = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withType("cp")
                .done();
        when(addressControl.checkAddressExistence(any())).thenReturn(addressReturn);
        when(contactPersonRepository.save(any())).then(returnsFirstArg());
        ContactPerson resultContactPerson = contactPersonControl.createNewContactPerson(userBuild, company);
        assertEquals("admin", resultContactPerson.getRole());
        assertEquals(userBuild.getFirstName(), resultContactPerson.getFirstName());
        assertEquals(userBuild.getLastName(), resultContactPerson.getLastName());
        assertEquals(addressReturn, resultContactPerson.getAddress());
        assertEquals(company, resultContactPerson.getCompany());
        assertNotNull(resultContactPerson.getSettings());
    }

    @Test
    void testUpdateContactPerson() throws DatabaseUserException {
        when(correctContactPerson.getAddress()).thenReturn(addressReturn);
        when(correctContactPerson.getId()).thenReturn(1);
        when(factoryContactPerson.getId()).thenReturn(1);
        when(factoryContactPerson.getAddress()).thenReturn(addressReturn);

        when(contactPersonDTO.getAddress()).thenReturn(address);
        when(addressControl.checkAddressExistence(address)).thenReturn(addressReturn);
        try (MockedStatic<UserFactory> mockFactory = Mockito.mockStatic(UserFactory.class)) {
            mockFactory.when(() -> UserFactory.createContactPerson(contactPersonDTO)).thenReturn(factoryContactPerson);
            when(contactPersonRepository.save(factoryContactPerson)).thenReturn(correctContactPerson);

            ContactPerson resultContactPerson = contactPersonControl.updateContactPerson(contactPersonDTO);
            assertEquals(factoryContactPerson.getId(), resultContactPerson.getId());
            assertEquals(factoryContactPerson.getAddress(), resultContactPerson.getAddress());
        }

    }

    @Test
    void testSaveContactPersonPositiveExistingUser() throws DatabaseUserException {
        when(contactPerson.getId()).thenReturn(10);
        when(contactPersonRepository.save(contactPerson)).thenReturn(savedContactPerson);

        ContactPerson resultContactPerson = contactPersonControl.saveContactPerson(contactPerson);
        assertEquals(savedContactPerson, resultContactPerson);
    }

    @Test
    void testSaveContactPersonPositiveNewUser() throws DatabaseUserException {
        when(contactPerson.getId()).thenReturn(0);
        when(contactPersonRepository.save(contactPerson)).thenReturn(savedContactPerson);

        ContactPerson resultContactPerson = contactPersonControl.saveContactPerson(contactPerson);
        assertEquals(savedContactPerson, resultContactPerson);
    }

    @Test
    void testSaveContactPersonNegativeDARFE() {
        when(contactPersonRepository.save(contactPerson)).thenThrow(DataAccessResourceFailureException.class);

        DatabaseUserException thrown = Assertions.assertThrows( DatabaseUserException.class, () ->
                contactPersonControl.saveContactPerson(contactPerson));
        assertEquals("Während der Verbindung zur Datenbank mit JPA ist ein Fehler aufgetreten.",
                thrown.getMessage());
    }

    @Test
    void testSaveContactPersonNegativeException() {
        when(contactPersonRepository.save(contactPerson)).thenThrow(RuntimeException.class);

        DatabaseUserException thrown = Assertions.assertThrows( DatabaseUserException.class, () ->
                contactPersonControl.saveContactPerson(contactPerson));
        assertEquals("Es ist ein unerwarteter Fehler aufgetreten.", thrown.getMessage());
    }

    @Test
    void handleAddressExistance() {
        when(addressControl.checkAddressExistence(address)).thenReturn(addressReturn);
        assertNotNull(contactPersonControl.handleAddressExistance(address));
        assertEquals(addressReturn , contactPersonControl.handleAddressExistance(address));
    }

    @Test
    void checkIfUserIsProfileOwner() {
        when(contactPersonRepository.findContactPersonByCompanyId(200)).thenReturn(contactPerson);
        when(userDTO.getId()).thenReturn(100);
        when(contactPersonDTO.getId()).thenReturn(200);
        assertFalse(contactPersonControl.checkIfUserIsProfileOwner(userDTO, 200));
        when(userDTO.getId()).thenReturn(200);
        assertTrue(contactPersonControl.checkIfUserIsProfileOwner(userDTO, 200));
        when(userDTO.getId()).thenReturn(200);
        when(contactPersonDTO.getId()).thenReturn(300);
        assertTrue(contactPersonControl.checkIfUserIsProfileOwner(userDTO, 200));
    }
}