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
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Objects;

@Route(value = "profile", layout = AppView.class)
@PageTitle("Profile")
public class StudentProfileView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private StudentProfileControl profileControl;
    private StudentUserDTO profileDTO;

    Label firstname     = new Label("Vorname:");
    Label lastname      = new Label("Nachname:");
    Label occupation    = new Label("Abschluss:");
    Label birthdate     = new Label("Geburtsdatum:");
    Label address       = new Label("Adresse:");
    Label skills        = new Label("Skills:");
    Label email         = new Label("E-Mail:");
    Label number        = new Label("Telefon:");
    Label interests     = new Label("Interessen:");
    Label website       = new Label("Webseite:");
    Label aboutme       = new Label("Über mich:");

    Label lfirstname    = new Label("Vorname");
    Label llastname     = new Label("Nachname");
    Label loccupation   = new Label("Abschluss");
    Label lbirthdate    = new Label("Geburtsdatum");
    Label laddress      = new Label("Adresse");
    Label lskills       = new Label("Skills");
    Label lemail        = new Label("E-Mail");
    Label lnumber       = new Label("Telefon");
    Label linterests    = new Label("Interessen");
    Label lwebsite      = new Label("Webseite");
    Label laboutme      = new Label("Über mich");

    Div   div           = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (!Objects.equals(parameter, "")) {
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
        HorizontalLayout hfirstname     = new HorizontalLayout();
        HorizontalLayout hlastname      = new HorizontalLayout();
        HorizontalLayout hoccupation    = new HorizontalLayout();
        HorizontalLayout hbirthdate     = new HorizontalLayout();
        HorizontalLayout haddress       = new HorizontalLayout();
        HorizontalLayout hskills        = new HorizontalLayout();
        HorizontalLayout hemail         = new HorizontalLayout();
        HorizontalLayout hnumber        = new HorizontalLayout();
        HorizontalLayout hinterests     = new HorizontalLayout();
        HorizontalLayout hwebsite       = new HorizontalLayout();
        HorizontalLayout haboutme       = new HorizontalLayout();

        hfirstname.add(firstname, lfirstname);
        hlastname.add(lastname, llastname);
        hoccupation.add(occupation, loccupation);
        hbirthdate.add(birthdate, lbirthdate);
        haddress.add(address, laddress);
        hskills.add(skills, lskills);
        hemail.add(email, lemail);
        hnumber.add(number, lnumber);
        hinterests.add(interests, linterests);
        hwebsite.add(website, lwebsite);
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
                hskills, hemail, hnumber, hinterests, hwebsite, haboutme);

        // Add Edit Button ONLY when the logged-in user is the owner of this profile
        int currentUserId = getCurrentUser().getId();
        if(Objects.equals(profileDTO.getId(), currentUserId))
            div.add(hbuttons);
        add(div);
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}


