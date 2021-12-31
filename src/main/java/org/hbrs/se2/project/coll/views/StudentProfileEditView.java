package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.StudentProfileControl;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.StudentUserDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "profile_edit", layout = AppView.class)
@PageTitle("StudentProfileEdit")
public class StudentProfileEditView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    @Autowired
    private StudentProfileControl profileControl;

    private StudentUserDTO profileDTO;
    private Address addr = new Address();
    int studentId;

    //automatisches binding der Textfelder in ein DTO kann nicht durchgeführt werden,
    //da address eigene Entity ist und kein String
    //private Binder<StudentUserDTOImpl> binder = new Binder(StudentUserDTOImpl.class);

    Label       infoText        = new Label("Mit (*) markierte Felder sind notwendig.");
    Label       lsalutation     = new Label("Anrede (*):");
    Label       ltitle          = new Label("Titel:");
    Label       lfirstname      = new Label("Vorname (*):");
    Label       llastname       = new Label("Nachname (*):");
    Label       lgraduation     = new Label("Abschluss:");
    Label       ldateOfBirth    = new Label("Geburtsdatum (*):");
    Label       lstreet         = new Label("Strasse (*):");
    Label       lstreetnumber   = new Label("Hausnummer (*):");
    Label       lpostalcode     = new Label("PLZ (*):");
    Label       lcity           = new Label("Ort (*):");
    Label       lcountry        = new Label("Land (*):");
    Label       lskills         = new Label("Skills:");
    Label       lemail          = new Label("E-Mail (*):");
    Label       lphone          = new Label("Telefon (*):");
    Label       linterests      = new Label("Interessen:");
    Label       lwebsite        = new Label("Webseite:");
    Label       ldescription    = new Label("Über mich:");

    TextField   salutation      = new TextField();
    TextField   title           = new TextField();
    TextField   firstName       = new TextField();
    TextField   lastName        = new TextField();
    TextField   graduation      = new TextField();
    DatePicker  dateOfBirth     = new DatePicker();
    TextField   street          = new TextField();
    TextField   streetnumber    = new TextField();
    TextField   postalcode      = new TextField();
    TextField   city            = new TextField();
    TextField   country         = new TextField();
    TextField   skills          = new TextField();
    TextField   email           = new TextField();
    TextField   phone           = new TextField();
    TextField   interests       = new TextField();
    TextField   website         = new TextField();
    TextField   description     = new TextField();

    Div         div           = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (!Utils.stringIsEmptyOrNull(parameter)) {

            if(checkIfUserIsLoggedIn()){
                profileDTO = profileControl.loadProfileDataById(Integer.parseInt(parameter));
                studentId  = profileDTO.getId();

                boolean ownership = checkIfUserIsProfileOwner();

                if(ownership) {
                    initLabels(profileDTO);
                    createProfileEditView();
                }
            }
        }
    }

    // Used to read DTO data and inject them into the labels
    public void initLabels(StudentUserDTO profileDTO) {

        // Skip ID so one can be generated.
        addr.setStreet(profileDTO.getAddress().getStreet());
        addr.setHouseNumber(profileDTO.getAddress().getHouseNumber());
        addr.setPostalCode(profileDTO.getAddress().getPostalCode());
        addr.setCity(profileDTO.getAddress().getCity());
        addr.setCountry(profileDTO.getAddress().getCountry());

        studentId = profileDTO.getId();
        salutation.setValue(profileDTO.getSalutation());
        title.setValue(profileDTO.getTitle());
        firstName.setValue(profileDTO.getFirstName());
        lastName.setValue(profileDTO.getLastName());
        dateOfBirth.setValue(profileDTO.getDateOfBirth());
        street.setValue(addr.getStreet());
        streetnumber.setValue(addr.getHouseNumber());
        postalcode.setValue(addr.getPostalCode());
        city.setValue(addr.getCity());
        country.setValue(addr.getCountry());
        graduation.setValue(profileDTO.getGraduation() == null ? "" : profileDTO.getGraduation());
        skills.setValue(profileDTO.getSkills() == null ? "" : profileDTO.getSkills());
        email.setValue(profileDTO.getEmail() == null ? "" : profileDTO.getEmail());
        phone.setValue(profileDTO.getPhone() == null ? "" : profileDTO.getPhone());
        interests.setValue(profileDTO.getInterests() == null ? "" : profileDTO.getInterests());
        website.setValue(profileDTO.getWebsite() == null ? "" : profileDTO.getWebsite());
        description.setValue(profileDTO.getDescription() == null ? "" : profileDTO.getDescription());
    }

    // Build profile content
    public void createProfileEditView() {
        H2 h2 = new H2("Editiere mein Profil");

        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ lsalutation, ltitle, lfirstname, llastname, lgraduation, ldateOfBirth, lstreet,
                lstreetnumber, lpostalcode, lcity, lcountry, lskills, lemail, lphone, linterests, lwebsite,
                ldescription}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        for (TextField textfield : new TextField[]{ salutation, title, firstName, lastName, graduation, street,
                streetnumber, postalcode, city, country, skills, email, phone, interests, website, description }) {
            textfield.getElement().getStyle().set("height", "20px");
            textfield.getElement().getStyle().set("width", "300px");
        }

        // Profile Data
        HorizontalLayout hinfotext      = new HorizontalLayout(infoText);
        HorizontalLayout hsalutation    = new HorizontalLayout(lsalutation, salutation);
        HorizontalLayout htitle         = new HorizontalLayout(ltitle, title);
        HorizontalLayout hfirstname     = new HorizontalLayout(lfirstname, firstName);
        HorizontalLayout hlastname      = new HorizontalLayout(llastname, lastName);
        HorizontalLayout hoccupation    = new HorizontalLayout(lgraduation, graduation);
        HorizontalLayout hbirthdate     = new HorizontalLayout(ldateOfBirth, dateOfBirth);
        HorizontalLayout hstreet        = new HorizontalLayout(lstreet, street);
        HorizontalLayout hstreetnumber  = new HorizontalLayout(lstreetnumber, streetnumber);
        HorizontalLayout hpostalcode    = new HorizontalLayout(lpostalcode, postalcode);
        HorizontalLayout hcity          = new HorizontalLayout(lcity, city);
        HorizontalLayout hcountry       = new HorizontalLayout(lcountry, country);
        HorizontalLayout hskills        = new HorizontalLayout(lskills, skills);
        HorizontalLayout hemail         = new HorizontalLayout(lemail, email);
        HorizontalLayout hnumber        = new HorizontalLayout(lphone, phone);
        HorizontalLayout hinterests     = new HorizontalLayout(linterests, interests);
        HorizontalLayout hwebsite       = new HorizontalLayout(lwebsite, website);
        HorizontalLayout haboutme       = new HorizontalLayout(ldescription, description);

        // Create Save and Cancel Buttons
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button saveButton         = new Button("Speichern");
        Button cancelButton       = new Button("Abbrechen");

        saveButton.addClickListener(e -> {
            if(!checkForEmptyInput()) {
                updateProfileData();
                UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + studentId);
            }
        });
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW +
                studentId));
        hbuttons.add(saveButton, cancelButton);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hinfotext, hsalutation, htitle, hfirstname, hlastname,
                hoccupation, hbirthdate, hstreet, hstreetnumber, hpostalcode, hcity, hcountry, hskills, hemail,
                hnumber, hinterests, hwebsite, haboutme, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "10px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hinfotext, hsalutation, htitle, hfirstname, hlastname, hoccupation, hbirthdate,
                hstreet, hstreetnumber, hpostalcode, hcity, hcountry, hskills, hemail, hnumber, hinterests, hwebsite,
                haboutme, hbuttons);
        add(div);
    }

    // Used to save modified data in the database. Empty fields get marked as soon as save button is clicked.
    public void updateProfileData() {
        StudentUserDTOImpl updatedProfile = new StudentUserDTOImpl();
        updatedProfile.setId(studentId);
        updatedProfile.setType("st");
        updatedProfile.setPassword(profileDTO.getPassword());
        updatedProfile.setSalutation(salutation.getValue());
        updatedProfile.setTitle(title.getValue());
        updatedProfile.setFirstName(firstName.getValue());
        updatedProfile.setLastName(lastName.getValue());
        updatedProfile.setGraduation(graduation.getValue());
        updatedProfile.setDateOfBirth(dateOfBirth.getValue());
        updatedProfile.setSkills(skills.getValue());
        updatedProfile.setEmail(email.getValue());
        updatedProfile.setPhone(phone.getValue());
        updatedProfile.setInterests(interests.getValue());
        updatedProfile.setWebsite(website.getValue());
        updatedProfile.setDescription(description.getValue());

        // Address
        addr.setStreet(street.getValue());
        addr.setHouseNumber(streetnumber.getValue());
        addr.setPostalCode(postalcode.getValue());
        addr.setCity(city.getValue());
        addr.setCountry(country.getValue());
        updatedProfile.setAddress(addr);

        try {
            profileControl.updateStudentProfile(updatedProfile);
            // Fehlerbehandlung der einzelnen Textfields wie in RegistrationControl
        } catch (Exception exception) {
            // Rückmeldung über popup window
        }
    }

    public boolean checkForEmptyInput() {
        return checkForEmptyTextField(salutation) ||
                checkForEmptyTextField(firstName) ||
                checkForEmptyTextField(lastName) ||
                checkForEmptyTextField(street) ||
                checkForEmptyTextField(streetnumber) ||
                checkForEmptyTextField(postalcode) ||
                checkForEmptyTextField(city) ||
                checkForEmptyDatePicker(dateOfBirth) ||
                checkForEmptyTextField(country) ||
                checkForEmptyTextField(email) ||
                checkForEmptyTextField(phone);
    }

    public boolean checkForEmptyTextField(TextField textField) {
        boolean empty = Utils.stringIsEmptyOrNull(textField.getValue());
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

    public boolean checkForEmptyDatePicker(DatePicker datePicker) {
        boolean empty = datePicker.isEmpty();
        if (empty) {
            datePicker.setInvalid(true);
            Notification notification = new Notification("Bitte geben Sie in das markierte Feld einen " +
                    "gültigen Wert ein.", 3000);
            notification.open();
        } else {
            datePicker.setInvalid(false);
        }
        return empty;
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = Utils.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            return false;
        }
        else
            return true;
    }

    // If the user is not the owner of this profile, they get redirected to the profile
    private boolean checkIfUserIsProfileOwner() {
        if(Utils.getCurrentUser() != null && Utils.getCurrentUser().getId() != studentId)
        {
            UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + studentId);
            UI.getCurrent().getPage().reload();
            return false;
        }
        else
            return true;
    }

    @Override
    /**
     * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen.
     * Hier kann man die finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist
     * Dann erfolgt hier ein ReDirect auf die Login-Seite. Eine Navigation (Methode navigate)
     * ist hier nicht möglich, da die finale Navigation noch nicht stattgefunden hat.
     * Diese Methode in der AppLayout sichert auch den un-authorisierten Zugriff auf die innerliegenden
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (Utils.getCurrentUser() == null){
            beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }
    }

}
