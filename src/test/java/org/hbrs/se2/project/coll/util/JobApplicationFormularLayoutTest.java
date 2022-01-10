package org.hbrs.se2.project.coll.util;


import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.layout.JobApplicationFormularLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationFormularLayoutTest {

    JobApplicationFormularLayout jobApplicationFormularLayout;

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
        jobApplicationFormularLayout = new JobApplicationFormularLayout();
    }

    @Test
    void getError() {
        jobApplicationFormularLayout.setError(error);
        assertEquals(error , jobApplicationFormularLayout.getError());
    }

    @Test
    void setError() {

        assertNotNull(jobApplicationFormularLayout.getError());
    }

    @Test
    void getPageHeadline() {
        assertEquals( headLine , jobApplicationFormularLayout.getPageHeadline().getText());
        jobApplicationFormularLayout.setPageHeadline(new H2(header));
        assertEquals(header , jobApplicationFormularLayout.getPageHeadline().getText());
    }

    @Test
    void setPageHeadline() {
        assertNotNull(jobApplicationFormularLayout.getPageHeadline());
    }

    @Test
    void getsSalutation() {
        assertTrue(jobApplicationFormularLayout.getsSalutation() instanceof Span);
    }

    @Test
    void setsSalutation() {
        assertNotNull(jobApplicationFormularLayout.getsSalutation());
        jobApplicationFormularLayout.setsSalutation(new Span(salutation));
        assertNotNull(jobApplicationFormularLayout.getsSalutation());

    }

    @Test
    void getsName() {
        assertNotNull(jobApplicationFormularLayout.getsSalutation());
    }

    @Test
    void setsName() {
        jobApplicationFormularLayout.setsName(new Span(name));
        assertNotNull(jobApplicationFormularLayout.getsName());
        assertEquals(name , jobApplicationFormularLayout.getsName().getElement().getText());
    }

    @Test
    void getsDateOfBirth() {
        assertNotNull(jobApplicationFormularLayout.getsDateOfBirth());
    }

    @Test
    void setsDateOfBirth() {

        jobApplicationFormularLayout.setsDateOfBirth(new Span(localDate.toString()));
        assertNotNull(jobApplicationFormularLayout.getsDateOfBirth());
        assertEquals(localDate.toString() , jobApplicationFormularLayout.getsDateOfBirth().getElement().getText());
    }

    @Test
    void getsEmail() {

        jobApplicationFormularLayout.setsEmail(new Span(email));
        assertNotNull(jobApplicationFormularLayout.getsEmail());
        assertEquals(email , jobApplicationFormularLayout.getsEmail().getElement().getText());
    }

    @Test
    void setsEmail() {
        assertNotNull(jobApplicationFormularLayout.getsEmail().getElement().getText());
    }

    @Test
    void getsPhone() {


        jobApplicationFormularLayout.setsPhone(new Span(phone));
        assertEquals(phone , jobApplicationFormularLayout.getsPhone().getElement().getText());
    }

    @Test
    void setsPhone() {
        assertEquals("" , jobApplicationFormularLayout.getsPhone().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsPhone());
    }

    @Test
    void getsAddress() {

        jobApplicationFormularLayout.setsAddress(new Span(address));
        assertNotNull(jobApplicationFormularLayout.getsAddress());
        assertEquals(address , jobApplicationFormularLayout.getsAddress().getElement().getText());
    }

    @Test
    void setsAddress() {
        jobApplicationFormularLayout.setsAddress(new Span());
        assertNotNull(jobApplicationFormularLayout.getsAddress());
    }

    @Test
    void getsLocation() {

        jobApplicationFormularLayout.setsLocation(new Span(location));
        assertNotNull(jobApplicationFormularLayout.getsLocation());
        assertEquals(location , jobApplicationFormularLayout.getsLocation().getElement().getText());

    }

    @Test
    void setsLocation() {
        jobApplicationFormularLayout.setsLocation(new Span());
        assertNotNull(jobApplicationFormularLayout.getsLocation());
    }

    @Test
    void getsCountry() {
        jobApplicationFormularLayout.setsCountry(new Span(country));
        assertNotNull(jobApplicationFormularLayout.getsCountry());
        assertEquals(country , jobApplicationFormularLayout.getsCountry().getElement().getText());
    }

    @Test
    void setsCountry() {

        jobApplicationFormularLayout.setsCountry(new Span());
        assertNotNull(jobApplicationFormularLayout.getsCountry());
    }

    @Test
    void setErrorFields() {
        ArrayList<JobApplicationResultDTO.ReasonType> arrayList = new ArrayList<>();
        arrayList.add(  JobApplicationResultDTO.ReasonType.HEADLINE_MISSING);

        jobApplicationFormularLayout.setErrorFields(arrayList , new TextField() , new TextArea());
        assertEquals("\"Fehler\"" , jobApplicationFormularLayout.getError());

        arrayList.clear();
        arrayList.add(JobApplicationResultDTO.ReasonType.TEXT_MISSING);
        jobApplicationFormularLayout.setErrorFields(arrayList , new TextField() , new TextArea());
        assertEquals("\"Fehler\"" , jobApplicationFormularLayout.getError());


    }


    @Test
    void loadStudentUserInfo(){

        studentUser.setAddress(addressEntity);

        mockLoadStudentUser();

        jobApplicationFormularLayout.loadStudentUserInfo(studentUser);

        assertNotNull(jobApplicationFormularLayout.getsSalutation());
        assertEquals(salutation + " " + title , jobApplicationFormularLayout.getsSalutation().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsName());
        assertEquals(firstName + " " + lastName , jobApplicationFormularLayout.getsName().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsDateOfBirth());
        assertEquals(Utils.convertToGermanDateFormat(localDate) , jobApplicationFormularLayout.getsDateOfBirth().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsEmail());
        assertEquals(email , jobApplicationFormularLayout.getsEmail().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsPhone());
        assertEquals(phone , jobApplicationFormularLayout.getsPhone().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsAddress());
        assertEquals(street + " " + houseNumber , jobApplicationFormularLayout.getsAddress().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsLocation());
        assertEquals (postalCode + " " + city , jobApplicationFormularLayout.getsLocation().getElement().getText());
        assertNotNull(jobApplicationFormularLayout.getsCountry());
        assertEquals(country , jobApplicationFormularLayout.getsCountry().getElement().getText());

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



    @Test
    void personalInformationVerticalLayout() {
        JobApplicationFormularLayout jobAppFormLayout = new JobApplicationFormularLayout();
        assertEquals(VerticalLayout.class , jobAppFormLayout.personalInformationVerticalLayout().getClass());

    }


}