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
import org.hbrs.se2.project.coll.control.CompanyProfileControl;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;


//TODO: Diese Seite sollte nur verfügbar sein, wenn man eingeloggt ist.
//TODO: UserDTO für Daten auslesen und in Textfelder einfüllen
@Route(value = "companyprofile", layout = MainLayout.class)
@PageTitle("Profile")
public class CompanyProfileView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private CompanyProfileControl profileControl;

    private CompanyProfileDTO profileDTO;

    Label companyname   = new Label("Firmenname:");
    Label address       = new Label("Adresse:");
    Label email         = new Label("E-Mail:");
    Label phone         = new Label("Telefon:");
    Label fax           = new Label("Fax:");
    Label website       = new Label("Webseite:");
    Label description   = new Label("Beschreibung:");

    Label lcompanyname  = new Label();
    Label laddress      = new Label();
    Label lemail        = new Label();
    Label lphone        = new Label();
    Label lfax          = new Label();
    Label lwebsite      = new Label();
    Label ldescription  = new Label();

    Div   div           = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (parameter!="") {
            profileDTO = profileControl.findCompanyProfileByCompanyID(Integer.parseInt(parameter));
            System.out.println(profileDTO.getCompanyName());
            initLabels(profileDTO);
            createProfile();
        }
    }

    public void initLabels(CompanyProfileDTO profileDTO) {
        lcompanyname    = new Label(profileDTO.getCompanyName());
        laddress        = new Label(String.valueOf(profileDTO.getAddress()));
        lemail          = new Label(profileDTO.getEmail());
        lphone          = new Label(String.valueOf(profileDTO.getPhoneNumber()));
        lfax            = new Label(String.valueOf(profileDTO.getFaxNumber()));
        lwebsite        = new Label(profileDTO.getWebsite());
        ldescription    = new Label(profileDTO.getDescription());
    }

    public void createProfile() {

        //TODO: Methods for grabbing UserDTO data

        // Layout
        H2 h2 = new H2("Mein Profil");

        // TODO: Get Image from Database
        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ companyname, address, email, phone, fax, website, description}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        // Profile Data
        // TODO: Get Data from UserDTO
        HorizontalLayout hcompanyname = new HorizontalLayout();
        hcompanyname.add(companyname, lcompanyname);
        HorizontalLayout haddress = new HorizontalLayout();
        haddress.add(address, laddress);
        HorizontalLayout hemail = new HorizontalLayout();
        hemail.add(email, lemail);
        HorizontalLayout hphone = new HorizontalLayout();
        hphone.add(phone, lphone);
        HorizontalLayout hfax = new HorizontalLayout();
        hfax.add(fax, lfax);
        HorizontalLayout hwebsite = new HorizontalLayout();
        hwebsite.add(website, lwebsite);
        HorizontalLayout hdescription = new HorizontalLayout();
        hdescription.add(description, ldescription);

        // Edit Profile Button
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button button = new Button("Profil editieren");
        button.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_EDIT_VIEW));
        hbuttons.add(button);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hcompanyname, haddress, hemail, hphone, hfax, hwebsite,
                hdescription, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hcompanyname, haddress,hemail, hphone, hfax, hwebsite,
                hdescription, button);
        add(div);
    }

    public CompanyProfileView() {

    }

}

