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
import com.vaadin.flow.server.PWA;
import org.hbrs.se2.project.coll.control.StudentProfileControl;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Objects;

@Route(value = "profile", layout = AppView.class)
@PageTitle("StudentProfile")
public class StudentProfileView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private StudentProfileControl profileControl;
    private StudentUserDTO profileDTO;
    Address addr = new Address();

    Label salutation    = new Label("Anrede:");
    Label title         = new Label("Titel:");
    Label firstname     = new Label("Vorname:");
    Label lastname      = new Label("Nachname:");
    Label occupation    = new Label("Abschluss:");
    Label birthdate     = new Label("Geburtsdatum:");
    Label street        = new Label("Strasse:");
    Label streetnumber  = new Label("Hausnummer:");
    Label postalcode    = new Label("PLZ:");
    Label city          = new Label("Ort:");
    Label country       = new Label("Land:");
    Label skills        = new Label("Skills:");
    Label email         = new Label("E-Mail:");
    Label number        = new Label("Telefon:");
    Label interests     = new Label("Interessen:");
    Label website       = new Label("Webseite:");
    Label aboutme       = new Label("Über mich:");

    Label lsalutation;
    Label ltitle;
    Label lfirstname;
    Label llastname;
    Label loccupation;
    Label lbirthdate;
    Label lstreet;
    Label lstreetnumber;
    Label lpostalcode;
    Label lcity;
    Label lcountry;
    Label lskills;
    Label lemail;
    Label lnumber;
    Label linterests;
    Label lwebsite;
    Label laboutme;

    Div   div = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (!Objects.equals(parameter, "")) {
            profileDTO  = profileControl.loadProfileDataById(Integer.parseInt(parameter));
            addr = profileDTO.getAddress();
            initLabels(profileDTO);
        }
        createProfileView();
    }
    // Used to read DTO data and inject them into the labels
    public void initLabels(StudentUserDTO profileDTO) {
        lsalutation     = new Label(profileDTO.getSalutation());
        ltitle          = new Label(profileDTO.getTitle());
        lfirstname      = new Label(profileDTO.getFirstName());
        llastname       = new Label(profileDTO.getLastName());
        loccupation     = new Label(profileDTO.getGraduation() == null ? "" : profileDTO.getGraduation());
        lbirthdate      = new Label(profileDTO.getDateOfBirth().toString());
        lstreet         = new Label(addr.getStreet());
        lstreetnumber   = new Label(addr.getHouseNumber());
        lpostalcode     = new Label(addr.getPostalCode());
        lcity           = new Label(addr.getCity());
        lcountry        = new Label(addr.getCountry());
        lskills         = new Label(profileDTO.getSkills() == null ? "" : profileDTO.getSkills());
        lemail          = new Label(profileDTO.getEmail() == null ? "" : profileDTO.getEmail());
        lnumber         = new Label(profileDTO.getPhone() == null ? "" : profileDTO.getPhone());
        linterests      = new Label(profileDTO.getInterests() == null ? "" : profileDTO.getInterests());
        lwebsite        = new Label(profileDTO.getWebsite() == null ? "" : profileDTO.getWebsite());
        laboutme        = new Label(profileDTO.getDescription() == null ? "" : profileDTO.getDescription());
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
        for (Label label : new Label[]{ salutation, title, firstname, lastname, occupation, birthdate, street,
                streetnumber, postalcode, city, country, skills, email, number, interests, website, aboutme }) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        // Profile Data
        // TODO: Get Data from UserDTO
        HorizontalLayout hsalutation    = new HorizontalLayout(salutation, lsalutation);
        HorizontalLayout htitle         = new HorizontalLayout(title, ltitle);
        HorizontalLayout hfirstname     = new HorizontalLayout(firstname, lfirstname);
        HorizontalLayout hlastname      = new HorizontalLayout(lastname, llastname);
        HorizontalLayout hoccupation    = new HorizontalLayout(occupation, loccupation);
        HorizontalLayout hbirthdate     = new HorizontalLayout(birthdate, lbirthdate);
        HorizontalLayout hstreet        = new HorizontalLayout(street, lstreet);
        HorizontalLayout hstreetnumber  = new HorizontalLayout(streetnumber, lstreetnumber);
        HorizontalLayout hpostalcode    = new HorizontalLayout(postalcode, lpostalcode);
        HorizontalLayout hcity          = new HorizontalLayout(city, lcity);
        HorizontalLayout hcountry       = new HorizontalLayout(country, lcountry);
        HorizontalLayout hskills        = new HorizontalLayout(skills, lskills);
        HorizontalLayout hemail         = new HorizontalLayout(email, lemail);
        HorizontalLayout hnumber        = new HorizontalLayout(number, lnumber);
        HorizontalLayout hinterests     = new HorizontalLayout(interests, linterests);
        HorizontalLayout hwebsite       = new HorizontalLayout(website, lwebsite);
        HorizontalLayout haboutme       = new HorizontalLayout(aboutme, laboutme);

        // Edit Profile Button
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button button = new Button("Profil editieren");
        button.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_EDIT_VIEW +
                profileDTO.getId()));
        hbuttons.add(button);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hsalutation, htitle, hfirstname, hlastname, hoccupation,
                hbirthdate, hstreet, hstreetnumber, hpostalcode, hcity, hcountry, hskills, hemail, hnumber, hinterests,
                hwebsite, haboutme, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hsalutation, htitle, hfirstname, hlastname, hoccupation, hbirthdate, hstreet,
                hstreetnumber, hpostalcode, hcity, hcountry, hskills, hemail, hnumber, hinterests, hwebsite, haboutme);

        // Add Edit Button ONLY when the logged-in user is the owner of this profile
        if (getCurrentUser() != null) {
            int currentUserId = getCurrentUser().getId();
            if(Objects.equals(profileDTO.getId(), currentUserId))
                div.add(hbuttons);
        }
        add(div);
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}


