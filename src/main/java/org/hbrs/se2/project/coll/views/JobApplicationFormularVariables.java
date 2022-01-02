package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;

public class JobApplicationFormularVariables {

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
}
