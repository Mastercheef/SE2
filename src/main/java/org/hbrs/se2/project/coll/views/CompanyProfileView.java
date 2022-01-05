package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.CompanyControl;
import org.hbrs.se2.project.coll.control.LoginControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.LabelCompany;
import org.hbrs.se2.project.coll.util.Utils;
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
    private CompanyControl companyControl;
    @Autowired
    private LoginControl loginControl;

    Address address;
    int companyId;

    private static final String WIDTH = "200px";
    private static final String FONT = "font-weight";

    private static final Logger LOGGER = Logger.getLogger(CompanyProfileView.class.getName());

    LabelCompany labelCompany = new LabelCompany();

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
                CompanyDTO profileDTO = companyControl.findCompanyProfileByCompanyId(Integer.parseInt(parameter));
                address = profileDTO.getAddress();
                initLabels(profileDTO);
                createProfile();
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO,e.toString());
        }
    }

    // Used to read DTO data and inject it into the labels
    public void initLabels(CompanyDTO profileDTO) {
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

        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth(WIDTH);
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling

        // Profile Data
        HorizontalLayout hcompanyname   = new HorizontalLayout(labelCompany.getCompanyname(), lcompanyname);
        HorizontalLayout hstreet        = new HorizontalLayout(labelCompany.getStreet(), lstreet);
        HorizontalLayout hstreetnumber  = new HorizontalLayout(labelCompany.getStreetnumber(), lstreetnumber);
        HorizontalLayout hpostalcode    = new HorizontalLayout(labelCompany.getPostalcode(), lpostalcode);
        HorizontalLayout hcity          = new HorizontalLayout(labelCompany.getCity(), lcity);
        HorizontalLayout hcountry       = new HorizontalLayout(labelCompany.getCountry(), lcountry);
        HorizontalLayout hemail         = new HorizontalLayout(labelCompany.getEmail(), lemail);
        HorizontalLayout hphone         = new HorizontalLayout(labelCompany.getPhone(), lphone);
        HorizontalLayout hfax           = new HorizontalLayout(labelCompany.getFax(), lfax);
        HorizontalLayout hwebsite       = new HorizontalLayout(labelCompany.getWebsite(), lwebsite);
        HorizontalLayout hdescription   = new HorizontalLayout(labelCompany.getDescription(), ldescription);

        // Edit Profile Button
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button button = new Button("Profil editieren");
        button.addClickListener(e -> navigateToEdit(companyId));
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

        if (Utils.getCurrentUser() != null) {
            int currentUserId = Utils.getCurrentUser().getId();
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
        Label contactName          = new Label("Name:");
        Label contactPhone         = new Label("Telefon:");
        Label contactEmail         = new Label("E-Mail:");
        Label contactPosition      = new Label("Abteilung:");

        Label lcontactName         = new Label(contactPerson.getFirstName() + " " + contactPerson.getLastName());
        Label lcontactPhone        = new Label(contactPerson.getPhone());
        Label lcontactEmail        = new Label(contactPerson.getEmail());
        Label lcontactPosition     = new Label(contactPerson.getRole());

        // Styling
        for (Label label : new Label[]{ contactName, contactPhone, contactEmail, contactPosition }) {
            label.getElement().getStyle().set(FONT, "bold");
            label.setWidth("100px");
        }

        // Alignment
        HorizontalLayout hContactName      = new HorizontalLayout(contactName, lcontactName);
        HorizontalLayout hContactPhone     = new HorizontalLayout(contactPhone, lcontactPhone);
        HorizontalLayout hContactEmail     = new HorizontalLayout(contactEmail, lcontactEmail);
        HorizontalLayout hContactPosition  = new HorizontalLayout(contactPosition, lcontactPosition);

        // Add to div
        form.add(contactHeadline, hContactName, hContactPhone, hContactEmail, hContactPosition);
        return form;
    }

    public Div initJobOffers() {
        Div form        = new Div();
        H3 jobHeadline  = new H3("Stellenangebote dieser Firma");
        H4 jobNumber;
        form.add(jobHeadline);

        Button newJob = new Button("Neues Stellenangebot");
        newJob.addClickListener(e -> navigateToCreateJob(companyId));
        HorizontalLayout hbuttons = new HorizontalLayout(newJob);

        // Add "New Job" Button ONLY when the logged-in user is the contact person of this company
        int contactPersonId = contactPersonRepository.findContactPersonByCompanyId(companyId).getId();

        getIDCurrentUser(form, hbuttons, contactPersonId);

        // Find Job Advertisements for this company
        List<JobAdvertisement> jobAdvertisements =
                jobAdvertisementRepository.findJobAdvertisementsByCompanyId(companyId);

        // Only print them if they exist
        if(jobAdvertisements.isEmpty()){
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
                Label jobSalary         = new Label("Lohn:");

                Label lJobTitle;
                Label lJobType;
                Label lJobHours;
                Label lJobRequirements;
                Label lJobDescription;
                Label lJobStart;
                Label lJobEnd;
                Label lJobTemporary;
                Label lJobSalary;

                // Styling
                for (Label label : new Label[]{ jobTitle, jobType, jobHours, jobRequirements, jobDescription, jobStart,
                        jobEnd, jobTemporary, jobSalary }) {
                    label.getElement().getStyle().set(FONT, "bold");
                    label.setWidth(WIDTH);
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
                lJobSalary          = new Label(Integer.toString(job.getSalary()));


                lJobTemporary = getLabel(job);

                HorizontalLayout hJobTitle          = new HorizontalLayout(jobTitle, lJobTitle);
                HorizontalLayout hJobType           = new HorizontalLayout(jobType, lJobType);
                HorizontalLayout hJobHours          = new HorizontalLayout(jobHours, lJobHours);
                HorizontalLayout hJobRequirements   = new HorizontalLayout(jobRequirements, lJobRequirements);
                HorizontalLayout hJobDescription    = new HorizontalLayout(jobDescription, lJobDescription);
                HorizontalLayout hJobStart          = new HorizontalLayout(jobStart, lJobStart);
                HorizontalLayout hJobEnd            = new HorizontalLayout(jobEnd, lJobEnd);
                HorizontalLayout hJobTemporary      = new HorizontalLayout(jobTemporary, lJobTemporary);
                HorizontalLayout hJobSalary         = new HorizontalLayout(jobSalary, lJobSalary);

                // Create Buttons to get in contact with the Company
                HorizontalLayout hJobButtons = new HorizontalLayout();
                Button contactButton = new Button("Kontakt aufnehmen");
                contactButton.addClickListener(e -> Utils.navigateToContactFormular(companyId, job.getId()));
                hJobButtons.add(contactButton);

                // Button to delete. Only viewable by company's contact person
                if(contactPersonId == Utils.getCurrentUser().getId())
                {
                    Button deleteButton = new Button("Stellenangebot löschen");
                    deleteButton.addClickListener(e -> {

                        // Preventing missclicks by opening a dialog box
                        Dialog dialog    = new Dialog();
                        Label  question  = new Label("Sind sie sicher, dass Sie dieses Stellenangebot löschen " +
                                "möchten?");
                        Label  info      = new Label("(Dieser Vorgang ist unwiderruflich.)");
                        Button yesButton = new Button("Ja");
                        Button noButton  = new Button ("Nein");

                        createYesButton(job, dialog, yesButton);
                        noButton.addClickListener(no -> dialog.close());

                        HorizontalLayout buttons = new HorizontalLayout(yesButton, noButton);
                        VerticalLayout dialogContent = new VerticalLayout(question, info, buttons);
                        dialogContent.setAlignItems(FlexComponent.Alignment.CENTER);
                        dialog.add(dialogContent);
                        dialog.open();

                    });
                    hJobButtons.add(deleteButton);
                }

                // Add everything to the container
                form.add(jobNumber, hJobTitle, hJobType, hJobHours, hJobRequirements, hJobDescription, hJobStart,
                            hJobEnd, hJobTemporary, hJobSalary, hJobButtons);
            }
        }
        return form;
    }

    private void createYesButton(JobAdvertisement job, Dialog dialog, Button yesButton) {
        yesButton.addClickListener(jo -> {
            dialog.close();
            try {
                companyControl.deleteAdvertisement(job);
            } catch (DatabaseUserException ex) {
                ex.printStackTrace();
            }
            UI.getCurrent().getPage().reload();
        });
    }

    private void getIDCurrentUser(Div form, HorizontalLayout hbuttons, int contactPersonId) {
        if (Utils.getCurrentUser() != null) {
            int currentUserId = Utils.getCurrentUser().getId();
            if(Objects.equals(contactPersonId, currentUserId))
                form.add(hbuttons);
        }
    }

    private Label getLabel(JobAdvertisement job) {
        Label lJobTemporary;
        if(String.valueOf(job.getTemporaryEmployment()).equals("true"))
            lJobTemporary = new Label("Ja");
        else
            lJobTemporary = new Label("Nein");
        return lJobTemporary;
    }

    public static void navigateToEdit(int companyId) {
        if(!Objects.equals(Utils.getCurrentLocation(), Globals.Pages.COMPANYPROFILE_EDIT_VIEW))
            UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_EDIT_VIEW + companyId);
    }
    public static void navigateToCreateJob(int companyId) {
        if(!Objects.equals(Utils.getCurrentLocation(), Globals.Pages.RECRUITMENT_VIEW))
            UI.getCurrent().navigate(Globals.Pages.RECRUITMENT_VIEW + companyId);
    }

    public CompanyProfileView() {
        //Required because of VAADIN
    }

}

