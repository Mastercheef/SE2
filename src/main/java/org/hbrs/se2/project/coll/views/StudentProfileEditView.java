package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.util.Globals;

@Route(value = "profile_edit", layout = MainLayout.class)
@PageTitle("Edit your Profile")
public class StudentProfileEditView extends VerticalLayout {

    Label       firstname     = new Label("Vorname:");
    Label       lastname      = new Label("Nachname:");
    Label       occupation    = new Label("Beruf:");
    Label       birthdate     = new Label("Geburtsdatum:");
    Label       address       = new Label("Adresse:");
    Label       skills        = new Label("Skills:");
    Label       email         = new Label("E-Mail:");
    Label       number        = new Label("Telefon:");
    Label       interests     = new Label("Interessen:");
    Label       website       = new Label("Webseite:");
    Label       aboutme       = new Label("Über mich:");
    TextField   lfirstname    = new TextField();
    TextField   llastname     = new TextField();
    TextField   loccupation   = new TextField();
    TextField   lbirthdate    = new TextField();
    TextField   laddress      = new TextField();
    TextField   lskills       = new TextField();
    TextField   lemail        = new TextField();
    TextField   lnumber       = new TextField();
    TextField   linterests    = new TextField();
    TextField   lwebsite      = new TextField();
    TextField   laboutme      = new TextField();

    Div         div           = new Div();

    // TODO: Profilbild

    public StudentProfileEditView() {
        // TODO: get real Data as placeholders from UserDTO
        lfirstname.setPlaceholder("Max");
        llastname.setPlaceholder("Mustermann");
        loccupation.setPlaceholder("Student");
        lbirthdate.setPlaceholder("01.01.1990");
        laddress.setPlaceholder("Musterstrasse 1, 12345 Musterstadt");
        lskills.setPlaceholder("HTML, CSS, JavaScript, C++");
        lemail.setPlaceholder("max.mustermann@googlemail.com");
        lnumber.setPlaceholder("0173/11111111");
        linterests.setPlaceholder("Klettern, Kochen, Bier");
        lwebsite.setPlaceholder("http://www.maxmusterwebseite.de");
        laboutme.setPlaceholder("Ich kann alles. Ruf mich an, baby");

        //TODO: Update UserDTO after editing

        // Layout
        H2 h2 = new H2("Editiere mein Profil");
        //h2.getElement().getStyle().set("margin-top", "55px");

        // TODO: Get Image from Database
        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ firstname, lastname, occupation, birthdate, address, skills, email,
                number, interests, website, aboutme }) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        for (TextField textfield : new TextField[]{ lfirstname, llastname, loccupation, lbirthdate, laddress, lskills, lemail,
                lnumber, linterests, lwebsite, laboutme }) {
            textfield.getElement().getStyle().set("height", "20px");
            textfield.getElement().getStyle().set("width", "300px");
        }

        // Profile Data
        // TODO: Get Data from UserDTO
        HorizontalLayout hfirstname = new HorizontalLayout();
        hfirstname.add(firstname, lfirstname);
        HorizontalLayout hlastname = new HorizontalLayout();
        hlastname.add(lastname, llastname);
        HorizontalLayout hoccupation = new HorizontalLayout();
        hoccupation.add(occupation, loccupation);
        HorizontalLayout hbirthdate = new HorizontalLayout();
        hbirthdate.add(birthdate, lbirthdate);
        HorizontalLayout haddress = new HorizontalLayout();
        haddress.add(address, laddress);
        HorizontalLayout hskills = new HorizontalLayout();
        hskills.add(skills, lskills);
        HorizontalLayout hemail = new HorizontalLayout();
        hemail.add(email, lemail);
        HorizontalLayout hnumber = new HorizontalLayout();
        hnumber.add(number, lnumber);
        HorizontalLayout hinterests = new HorizontalLayout();
        hinterests.add(interests, linterests);
        HorizontalLayout hwebsite = new HorizontalLayout();
        hwebsite.add(website, lwebsite);
        HorizontalLayout haboutme = new HorizontalLayout();
        haboutme.add(aboutme, laboutme);

        // TODO: save in ProfileDTO (o.ä.)
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button saveButton   = new Button("Speichern");
        saveButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW));
        Button cancelButton = new Button("Abbrechen");
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW));
        hbuttons.add(saveButton, cancelButton);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hfirstname, hlastname, hoccupation, hbirthdate, haddress,
                hskills, hemail, hnumber, hinterests, hwebsite, haboutme, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "10px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hfirstname, hlastname, hoccupation,
                hbirthdate, haddress, hskills, hemail, hnumber, hinterests,
                hwebsite, haboutme, hbuttons);
        add(div);

    }
}
