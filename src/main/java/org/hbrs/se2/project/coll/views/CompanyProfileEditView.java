package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.hbrs.se2.project.coll.control.CompanyProfileControl;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.CompanyProfileDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "companyprofile_edit", layout = AppView.class)
@PageTitle("Edit your Profile")
public class CompanyProfileEditView extends VerticalLayout  implements HasUrlParameter<String> {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    ContactPersonRepository contactPersonRepository;

    @Autowired
    private CompanyProfileControl profileControl;
    List<Address> existingAddresses;
    Address address = new Address();
    int companyId;

    Label infoText      = new Label("Mit (*) markierte Felder sind notwendig.");
    Label companyname   = new Label("Firmenname (*):");
    Label street        = new Label("Strasse (*):");
    Label streetnumber  = new Label("Hausnummer (*):");
    Label postalcode    = new Label("PLZ (*):");
    Label city          = new Label("Ort (*):");
    Label country       = new Label("Land (*):");
    Label email         = new Label("E-Mail (*):");
    Label phone         = new Label("Telefon (*):");
    Label fax           = new Label("Fax (*):");
    Label website       = new Label("Webseite (*):");
    Label description   = new Label("Beschreibung (*):");

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
            checkIfUserIsLoggedIn();
            CompanyProfileDTO profileDTO = profileControl.findCompanyProfileByCompanyId(Integer.parseInt(parameter));
            companyId = profileDTO.getId();
            boolean ownership = checkIfUserIsProfileOwner();
            if(ownership) {
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
    }

    // Used to read DTO data and inject it into the labels
    public void initLabels(CompanyProfileDTO profileDTO) {
        lcompanyname.setValue(profileDTO.getCompanyName());
        lstreet.setValue(address.getStreet());
        lstreetnumber.setValue(address.getHouseNumber());
        lpostalcode.setValue(address.getPostalCode());
        lcity.setValue(address.getCity());
        lcountry.setValue(address.getCountry());
        lemail.setValue(profileDTO.getEmail());
        lphone.setValue(String.valueOf(profileDTO.getPhoneNumber()));
        lfax.setValue(String.valueOf(profileDTO.getFaxNumber()));
        lwebsite.setValue(profileDTO.getWebsite());
        ldescription.setValue(profileDTO.getDescription());
    }

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
            textfield.setRequired(true);
        }

        // Profile Data
        HorizontalLayout hinfotext      = new HorizontalLayout(infoText);
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
            if(!checkForEmptyInput()) {
                updateProfileData();
                UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + companyId);
            }
        });
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW +
                companyId));
        hbuttons.add(saveButton, cancelButton);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hinfotext, hcompanyname, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hinfotext, hcompanyname, hstreet, hstreetnumber, hpostalcode,
                hcity, hcountry, hemail, hphone, hfax, hwebsite, hdescription, hbuttons);
        add(div);
    }

    // Used to save modified data in the database. Empty fields get marked as soon as save button is clicked.
    public void updateProfileData() {
        CompanyProfileDTOImpl updatedProfile = new CompanyProfileDTOImpl();
        updatedProfile.setId(companyId);
        updatedProfile.setCompanyName(lcompanyname.getValue());
        updatedProfile.setEmail(lemail.getValue());
        updatedProfile.setPhoneNumber(Integer.parseInt(lphone.getValue()));
        updatedProfile.setFaxNumber(Integer.parseInt(lfax.getValue()));
        updatedProfile.setWebsite(lwebsite.getValue());
        updatedProfile.setDescription(ldescription.getValue());

        // Address
        address.setStreet(lstreet.getValue());
        address.setHouseNumber(lstreetnumber.getValue());
        address.setPostalCode(lpostalcode.getValue());
        address.setCity(lcity.getValue());
        address.setCountry(lcountry.getValue());
        updatedProfile.setAddress(address);

        // Save in DB
        profileControl.saveCompanyProfile(updatedProfile);
    }

    /*  We have to check if the address we edited in the View already exists in the DB.
        Return True  if it exists
        Return False if it does not exist
    */

    public boolean checkForEmptyInput() {
        return checkForEmptyTextField(lcompanyname) ||
                checkForEmptyTextField(lstreet) ||
                checkForEmptyTextField(lstreetnumber) ||
                checkForEmptyTextField(lpostalcode) ||
                checkForEmptyTextField(lcity) ||
                checkForEmptyTextField(lcountry) ||
                checkForEmptyTextField(lemail) ||
                checkForEmptyTextField(lphone) ||
                checkForEmptyTextField(lfax) ||
                checkForEmptyTextField(lwebsite) ||
                checkForEmptyTextField(ldescription);
    }
    public boolean checkForEmptyTextField(TextField textField) {
        boolean empty = Utils.StringIsEmptyOrNull(textField.getValue());
        if (empty) {
            textField.setInvalid(true);
            Notification notification = new Notification("Bitte geben Sie in das markierte Feld einen " +
                    "gültigen Wert ein.", 3000);
            notification.open();
        } else {
            textField.setInvalid(false);
        }
        return empty;
    }

    // If the user is not logged in, they get redirected to the login page
    private void checkIfUserIsLoggedIn() {
        UserDTO userDTO = this.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
        }
    }

    // If the user is not the owner of this profile, they get redirected to the profile
    private boolean checkIfUserIsProfileOwner() {
        int userId = this.getCurrentUser().getId();
        int contactPersonId = contactPersonRepository.findContactPersonByCompany_Id(companyId).getId();

        if(userId == contactPersonId)
            return true;
        else if(userId == companyId)
            return true;
        else
        {
            UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + companyId);
            UI.getCurrent().getPage().reload();
            return false;
        }
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}