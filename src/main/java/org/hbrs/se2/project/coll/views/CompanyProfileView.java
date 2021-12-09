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
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "companyprofile", layout = AppView.class)
@PageTitle("Profile")
public class CompanyProfileView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    private CompanyProfileControl profileControl;
    Address address;
    int companyId;

    private static final Logger LOGGER = Logger.getLogger(CompanyProfileView.class.getName());

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

    Label lcompanyname;
    Label lstreet;
    Label lstreetnumber;
    Label lpostalcode;
    Label lcity;
    Label lcountry;
    Label lemail;
    Label lphone;
    Label lfax;
    Label lwebsite;
    Label ldescription;

    Div   div       = new Div();
    Div   contact   = new Div();
    Div   jobs      = new Div();

    // Profiles can only be accesses via ID
    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        try {
            if (!Objects.equals(parameter, "")) {
                CompanyProfileDTO profileDTO = profileControl.findCompanyProfileByCompanyId(Integer.parseInt(parameter));
                address = profileDTO.getAddress();
                initLabels(profileDTO);
                createProfile();
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO,e.toString());
        }
    }

    // Used to read DTO data and inject it into the labels
    public void initLabels(CompanyProfileDTO profileDTO) {
        companyId       = profileDTO.getId();
        lcompanyname    = new Label(profileDTO.getCompanyName());
        lstreet         = new Label(address.getStreet());
        lstreetnumber   = new Label(address.getHouseNumber());
        lpostalcode     = new Label(address.getPostalCode());
        lcity           = new Label(address.getCity());
        lcountry        = new Label(address.getCountry());
        lemail          = new Label(profileDTO.getEmail());
        lphone          = new Label(String.valueOf(profileDTO.getPhoneNumber()));
        lfax            = new Label(String.valueOf(profileDTO.getFaxNumber()));
        lwebsite        = new Label(profileDTO.getWebsite());
        ldescription    = new Label(profileDTO.getDescription());
    }

    // Build profile content
    public void createProfile() {
        H2 h2 = new H2("Firmenprofil");

        // TODO: Get Image from Database
        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ companyname, street, streetnumber, postalcode, city, country, email,
                phone, fax, website, description}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.setWidth("200px");
        }

        // Profile Data
        HorizontalLayout hcompanyname   = new HorizontalLayout(companyname, lcompanyname);
        HorizontalLayout hstreet        = new HorizontalLayout(street, lstreet);
        HorizontalLayout hstreetnumber  = new HorizontalLayout(streetnumber, lstreetnumber);
        HorizontalLayout hpostalcode    = new HorizontalLayout(postalcode, lpostalcode);
        HorizontalLayout hcity          = new HorizontalLayout(city, lcity);
        HorizontalLayout hcountry       = new HorizontalLayout(country, lcountry);
        HorizontalLayout hemail         = new HorizontalLayout(email, lemail);
        HorizontalLayout hphone         = new HorizontalLayout(phone, lphone);
        HorizontalLayout hfax           = new HorizontalLayout(fax, lfax);
        HorizontalLayout hwebsite       = new HorizontalLayout(website, lwebsite);
        HorizontalLayout hdescription   = new HorizontalLayout(description, ldescription);

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

        // Contact Person Div
        contact = initContactPerson();

        // Job offer Div
        jobs = initJobOffers();

        // Align Layout
        HorizontalLayout finalLayout = new HorizontalLayout();
        VerticalLayout jobsAndContact = new VerticalLayout();
        jobsAndContact.add(contact, jobs);

        // Append everything to the site
        div.add(h2, profileImage, hcompanyname, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription);

        // Add Edit Button ONLY when the logged-in user is the contact person of this company
        int contactPersonId = contactPersonRepository.findContactPersonByCompanyId(companyId).getId();

        if (getCurrentUser() != null) {
            int currentUserId = getCurrentUser().getId();
            if(Objects.equals(contactPersonId, currentUserId))
                div.add(hbuttons);
        }
        finalLayout.add(div, jobsAndContact);
        add(finalLayout);
    }

    public Div initContactPerson() {
        Div form = new Div();
        ContactPerson contactPerson = contactPersonRepository.findContactPersonByCompanyId(companyId);

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
            label.setWidth("100px");
        }

        // Alignment
        HorizontalLayout hname      = new HorizontalLayout(name, lname);
        HorizontalLayout hphone     = new HorizontalLayout(phone, lphone);
        HorizontalLayout hemail     = new HorizontalLayout(email, lemail);
        HorizontalLayout hposition  = new HorizontalLayout(position, lposition);

        // Add to div
        form.add(contactHeadline, hname, hphone, hemail, hposition);
        return form;
    }

    public Div initJobOffers() {
        Div form        = new Div();
        H3 jobHeadline  = new H3("Stellenangebote dieser Firma");
        H4 jobNumber;
        form.add(jobHeadline);

        Button newJob = new Button("Neues Stellenangebot");
        newJob.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.RECRUITMENT_VIEW +
                companyId));
        HorizontalLayout hbuttons = new HorizontalLayout(newJob);

        // Add "New Job" Button ONLY when the logged-in user is the contact person of this company
        int contactPersonId = contactPersonRepository.findContactPersonByCompanyId(companyId).getId();

        if (getCurrentUser() != null) {
            int currentUserId = getCurrentUser().getId();
            if(Objects.equals(contactPersonId, currentUserId))
                form.add(hbuttons);
        }

        // Find Job Advertisements for this company
        List<JobAdvertisement> jobAdvertisements =
                jobAdvertisementRepository.findJobAdvertisementsByCompanyId(companyId);

        // Only print them if they exist
        if(jobAdvertisements.size() == 0){
            Label noJobs = new Label("Diese Firma hat zur Zeit keine Stellenangebote.");
            form.add(noJobs);
        }
        else
        {
            int count = 1;

            for(JobAdvertisement job : jobAdvertisements)
            {
                Label jobTitle          = new Label("Titel:");
                Label jobType           = new Label("Tätigkeit:");
                Label jobHours          = new Label("Stundenzahl/Woche:");
                Label jobRequirements   = new Label("Voraussetzungen:");
                Label jobDescription    = new Label("Beschreibung:");
                Label jobStart          = new Label("Beginn der Tätigkeit:");
                Label jobEnd            = new Label("Ende der Tätigkeit:");
                Label jobTemporary      = new Label("Temporäre Beschäftigung:");

                Label lJobTitle;
                Label lJobType;
                Label lJobHours;
                Label lJobRequirements;
                Label lJobDescription;
                Label lJobStart;
                Label lJobEnd;
                Label lJobTemporary;

                // Styling
                for (Label label : new Label[]{ jobTitle, jobType, jobHours, jobRequirements, jobDescription, jobStart,
                        jobEnd, jobTemporary }) {
                    label.getElement().getStyle().set("font-weight", "bold");
                    label.setWidth("200px");
                }

                // Count Job offers
                jobNumber = new H4("Stellenanzeige Nr. " + count);
                count++;

                // Fill labels with content
                lJobTitle           = new Label(job.getJobTitle());
                lJobType            = new Label(job.getTypeOfEmployment());
                lJobHours           = new Label(String.valueOf(job.getWorkingHours()));
                lJobRequirements    = new Label(job.getRequirements());
                lJobDescription     = new Label(job.getJobDescription());
                lJobStart           = new Label(job.getStartOfWork().toString());
                lJobEnd             = new Label(job.getEndOfWork().toString());

                if(String.valueOf(job.getTemporaryEmployment()).equals("true"))
                    lJobTemporary = new Label("Ja");
                else
                    lJobTemporary = new Label("Nein");

                HorizontalLayout hJobTitle          = new HorizontalLayout(jobTitle, lJobTitle);
                HorizontalLayout hJobType           = new HorizontalLayout(jobType, lJobType);
                HorizontalLayout hJobHours          = new HorizontalLayout(jobHours, lJobHours);
                HorizontalLayout hJobRequirements   = new HorizontalLayout(jobRequirements, lJobRequirements);
                HorizontalLayout hJobDescription    = new HorizontalLayout(jobDescription, lJobDescription);
                HorizontalLayout hJobStart          = new HorizontalLayout(jobStart, lJobStart);
                HorizontalLayout hJobEnd            = new HorizontalLayout(jobEnd, lJobEnd);
                HorizontalLayout hJobTemporary      = new HorizontalLayout(jobTemporary, lJobTemporary);

                // Create Buttons to get in contact with the Company
                // TODO: Kontaktformular nach Button Klick aufrufen (Sprint 2)
                Button contactButton = new Button("Kontakt aufnehmen");

                // Add everything to the container
                form.add(jobNumber, hJobTitle, hJobType, hJobHours, hJobRequirements, hJobDescription, hJobStart,
                            hJobEnd, hJobTemporary, contactButton);
            }
        }
        return form;
    }
    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    public CompanyProfileView() {

    }

}

