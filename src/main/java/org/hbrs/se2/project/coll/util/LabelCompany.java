package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.html.Label;

public class LabelCompany {

    private Label companyname   = new Label("Firmenname:");
    private Label street        = new Label("Strasse:");
    private Label streetnumber  = new Label("Hausnummer:");
    private Label postalcode    = new Label("PLZ:");
    private Label city          = new Label("Ort:");
    private Label country       = new Label("Land:");
    private Label email         = new Label("E-Mail:");
    private Label phone         = new Label("Telefon:");
    private Label fax           = new Label("Fax:");
    private Label website       = new Label("Webseite:");
    private Label description   = new Label("Beschreibung:");

    public Label getCompanyname() {
        return companyname;
    }

    public Label getStreet() {
        return street;
    }

    public Label getStreetnumber() {
        return streetnumber;
    }

    public Label getPostalcode() {
        return postalcode;
    }

    public Label getCity() {
        return city;
    }

    public Label getCountry() {
        return country;
    }

    public Label getEmail() {
        return email;
    }

    public Label getPhone() {
        return phone;
    }

    public Label getFax() {
        return fax;
    }

    public Label getWebsite() {
        return website;
    }

    public Label getDescription() {
        return description;
    }
}
