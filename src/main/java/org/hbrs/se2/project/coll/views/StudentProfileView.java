package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.StudentProfileControl;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.hbrs.se2.project.coll.util.*;

//TODO: Diese Seite sollte nur verfügbar sein, wenn man eingeloggt ist.
//TODO: UserDTO für Daten auslesen und in Textfelder einfüllen
@Route(value = "profile", layout = MainLayout.class)
@PageTitle("Profile")
public class StudentProfileView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private StudentProfileControl profileControl;
    private StudentUserDTO profileDTO;

    Label firstname     = new Label("Vorname:");
    Label lfirstname    = new Label("Vorname");
    Label lastname      = new Label("Nachname:");
    Label llastname     = new Label("Nachname");
    Label occupation    = new Label("Abschluss:");
    Label loccupation   = new Label("Abschluss");
    Label birthdate     = new Label("Geburtsdatum:");
    Label lbirthdate    = new Label("Geburtsdatum");
    Label address       = new Label("Adresse:");
    Label laddress      = new Label("Adresse");
    Label skills        = new Label("Skills:");
    Label lskills       = new Label("Skills");
    Label email         = new Label("E-Mail:");
    Label lemail        = new Label("E-Mail");
    Label number        = new Label("Telefon:");
    Label lnumber       = new Label("Telefon");
    Label interests     = new Label("Interessen:");
    Label linterests    = new Label("Interessen");
    Label website       = new Label("Webseite:");
    Label lwebsite      = new Label("Webseite");
    Label aboutme       = new Label("Über mich:");
    Label laboutme      = new Label("Über mich");

    Div   div           = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (parameter != "") {
            profileDTO  = profileControl.loadProfileDataById(Integer.parseInt(parameter));
            lfirstname  = new Label(profileDTO.getFirstName());
            llastname   = new Label(profileDTO.getLastName());
            loccupation = new Label(profileDTO.getGraduation());
            lbirthdate  = new Label(profileDTO.getDateOfBirth().toString());
            laddress    = new Label(profileDTO.getAddress().toString());
            lskills     = new Label(profileDTO.getSkills());
            lemail      = new Label(profileDTO.getEmail());
            lnumber     = new Label(profileDTO.getPhone());
            linterests  = new Label(profileDTO.getInterests());
            lwebsite    = new Label(profileDTO.getWebsite());
            laboutme    = new Label(profileDTO.getDescription());
        }
        createProfileView();
    }

    public void createProfileView() {
        //TODO: Methods for grabbing UserDTO data

        // Layout
        H2 h2 = new H2("Mein Profil");

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

        // Edit Profile Button
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button button = new Button("Profil editieren");
        button.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_EDIT_VIEW +
                profileDTO.getId()));
        hbuttons.add(button);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hfirstname, hlastname, hoccupation, hbirthdate, haddress,
                hskills, hemail, hnumber, hinterests, hwebsite, haboutme, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hfirstname, hlastname, hoccupation, hbirthdate, haddress,
                hskills, hemail, hnumber, hinterests, hwebsite, haboutme, button);
        add(div);
    }
}


