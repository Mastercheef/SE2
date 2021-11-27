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
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route(value = "companyprofile_edit", layout = MainLayout.class)
@PageTitle("Edit your Profile")
public class CompanyProfileEditView extends VerticalLayout  implements HasUrlParameter<String> {


    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyProfileControl profileControl;
    List<Address> existingAddresses;
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

            // Skip ID so one can be generated.
            addr.setStreet(profileDTO.getAddress().getStreet());
            addr.setHouseNumber(profileDTO.getAddress().getHouseNumber());
            addr.setPostalCode(profileDTO.getAddress().getPostalCode());
            addr.setCity(profileDTO.getAddress().getCity());
            addr.setCountry(profileDTO.getAddress().getCountry());

            initLabels(profileDTO);
            createProfile();
        }
    }

    // Used to read DTO data and inject them into the labels
    public void initLabels(CompanyProfileDTO profileDTO) {
        companyId = profileDTO.getId();
        lcompanyname.setPlaceholder(profileDTO.getCompanyName());
        lstreet.setPlaceholder(addr.getStreet());
        lstreetnumber.setPlaceholder(addr.getHouseNumber());
        lpostalcode.setPlaceholder(addr.getPostalCode());
        lcity.setPlaceholder(addr.getCity());
        lcountry.setPlaceholder(addr.getCountry());
        lemail.setPlaceholder(profileDTO.getEmail());
        lphone.setPlaceholder(String.valueOf(profileDTO.getPhoneNumber()));
        lfax.setPlaceholder(String.valueOf(profileDTO.getFaxNumber()));
        lwebsite.setPlaceholder(profileDTO.getWebsite());
        ldescription.setPlaceholder(profileDTO.getDescription());
    }

    // TODO: Profilbild

    public void createProfile() {

        // Layout
        H2 h2 = new H2("Editiere mein Firmenprofil");
        //h2.getElement().getStyle().set("margin-top", "55px");

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
            addr.setStreet(lstreet.getValue());
        else
            addr.setStreet(lstreet.getPlaceholder());

        // Streetnumber
        if(!Objects.equals(lstreetnumber.getValue(), ""))
            addr.setHouseNumber(lstreetnumber.getValue());
        else
            addr.setHouseNumber(lstreetnumber.getPlaceholder());

        // Postalcode
        if(!Objects.equals(lpostalcode.getValue(), ""))
            addr.setPostalCode(lpostalcode.getValue());
        else
            addr.setPostalCode(lpostalcode.getPlaceholder());

        // City
        if(!Objects.equals(lcity.getValue(), ""))
            addr.setCity(lcity.getValue());
        else
            addr.setCity(lcity.getPlaceholder());

        // Country
        if(!Objects.equals(lcountry.getValue(), ""))
            addr.setCountry(lcountry.getValue());
        else
            addr.setCountry(lcountry.getPlaceholder());

        // Check DB for existing address
        int newAddressID = checkAddressExistence(addr, existingAddresses);
        if(newAddressID != -1) {
            addr = addressRepository.getById(newAddressID);
        } else {
            addressRepository.save(addr);
        }
        updatedProfile.setAddress(addr);

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
