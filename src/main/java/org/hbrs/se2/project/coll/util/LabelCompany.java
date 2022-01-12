package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.html.Label;

public class LabelCompany {

    private static final String FONT = "font-weight";
    private static final String WIDTH = "200px";


    public LabelCompany() {
        for (Label label : new Label[]{ companyname, street, streetnumber, postalcode, city, country, email,
                phone, fax, website, description}) {
            label.getElement().getStyle().set(FONT, "bold");
            label.setWidth(WIDTH);
        }

    }
    private final Label companyname   = new Label("Firmenname:");
    private final Label street        = new Label("Strasse:");
    private final Label streetnumber  = new Label("Hausnummer:");
    private final Label postalcode    = new Label("PLZ:");
    private final Label city          = new Label("Ort:");
    private final Label country       = new Label("Land:");
    private final Label email         = new Label("E-Mail:");
    private final Label phone         = new Label("Telefon:");
    private final Label fax           = new Label("Fax:");
    private final Label website       = new Label("Webseite:");
    private final Label description   = new Label("Beschreibung:");

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
