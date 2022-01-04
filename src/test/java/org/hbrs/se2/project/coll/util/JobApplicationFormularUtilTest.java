package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class JobApplicationFormularUtilTest {

    JobApplicationFormularUtil jobApplicationFormularUtil;

    private String error = "error";
    private String header = "Header";
    private String address = "address";
    private String headLine = "Bewerbung";
    private String name = "sname";
    private LocalDate localDate = LocalDate.of(2000,1,2);
    private String email = "email";
    private String country = "Country";
    private String phone = "phone";
    private String location = "location";
    private String salutation = "Salutation";
    private String title = "Title";
    private String firstName = "Max";
    private String lastName = "Mustermann";
    private String street = "Musterstraße";
    private String houseNumber = "2";
    private String postalCode = "12345";
    private String city = "Musterstadt";


    @Mock
    StudentUser studentUser;

    @Mock
    Address addressEntity;

    @BeforeEach
    void setup() {
        jobApplicationFormularUtil = new JobApplicationFormularUtil();
    }

    @Test
    void getError() {
        jobApplicationFormularUtil.setError(error);
        assertEquals(error , jobApplicationFormularUtil.getError());
    }

    @Test
    void setError() {

        assertNotNull(jobApplicationFormularUtil.getError());
    }

    @Test
    void getPageHeadline() {
        assertEquals( headLine , jobApplicationFormularUtil.getPageHeadline().getText());
        jobApplicationFormularUtil.setPageHeadline(new H2(header));
        assertEquals(header , jobApplicationFormularUtil.getPageHeadline().getText());
    }

    @Test
    void setPageHeadline() {
        assertNotNull(jobApplicationFormularUtil.getPageHeadline());
    }

    @Test
    void getsSalutation() {
        assertTrue(jobApplicationFormularUtil.getsSalutation() instanceof Span);
    }

    @Test
    void setsSalutation() {
        assertNotNull(jobApplicationFormularUtil.getsSalutation());
        jobApplicationFormularUtil.setsSalutation(new Span(salutation));
        assertNotNull(jobApplicationFormularUtil.getsSalutation());

    }

    @Test
    void getsName() {
        assertNotNull(jobApplicationFormularUtil.getsSalutation());
    }

    @Test
    void setsName() {
        jobApplicationFormularUtil.setsName(new Span(name));
        assertNotNull(jobApplicationFormularUtil.getsName());
        assertEquals(name , jobApplicationFormularUtil.getsName().getElement().getText());
    }

    @Test
    void getsDateOfBirth() {
        assertNotNull(jobApplicationFormularUtil.getsDateOfBirth());
    }

    @Test
    void setsDateOfBirth() {

        jobApplicationFormularUtil.setsDateOfBirth(new Span(localDate.toString()));
        assertNotNull(jobApplicationFormularUtil.getsDateOfBirth());
        assertEquals(localDate.toString() , jobApplicationFormularUtil.getsDateOfBirth().getElement().getText());
    }

    @Test
    void getsEmail() {

        jobApplicationFormularUtil.setsEmail(new Span(email));
        assertNotNull(jobApplicationFormularUtil.getsEmail());
        assertEquals(email , jobApplicationFormularUtil.getsEmail().getElement().getText());
    }

    @Test
    void setsEmail() {
        assertNotNull(jobApplicationFormularUtil.getsEmail().getElement().getText());
    }

    @Test
    void getsPhone() {


        jobApplicationFormularUtil.setsPhone(new Span(phone));
        assertEquals(phone , jobApplicationFormularUtil.getsPhone().getElement().getText());
    }

    @Test
    void setsPhone() {
        assertEquals("" , jobApplicationFormularUtil.getsPhone().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsPhone());
    }

    @Test
    void getsAddress() {

        jobApplicationFormularUtil.setsAddress(new Span(address));
        assertNotNull(jobApplicationFormularUtil.getsAddress());
        assertEquals(address , jobApplicationFormularUtil.getsAddress().getElement().getText());
    }

    @Test
    void setsAddress() {
        jobApplicationFormularUtil.setsAddress(new Span());
        assertNotNull(jobApplicationFormularUtil.getsAddress());
    }

    @Test
    void getsLocation() {

        jobApplicationFormularUtil.setsLocation(new Span(location));
        assertNotNull(jobApplicationFormularUtil.getsLocation());
        assertEquals(location , jobApplicationFormularUtil.getsLocation().getElement().getText());

    }

    @Test
    void setsLocation() {
        jobApplicationFormularUtil.setsLocation(new Span());
        assertNotNull(jobApplicationFormularUtil.getsLocation());
    }

    @Test
    void getsCountry() {
        jobApplicationFormularUtil.setsCountry(new Span(country));
        assertNotNull(jobApplicationFormularUtil.getsCountry());
        assertEquals(country , jobApplicationFormularUtil.getsCountry().getElement().getText());
    }

    @Test
    void setsCountry() {

        jobApplicationFormularUtil.setsCountry(new Span());
        assertNotNull(jobApplicationFormularUtil.getsCountry());
    }

    @Test
    void setErrorFields() {
        ArrayList<JobApplicationResultDTO.ReasonType> arrayList = new ArrayList<JobApplicationResultDTO.ReasonType>();
        arrayList.add(  JobApplicationResultDTO.ReasonType.HEADLINE_MISSING);

        jobApplicationFormularUtil.setErrorFields(arrayList , new TextField() , new TextArea());
        assertEquals("\"Fehler\"" , jobApplicationFormularUtil.getError());

        arrayList.clear();
        arrayList.add(JobApplicationResultDTO.ReasonType.TEXT_MISSING);
        jobApplicationFormularUtil.setErrorFields(arrayList , new TextField() , new TextArea());
        assertEquals("\"Fehler\"" , jobApplicationFormularUtil.getError());



    }


    @Test
    void loadStudentUserInfo(){

        studentUser.setAddress(addressEntity);

        mockLoadStudentUser();

        jobApplicationFormularUtil.loadStudentUserInfo(studentUser);

        assertNotNull(jobApplicationFormularUtil.getsSalutation());
        assertEquals(salutation + " " + title , jobApplicationFormularUtil.getsSalutation().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsName());
        assertEquals(firstName + " " + lastName , jobApplicationFormularUtil.getsName().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsDateOfBirth());
        assertEquals(Utils.convertToGermanDateFormat(localDate) , jobApplicationFormularUtil.getsDateOfBirth().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsEmail());
        assertEquals(email , jobApplicationFormularUtil.getsEmail().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsPhone());
        assertEquals(phone , jobApplicationFormularUtil.getsPhone().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsAddress());
        assertEquals(street + " " + houseNumber , jobApplicationFormularUtil.getsAddress().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsLocation());
        assertEquals (postalCode + " " + city , jobApplicationFormularUtil.getsLocation().getElement().getText());
        assertNotNull(jobApplicationFormularUtil.getsCountry());
        assertEquals(country , jobApplicationFormularUtil.getsCountry().getElement().getText());

    }

    private void mockLoadStudentUser() {
        studentUser.setAddress(addressEntity);

        when(studentUser.getAddress()).thenReturn(addressEntity);
        when(studentUser.getSalutation()).thenReturn(salutation);
        when(studentUser.getTitle()).thenReturn(title);
        when(studentUser.getFirstName()).thenReturn(firstName);
        when(studentUser.getLastName()).thenReturn(lastName);
        when(studentUser.getEmail()).thenReturn(email);
        when(studentUser.getPhone()).thenReturn(phone);
        when(studentUser.getDateOfBirth()).thenReturn(localDate);


        when(studentUser.getAddress().getHouseNumber()).thenReturn(houseNumber);
        when(studentUser.getAddress().getPostalCode()).thenReturn(postalCode);
        when(studentUser.getAddress().getStreet()).thenReturn(street);
        when(studentUser.getAddress().getCity()).thenReturn(city);
        when(studentUser.getAddress().getCountry()).thenReturn(country);
    }





}