package org.hbrs.se2.project.coll.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabelCompanyTest {

    LabelCompany labelCompany = new LabelCompany();
    @Test
    void getCompanyname() {
        assertEquals("Firmenname:" , labelCompany.getCompanyname().getText());
    }


    @Test
    void getStreet() {
    }

    @Test
    void getStreetnumber() {
    }

    @Test
    void getPostalcode() {
    }

    @Test
    void getCity() {
    }

    @Test
    void getCountry() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void getPhone() {
    }

    @Test
    void getFax() {
    }

    @Test
    void getWebsite() {
    }

    @Test
    void getDescription() {
    }
}