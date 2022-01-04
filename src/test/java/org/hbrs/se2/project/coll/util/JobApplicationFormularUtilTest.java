package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationFormularUtilTest {

    JobApplicationFormularUtil jobApplicationFormularUtil;

    private String error = "error";
    private String header = "Header";

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
        assertEquals("Bewerbung" , jobApplicationFormularUtil.getPageHeadline().getText());
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
        jobApplicationFormularUtil.setsSalutation(new Span("Salutation"));
        assertNotNull(jobApplicationFormularUtil.getsSalutation());

    }

    @Test
    void getsName() {
        assertNotNull(jobApplicationFormularUtil.getsSalutation());
    }

    @Test
    void setsName() {
        String name = "sname";
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
        LocalDate localDate = LocalDate.of(2000,1,2);
        jobApplicationFormularUtil.setsDateOfBirth(new Span(localDate.toString()));
        assertNotNull(jobApplicationFormularUtil.getsDateOfBirth());
        assertEquals(localDate.toString() , jobApplicationFormularUtil.getsDateOfBirth().getElement().getText());
    }

    @Test
    void getsEmail() {
        String email = "email";
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

        String phone = "phone";
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
        String address = "address";
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
        String location = "location";
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
        String country = "Country";
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
    }
}