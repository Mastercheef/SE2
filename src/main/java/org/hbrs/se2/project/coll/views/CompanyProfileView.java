package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.AuthorizationControl;
import org.hbrs.se2.project.coll.control.CompanyControl;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.control.JobApplicationControl;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.LabelCompany;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.hbrs.se2.project.coll.views.grids.JobAdvertisementGrid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "companyprofile", layout = AppView.class)
@PageTitle("Profile")
public class CompanyProfileView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private JobAdvertisementControl jobAdvertisementControl;
    @Autowired
    private JobApplicationControl jobApplicationControl;
    @Autowired
    private AuthorizationControl authorizationControl;

    @Autowired
    private CompanyControl companyControl;

    JobAdvertisementGrid jobAdvertisementGrid;

    Address address;
    int companyId;

    private static final String WIDTH = "200px";
    private static final String FONT = "font-weight";

    private static final Logger LOGGER = Logger.getLogger(CompanyProfileView.class.getName());

    LabelCompany labelCompany = new LabelCompany();

    H2 companyName;

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

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

    }

    // Used to read DTO data and inject it into the labels
    public void initLabels(CompanyDTO profileDTO) {
        companyId       = profileDTO.getId();
        companyName     = new H2(profileDTO.getCompanyName());
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
        div.setWidthFull();
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

        // Align Layout
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        HorizontalLayout mainCompanyLayout = new HorizontalLayout();
        VerticalLayout companyInfoLayout = new VerticalLayout();
        VerticalLayout contactPersonLayout = new VerticalLayout();
        contactPersonLayout.add(contact);

        // Append everything to the site
        companyInfoLayout.add(companyName, profileImage, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription);

        // Add Edit Button ONLY when the logged-in user is the contact person of this company
        if (UtilCurrent.getCurrentUser() != null &&
                authorizationControl.isUserCompanyContactPerson(UtilCurrent.getCurrentUser(), companyId)) {
            companyInfoLayout.add(hbuttons);
        }
        mainCompanyLayout.add(companyInfoLayout, contactPersonLayout);
        add(mainCompanyLayout);

        if (UtilCurrent.getCurrentUser() != null &&
                !authorizationControl.isUserCompanyContactPerson(UtilCurrent.getCurrentUser(),companyId)) {
            jobAdvertisementGrid = new JobAdvertisementGrid(jobAdvertisementControl, jobApplicationControl,
                    authorizationControl, companyId, true,
                    true, true, false, true);
            jobAdvertisementGrid.setWidth("99%");
            jobAdvertisementGrid.loadGridData();
            HorizontalLayout jobGridLayout = new HorizontalLayout(jobAdvertisementGrid);
            jobGridLayout.setWidthFull();
            jobGridLayout.setHeight("800px");
            add(jobGridLayout);
        }
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

    public static void navigateToEdit(int companyId) {
        if(!Objects.equals(UtilCurrent.getCurrentLocation(), Globals.Pages.COMPANYPROFILE_EDIT_VIEW))
            UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_EDIT_VIEW + companyId);
    }

    public CompanyProfileView() {
        //Required because of VAADIN
    }

}

