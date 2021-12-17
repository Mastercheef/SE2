package org.hbrs.se2.project.coll.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabelCompanyTest {

    LabelCompany labelCompany = new LabelCompany();
    @Test
    void getCompanyname() {
        assertEquals("Firmenname:" , labelCompany.getCompanyname().getText());
        assertEquals("200px" , labelCompany.getCompanyname().getWidth());
    }


    @Test
    void getStreet() {
        assertEquals("Strasse:" , labelCompany.getStreet().getText());
    }

    @Test
    void getStreetnumber() {
        assertEquals("Hausnummer:" , labelCompany.getStreetnumber().getText());
    }

    @Test
    void getPostalcode() {
        assertEquals("PLZ:" , labelCompany.getPostalcode().getText());

    }

    @Test
    void getCity() {
        assertEquals("Ort:" , labelCompany.getCity().getText());

    }

    @Test
    void getCountry() {
        assertEquals("Land:" , labelCompany.getCountry().getText());

    }

    @Test
    void getEmail() {
        assertEquals("E-Mail:" , labelCompany.getEmail().getText());

    }

    @Test
    void getPhone() {
        assertEquals("Telefon:" , labelCompany.getPhone().getText());

    }

    @Test
    void getFax() {
        assertEquals("Fax:" , labelCompany.getFax().getText());

    }

    @Test
    void getWebsite() {
        assertEquals("Webseite:" , labelCompany.getWebsite().getText());

    }

    @Test
    void getDescription() {
        assertEquals("Beschreibung:" , labelCompany.getDescription().getText());

    }
}