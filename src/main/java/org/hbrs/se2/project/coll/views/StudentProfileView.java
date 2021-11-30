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
import org.hbrs.se2.project.coll.entities.Address;
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

    Label lsalutation   = new Label("Anrede");
    Label ltitle        = new Label("Titel");
    Label lfirstname    = new Label("Vorname");
    Label llastname     = new Label("Nachname");
    Label loccupation   = new Label("Abschluss");
    Label lbirthdate    = new Label("Geburtsdatum");
    Label lstreet       = new Label("Strasse");
    Label lstreetnumber = new Label("1");
    Label lpostalcode   = new Label("12345");
    Label lcity         = new Label("Stadt");
    Label lcountry      = new Label("Land");
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
        loccupation     = new Label(profileDTO.getGraduation());
        lbirthdate      = new Label(profileDTO.getDateOfBirth().toString());
        lstreet         = new Label(addr.getStreet());
        lstreetnumber   = new Label(addr.getHouseNumber());
        lpostalcode     = new Label(addr.getPostalCode());
        lcity           = new Label(addr.getCity());
        lcountry        = new Label(addr.getCountry());
        lskills         = new Label(profileDTO.getSkills());
        lemail          = new Label(profileDTO.getEmail());
        lnumber         = new Label(profileDTO.getPhone());
        linterests      = new Label(profileDTO.getInterests());
        lwebsite        = new Label(profileDTO.getWebsite());
        laboutme        = new Label(profileDTO.getDescription());
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
        HorizontalLayout hsalutation    = new HorizontalLayout();
        HorizontalLayout htitle         = new HorizontalLayout();
        HorizontalLayout hfirstname     = new HorizontalLayout();
        HorizontalLayout hlastname      = new HorizontalLayout();
        HorizontalLayout hoccupation    = new HorizontalLayout();
        HorizontalLayout hbirthdate     = new HorizontalLayout();
        HorizontalLayout hstreet        = new HorizontalLayout();
        HorizontalLayout hstreetnumber  = new HorizontalLayout();
        HorizontalLayout hpostalcode    = new HorizontalLayout();
        HorizontalLayout hcity          = new HorizontalLayout();
        HorizontalLayout hcountry       = new HorizontalLayout();
        HorizontalLayout hskills        = new HorizontalLayout();
        HorizontalLayout hemail         = new HorizontalLayout();
        HorizontalLayout hnumber        = new HorizontalLayout();
        HorizontalLayout hinterests     = new HorizontalLayout();
        HorizontalLayout hwebsite       = new HorizontalLayout();
        HorizontalLayout haboutme       = new HorizontalLayout();

        hsalutation.add(salutation, lsalutation);
        htitle.add(title, ltitle);
        hfirstname.add(firstname, lfirstname);
        hlastname.add(lastname, llastname);
        hoccupation.add(occupation, loccupation);
        hbirthdate.add(birthdate, lbirthdate);
        hstreet.add(street, lstreet);
        hstreetnumber.add(streetnumber, lstreetnumber);
        hpostalcode.add(postalcode, lpostalcode);
        hcity.add(city, lcity);
        hcountry.add(country, lcountry);
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


