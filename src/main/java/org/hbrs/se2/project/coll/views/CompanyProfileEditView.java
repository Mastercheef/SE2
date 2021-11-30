package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.hbrs.se2.project.coll.control.CompanyProfileControl;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route(value = "companyprofile_edit", layout = AppView.class)
@PageTitle("Edit your Profile")
public class CompanyProfileEditView extends VerticalLayout  implements HasUrlParameter<String> {


    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyProfileControl profileControl;
    List<Address> existingAddresses;
    Address address = new Address();
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

    TextField lcompanyname  = new TextField();
    TextField lstreet       = new TextField();
    TextField lstreetnumber = new TextField();
    TextField lpostalcode   = new TextField();
    TextField lcity         = new TextField();
    TextField lcountry      = new TextField();
    TextField lemail        = new TextField();
    TextField lphone        = new TextField();
    TextField lfax          = new TextField();
    TextField lwebsite      = new TextField();
    TextField ldescription  = new TextField();

    Div div = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (!parameter.equals("")) {
            CompanyProfileDTO profileDTO = profileControl.findCompanyProfileByCompanyId(Integer.parseInt(parameter));
            existingAddresses            = addressRepository.getByIdAfter(0);

            // Skip ID so one can be generated. Important for saving new Addresses in DB.
            address.setStreet(profileDTO.getAddress().getStreet());
            address.setHouseNumber(profileDTO.getAddress().getHouseNumber());
            address.setPostalCode(profileDTO.getAddress().getPostalCode());
            address.setCity(profileDTO.getAddress().getCity());
            address.setCountry(profileDTO.getAddress().getCountry());

            initLabels(profileDTO);
            createProfile();
        }
    }

    // Used to read DTO data and inject it into the labels
    public void initLabels(CompanyProfileDTO profileDTO) {
        companyId = profileDTO.getId();
        lcompanyname.setPlaceholder(profileDTO.getCompanyName());
        lstreet.setPlaceholder(address.getStreet());
        lstreetnumber.setPlaceholder(address.getHouseNumber());
        lpostalcode.setPlaceholder(address.getPostalCode());
        lcity.setPlaceholder(address.getCity());
        lcountry.setPlaceholder(address.getCountry());
        lemail.setPlaceholder(profileDTO.getEmail());
        lphone.setPlaceholder(String.valueOf(profileDTO.getPhoneNumber()));
        lfax.setPlaceholder(String.valueOf(profileDTO.getFaxNumber()));
        lwebsite.setPlaceholder(profileDTO.getWebsite());
        ldescription.setPlaceholder(profileDTO.getDescription());
    }

    // TODO: Profilbild
    // Build profile content
    public void createProfile() {
        H2 h2 = new H2("Editiere mein Firmenprofil");

        // TODO: Get Image from Database
        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ companyname, street, streetnumber, postalcode, city, country,
                email, phone, fax, website, description}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        for (TextField textfield : new TextField[]{ lcompanyname, lstreet, lstreetnumber, lpostalcode, lcity, lcountry,
                lemail, lphone, lfax, lwebsite, ldescription}) {
            textfield.getElement().getStyle().set("height", "20px");
            textfield.getElement().getStyle().set("width", "300px");
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

        // Buttons for saving/cancelling
        HorizontalLayout hbuttons   = new HorizontalLayout();
        Button saveButton           = new Button("Speichern");
        Button cancelButton         = new Button("Abbrechen");

        // Create buttons to save in database and cancel progress
        saveButton.addClickListener(e -> {
                    updateProfileData();
                    UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + companyId);
                });
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW +
                companyId));
        hbuttons.add(saveButton, cancelButton);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hcompanyname, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hcompanyname, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription, hbuttons);
        add(div);
    }

    // Used to save modified data in the database
    public void updateProfileData() {

        // Only modify data if Textfields are not empty. Otherwise, use previous data.
        CompanyProfile updatedProfile = new CompanyProfile();

        // ID
        updatedProfile.setId(companyId);

        // Company Name
        if(!Objects.equals(lcompanyname.getValue(), ""))
            updatedProfile.setCompanyName(lcompanyname.getValue());
        else
            updatedProfile.setCompanyName(lcompanyname.getPlaceholder());

        // Address
        // Street
        if(!Objects.equals(lstreet.getValue(), ""))
            address.setStreet(lstreet.getValue());
        else
            address.setStreet(lstreet.getPlaceholder());

        // Streetnumber
        if(!Objects.equals(lstreetnumber.getValue(), ""))
            address.setHouseNumber(lstreetnumber.getValue());
        else
            address.setHouseNumber(lstreetnumber.getPlaceholder());

        // Postalcode
        if(!Objects.equals(lpostalcode.getValue(), ""))
            address.setPostalCode(lpostalcode.getValue());
        else
            address.setPostalCode(lpostalcode.getPlaceholder());

        // City
        if(!Objects.equals(lcity.getValue(), ""))
            address.setCity(lcity.getValue());
        else
            address.setCity(lcity.getPlaceholder());

        // Country
        if(!Objects.equals(lcountry.getValue(), ""))
            address.setCountry(lcountry.getValue());
        else
            address.setCountry(lcountry.getPlaceholder());

        // Check DB for existing address
        int newAddressID = checkAddressExistence(address, existingAddresses);
        if(newAddressID != -1) {
            address = addressRepository.getById(newAddressID);
        } else {
            addressRepository.save(address);
        }
        updatedProfile.setAddress(address);

        // Email
        if(!Objects.equals(lemail.getValue(), ""))
            updatedProfile.setEmail(lemail.getValue());
        else
            updatedProfile.setEmail(lemail.getPlaceholder());

        // Phone
        if(!Objects.equals(lphone.getValue(), ""))
            updatedProfile.setPhoneNumber(Integer.parseInt(lphone.getValue()));
        else
            updatedProfile.setPhoneNumber(Integer.parseInt(lphone.getPlaceholder()));

        // Fax
        if(!Objects.equals(lfax.getValue(), ""))
            updatedProfile.setFaxNumber(Integer.parseInt(lfax.getValue()));
        else
            updatedProfile.setFaxNumber(Integer.parseInt(lfax.getPlaceholder()));

        // Website
        if(!Objects.equals(lwebsite.getValue(), ""))
            updatedProfile.setWebsite(lwebsite.getValue());
        else
            updatedProfile.setWebsite(lwebsite.getPlaceholder());

        // Description
        if(!Objects.equals(ldescription.getValue(), ""))
            updatedProfile.setDescription(ldescription.getValue());
        else
            updatedProfile.setDescription(ldescription.getPlaceholder());

        // Save in DB
        companyProfileRepository.save(updatedProfile);
    }

    /*  We have to check if the address we edited in the View already exists in the DB.
        Return True  if it exists
        Return False if it does not exist
    */
    public int checkAddressExistence(Address a, List<Address> addresses) {
        int ID = -1;

        for(Address b : addresses) {
            if(Objects.equals(a.getStreet(), b.getStreet()) &&
                    Objects.equals(a.getHouseNumber(), b.getHouseNumber()) &&
                    Objects.equals(a.getPostalCode(), b.getPostalCode()) &&
                    Objects.equals(a.getCity(), b.getCity()) &&
                    Objects.equals(a.getCountry(), b.getCountry())) {
                ID = b.getId();
                break;
            }
        }
        return ID;
    }
}