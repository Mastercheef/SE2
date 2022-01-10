package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.hbrs.se2.project.coll.entities.StudentUser;

import java.util.List;

public class JobApplicationFormularUtil {

    private String error = "\"Fehler\"";


    private H2 pageHeadline = new H2("Bewerbung");

    private Span sSalutation = new Span();
    private Span sName = new Span();
    private Span sDateOfBirth = new Span();


    private Span sEmail = new Span();
    private Span sPhone = new Span();

    private Span sAddress = new Span();
    private Span sLocation = new Span();
    private Span sCountry = new Span();

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public H2 getPageHeadline() {
        return pageHeadline;
    }

    public void setPageHeadline(H2 pageHeadline) {
        this.pageHeadline = pageHeadline;
    }

    public Span getsSalutation() {
        return sSalutation;
    }

    public void setsSalutation(Span sSalutation) {
        this.sSalutation = sSalutation;
    }

    public Span getsName() {
        return sName;
    }

    public void setsName(Span sName) {
        this.sName = sName;
    }

    public Span getsDateOfBirth() {
        return sDateOfBirth;
    }

    public void setsDateOfBirth(Span sDateOfBirth) {
        this.sDateOfBirth = sDateOfBirth;
    }

    public Span getsEmail() {
        return sEmail;
    }

    public void setsEmail(Span sEmail) {
        this.sEmail = sEmail;
    }

    public Span getsPhone() {
        return sPhone;
    }

    public void setsPhone(Span sPhone) {
        this.sPhone = sPhone;
    }

    public Span getsAddress() {
        return sAddress;
    }

    public void setsAddress(Span sAddress) {
        this.sAddress = sAddress;
    }

    public Span getsLocation() {
        return sLocation;
    }

    public void setsLocation(Span sLocation) {
        this.sLocation = sLocation;
    }

    public Span getsCountry() {
        return sCountry;
    }

    public void setsCountry(Span sCountry) {
        this.sCountry = sCountry;
    }

    public void setErrorFields(List<JobApplicationResultDTO.ReasonType> reasons, TextField headline, TextArea text) {
        for (JobApplicationResultDTO.ReasonType reason : reasons) {
            if (reason == JobApplicationResultDTO.ReasonType.UNEXPECTED_ERROR) {
                Utils.triggerDialogMessage(Globals.View.ERROR, "Es ist ein unerwarteter Fehler aufgetreten");
            }
            if (reason == JobApplicationResultDTO.ReasonType.HEADLINE_MISSING) {
                headline.setErrorMessage("Bitte geben Sie einen Betreff ein");
                headline.setInvalid(true);
            }
            if (reason == JobApplicationResultDTO.ReasonType.TEXT_MISSING) {
                text.setErrorMessage("Bitte geben Sie einen Bewerbungstext ein");
                text.setInvalid(true);
            }
        }
    }

    public void loadStudentUserInfo(StudentUser studentUser) {
        sSalutation = new Span(studentUser.getSalutation() + " " + studentUser.getTitle());
        sName = new Span(studentUser.getFirstName() + " " + studentUser.getLastName());
        sDateOfBirth = new Span(Utils.convertToGermanDateFormat(studentUser.getDateOfBirth()));
        sEmail = new Span(studentUser.getEmail());
        sPhone = new Span(studentUser.getPhone());

        sAddress = new Span(studentUser.getAddress().getStreet() + " " + studentUser.getAddress().getHouseNumber());
        sLocation = new Span(studentUser.getAddress().getPostalCode() + " " + studentUser.getAddress().getCity());
        sCountry = new Span(studentUser.getAddress().getCountry());
    }

    public VerticalLayout personalInformationVerticalLayout() {
        VerticalLayout personalInformation = new VerticalLayout(sSalutation, sName, sAddress, sLocation, sCountry, sEmail, sPhone);
        personalInformation.setSpacing(false);
        personalInformation.setPadding(false);
        return personalInformation;
    }
}
