package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.CompanyProfileControl;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


//TODO: Diese Seite sollte nur verfügbar sein, wenn man eingeloggt ist.
@Route(value = "companyprofile", layout = MainLayout.class)
@PageTitle("Profile")
public class CompanyProfileView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private CompanyProfileControl profileControl;
    Address addr = new Address();
    int companyId;

    Label companyname   = new Label("Firmenname:");
    Label street        = new Label("Strasse:");
    Label streetnumber  = new Label("Hausnummer:");
    Label postalcode    = new Label("PLZ:");
    Label city          = new Label("Ort:");
    Label country       = new Label("Land:");
    Label email         = new Label("E-Mail:");
    Label phone         = new Label("Telefon:");
    Label fax           = new Label("Fax:");
    Label website       = new Label("Webseite:");
    Label description   = new Label("Beschreibung:");

    Label lcompanyname  = new Label();
    Label lstreet       = new Label();
    Label lstreetnumber = new Label();
    Label lpostalcode   = new Label();
    Label lcity         = new Label();
    Label lcountry      = new Label();
    Label lemail        = new Label();
    Label lphone        = new Label();
    Label lfax          = new Label();
    Label lwebsite      = new Label();
    Label ldescription  = new Label();

    Div   div           = new Div();
    Div   contact       = new Div();
    Div   jobs          = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (!Objects.equals(parameter, "")) {
            CompanyProfileDTO profileDTO = profileControl.findCompanyProfileByCompanyId(Integer.parseInt(parameter));
            addr = profileDTO.getAddress();
            initLabels(profileDTO);
            createProfile();
        }
    }

    // Used to read DTO data and inject them into the labels
    public void initLabels(CompanyProfileDTO profileDTO) {
        companyId       = profileDTO.getId();
        lcompanyname    = new Label(profileDTO.getCompanyName());
        lstreet         = new Label(addr.getStreet());
        lstreetnumber   = new Label(addr.getHouseNumber());
        lpostalcode     = new Label(addr.getPostalCode());
        lcity           = new Label(addr.getCity());
        lcountry        = new Label(addr.getCountry());
        lemail          = new Label(profileDTO.getEmail());
        lphone          = new Label(String.valueOf(profileDTO.getPhoneNumber()));
        lfax            = new Label(String.valueOf(profileDTO.getFaxNumber()));
        lwebsite        = new Label(profileDTO.getWebsite());
        ldescription    = new Label(profileDTO.getDescription());
    }

    public void createProfile() {

        // Layout
        H2 h2 = new H2("Mein Profil");

        // TODO: Get Image from Database
        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ companyname, street, streetnumber, postalcode, city, country, email,
                phone, fax, website, description}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        // Profile Data
        HorizontalLayout hcompanyname   = new HorizontalLayout();
        HorizontalLayout hstreet        = new HorizontalLayout();
        HorizontalLayout hstreetnumber  = new HorizontalLayout();
        HorizontalLayout hpostalcode    = new HorizontalLayout();
        HorizontalLayout hcity          = new HorizontalLayout();
        HorizontalLayout hcountry       = new HorizontalLayout();
        HorizontalLayout hemail         = new HorizontalLayout();
        HorizontalLayout hphone         = new HorizontalLayout();
        HorizontalLayout hfax           = new HorizontalLayout();
        HorizontalLayout hwebsite       = new HorizontalLayout();
        HorizontalLayout hdescription   = new HorizontalLayout();

        hcompanyname.add(companyname, lcompanyname);
        hstreet.add(street, lstreet);
        hstreetnumber.add(streetnumber, lstreetnumber);
        hpostalcode.add(postalcode, lpostalcode);
        hcity.add(city, lcity);
        hcountry.add(country, lcountry);
        hemail.add(email, lemail);
        hphone.add(phone, lphone);
        hfax.add(fax, lfax);
        hwebsite.add(website, lwebsite);
        hdescription.add(description, ldescription);

        // Edit Profile Button
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button button = new Button("Profil editieren");
        button.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_EDIT_VIEW +
                companyId));
        hbuttons.add(button);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hcompanyname, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Contact Person
        contact = initContactPerson();

        // Job offer Div
        jobs = initJobOffers();

        // Align Layout
        HorizontalLayout finalLayout = new HorizontalLayout();
        VerticalLayout jobsAndContact = new VerticalLayout();
        jobsAndContact.add(contact, jobs);

        // Append everything to the site
        div.add(h2, profileImage, hcompanyname, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription, hbuttons);

        finalLayout.add(div, jobsAndContact);

        add(finalLayout);
    }

    public Div initContactPerson() {
        Div form = new Div();
        ContactPerson contactPerson = contactPersonRepository.findContactPersonByCompany_Id(companyId);

        // Labels
        H3 contactHeadline  = new H3("Kontaktperson dieser Firma");
        Label name          = new Label("Name:");
        Label phone         = new Label("Telefon:");
        Label email         = new Label("E-Mail:");
        Label position      = new Label("Abteilung:");

        Label lname         = new Label(contactPerson.getFirstName() + " " + contactPerson.getLastName());
        Label lphone        = new Label(contactPerson.getPhone());
        Label lemail        = new Label(contactPerson.getEmail());
        Label lposition     = new Label(contactPerson.getRole());

        // Styling
        for (Label label : new Label[]{ name, phone, email, position }) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "100px");        // For alignment
        }

        // Alignment
        HorizontalLayout hname      = new HorizontalLayout();
        HorizontalLayout hphone     = new HorizontalLayout();
        HorizontalLayout hemail     = new HorizontalLayout();
        HorizontalLayout hposition  = new HorizontalLayout();

        hname.add(name, lname);
        hphone.add(phone, lphone);
        hemail.add(email, lemail);
        hposition.add(position, lposition);

        // Add to div
        form.add(contactHeadline, hname, hphone, hemail, hposition);
        return form;
    }

    public Div initJobOffers() {
        Div form = new Div();

        H3 jobHeadline  = new H3("Stellenangebote dieser Firma");
        Label test      = new Label("hallo");
        form.add(jobHeadline, test);

        return form;
    }

    public CompanyProfileView() {

    }

}

