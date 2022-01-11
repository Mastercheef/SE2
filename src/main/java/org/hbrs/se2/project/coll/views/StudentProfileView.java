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
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route(value = "profile", layout = AppView.class)
@PageTitle("StudentProfile")
public class StudentProfileView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private StudentProfileControl profileControl;
    private StudentUserDTO profileDTO;
    Address addr = new Address();

    String floatString = "float";

    Label salutation    = new Label("Anrede:");
    Label title         = new Label("Titel:");
    Label firstname     = new Label("Vorname:");
    Label lastname      = new Label("Nachname:");
    Label occupation    = new Label("Abschluss:");
    Label birthdate     = new Label("Geburtsdatum:");
    Label address = new Label("Anschrift:");
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
    Label lskills;
    Label lemail;
    Label lnumber;
    Label linterests;
    Label lwebsite;
    Label laboutme;

    Div   div = new Div();
    Div   leftDiv = new Div();
    Div   rightDiv = new Div();
    Div   bottomDiv = new Div();

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
        lstreet         = new Label();
        lstreet.getElement().setProperty("innerHTML", addr.toString().replaceAll("\n", "<br>"));
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

        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ salutation, title, firstname, lastname, occupation, birthdate, address,
                skills, email, number, interests, website, aboutme }) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        // Profile Data
        HorizontalLayout hsalutation    = new HorizontalLayout(salutation, lsalutation);
        HorizontalLayout htitle         = new HorizontalLayout(title, ltitle);
        HorizontalLayout hfirstname     = new HorizontalLayout(firstname, lfirstname);
        HorizontalLayout hlastname      = new HorizontalLayout(lastname, llastname);
        HorizontalLayout hoccupation    = new HorizontalLayout(occupation, loccupation);
        HorizontalLayout hbirthdate     = new HorizontalLayout(birthdate, lbirthdate);
        HorizontalLayout haddress        = new HorizontalLayout(address, lstreet);
        HorizontalLayout hskills        = new HorizontalLayout(skills, lskills);
        HorizontalLayout hemail         = new HorizontalLayout(email, lemail);
        HorizontalLayout hnumber        = new HorizontalLayout(number, lnumber);
        HorizontalLayout hinterests     = new HorizontalLayout(interests, linterests);
        HorizontalLayout hwebsite       = new HorizontalLayout(website, lwebsite);
        HorizontalLayout haboutme       = new HorizontalLayout(aboutme, laboutme);

        // Edit Profile Button
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button button = new Button("Profil editieren");
        button.addClickListener(e -> navigateToEdit(profileDTO.getId()));
        hbuttons.add(button);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hsalutation, htitle, hfirstname, hlastname, hoccupation,
                hbirthdate, haddress, hskills, hemail, hnumber, hinterests,
                hwebsite, haboutme, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Append everything to the site
        leftDiv.add(profileImage, hsalutation, htitle, hfirstname, hlastname, hbirthdate, haddress, hemail, hnumber);
        rightDiv.add(hoccupation, hwebsite, hskills, hinterests, haboutme);
        div.add(h2, leftDiv, rightDiv, bottomDiv);

        // Add Edit Button ONLY when the logged-in user is the owner of this profile
        if (UtilCurrent.getCurrentUser() != null) {
            int currentUserId = UtilCurrent.getCurrentUser().getId();
            if(Objects.equals(profileDTO.getId(), currentUserId))
                bottomDiv.add(hbuttons);
        }
        // Css Changes for split View
        bottomDiv.setWidth("100%");
        bottomDiv.getStyle().set(floatString, "left");
        leftDiv.setWidth("40%");
        leftDiv.setMinWidth("300px");
        rightDiv.setWidth("60%");
        rightDiv.setMinWidth("400px");
        div.setClassName("centered");
        div.setWidth("80%");
        leftDiv.getStyle().set(floatString, "left");
        rightDiv.getStyle().set(floatString, "left");
        add(div);
    }
    public static void navigateToEdit(int studentId) {
        if(!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.PROFILE_EDIT_VIEW))
            UI.getCurrent().navigate(Globals.Pages.PROFILE_EDIT_VIEW + studentId);
    }

}


