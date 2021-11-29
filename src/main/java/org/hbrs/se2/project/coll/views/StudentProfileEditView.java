package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.StudentProfileControl;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.StudentUserDTOImpl;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "profile_edit", layout = AppView.class)
@PageTitle("Edit your Profile")
public class StudentProfileEditView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    @Autowired
    private StudentProfileControl profileControl;

    private StudentUserDTO profileDTO;

    //automatisches binding der Textfelder in ein DTO kann nicht durchgeführt werden,
    //da address eigene Entity ist und kein String
    //private Binder<StudentUserDTOImpl> binder = new Binder(StudentUserDTOImpl.class);

    Label       lfirstname      = new Label("Vorname:");
    Label       llastname       = new Label("Nachname:");
    Label       lgraduation     = new Label("Abschluss:");
    Label       ldateOfBirth    = new Label("Geburtsdatum:");
    Label       laddress        = new Label("Adresse:");
    Label       lskills         = new Label("Skills:");
    Label       lemail          = new Label("E-Mail:");
    Label       lphone          = new Label("Telefon:");
    Label       linterests      = new Label("Interessen:");
    Label       lwebsite        = new Label("Webseite:");
    Label       ldescription    = new Label("Über mich:");

    TextField   firstName       = new TextField();
    TextField   lastName        = new TextField();
    TextField   graduation      = new TextField();
    DatePicker  dateOfBirth     = new DatePicker();
    TextField   address         = new TextField();
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
        if (!Utils.StringIsEmptyOrNull(parameter)) {
            profileDTO = profileControl.loadProfileDataById(Integer.parseInt(parameter));
            firstName.setValue(profileDTO.getFirstName());
            lastName.setValue(profileDTO.getLastName());
            graduation.setValue(profileDTO.getGraduation());
            dateOfBirth.setValue(profileDTO.getDateOfBirth());
            address.setValue(profileDTO.getAddress().toString());
            skills.setValue(profileDTO.getSkills());
            email.setValue(profileDTO.getEmail());
            phone.setValue(profileDTO.getPhone());
            interests.setValue(profileDTO.getInterests());
            website.setValue(profileDTO.getWebsite());
            description.setValue(profileDTO.getDescription());
        }
        createProfileEditView();
    }

    // TODO: Profilbild

    public StudentProfileEditView() {

    }

    public void createProfileEditView() {

        //binder.bindInstanceFields(this);

        // Layout
        H2 h2 = new H2("Editiere mein Profil");
        //h2.getElement().getStyle().set("margin-top", "55px");

        // TODO: Get Image from Database
        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");

        // Styling
        for (Label label : new Label[]{ lfirstname, llastname, lgraduation, ldateOfBirth, laddress, lskills, lemail,
                lphone, linterests, lwebsite, ldescription}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        for (TextField textfield : new TextField[]{ firstName, lastName, graduation, address, skills, email,
                phone, interests, website, description }) {
            textfield.getElement().getStyle().set("height", "20px");
            textfield.getElement().getStyle().set("width", "300px");
        }

        // Profile Data
        HorizontalLayout hfirstname     = new HorizontalLayout();
        HorizontalLayout hlastname      = new HorizontalLayout();
        HorizontalLayout hoccupation    = new HorizontalLayout();
        HorizontalLayout hbirthdate     = new HorizontalLayout();
        HorizontalLayout haddress       = new HorizontalLayout();
        HorizontalLayout hskills        = new HorizontalLayout();
        HorizontalLayout hemail         = new HorizontalLayout();
        HorizontalLayout hnumber        = new HorizontalLayout();
        HorizontalLayout hinterests     = new HorizontalLayout();
        HorizontalLayout hwebsite       = new HorizontalLayout();
        HorizontalLayout haboutme       = new HorizontalLayout();

        hfirstname.add(lfirstname, firstName);
        hlastname.add(llastname, lastName);
        hoccupation.add(lgraduation, graduation);
        hbirthdate.add(ldateOfBirth, dateOfBirth);
        haddress.add(laddress, address);
        hskills.add(lskills, skills);
        hemail.add(lemail, email);
        hnumber.add(lphone, phone);
        hinterests.add(linterests, interests);
        hwebsite.add(lwebsite, website);
        haboutme.add(ldescription, description);

        // Create Save and Cancel Buttons
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button saveButton   = new Button("Speichern");
        saveButton.addClickListener(e -> {
            if (!checkForEmptyInput()) {
                profileControl.saveStudentUser(createStudentUserDTOImpl());
                UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + profileDTO.getId());
            }
        });
        Button cancelButton = new Button("Abbrechen");
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW +
                profileDTO.getId()));
        hbuttons.add(saveButton, cancelButton);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hfirstname, hlastname, hoccupation, hbirthdate, haddress,
                hskills, hemail, hnumber, hinterests, hwebsite, haboutme, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "10px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hfirstname, hlastname, hoccupation,
                hbirthdate, haddress, hskills, hemail, hnumber, hinterests,
                hwebsite, haboutme, hbuttons);
        add(div);
    }

    public StudentUserDTOImpl createStudentUserDTOImpl() {

        StudentUserDTOImpl studentUserDTO = new StudentUserDTOImpl();
        studentUserDTO.setId(profileDTO.getId());
        studentUserDTO.setSalutation(profileDTO.getSalutation());
        studentUserDTO.setTitle(profileDTO.getTitle());
        studentUserDTO.setFirstname(firstName.getValue());
        studentUserDTO.setLastname(lastName.getValue());
        studentUserDTO.setGraduation(graduation.getValue());
        studentUserDTO.setDateOfBirth(dateOfBirth.getValue());
        studentUserDTO.setAddress(profileDTO.getAddress());
        studentUserDTO.setSkills(skills.getValue());
        studentUserDTO.setEmail(email.getValue());
        studentUserDTO.setPassword(profileDTO.getPassword());
        studentUserDTO.setPhone(phone.getValue());
        studentUserDTO.setInterests(interests.getValue());
        studentUserDTO.setWebsite(website.getValue());
        studentUserDTO.setDescription(description.getValue());
        studentUserDTO.setType("st");

        return studentUserDTO;
    }

    public boolean checkForEmptyInput() {
        return checkForEmptyTextField(firstName) ||
                checkForEmptyTextField(lastName) ||
                checkForEmptyTextField(email) ||
                checkForEmptyTextField(phone);
    }

    public boolean checkForEmptyTextField(TextField textField) {
        boolean empty = Utils.StringIsEmptyOrNull(textField.getValue());
        if (empty) {
            textField.setInvalid(true);
            textField.setErrorMessage("Bitte gib einen Wert ein");
        } else {
            textField.setInvalid(false);
        }
        return empty;
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = this.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            return false;
        }
        return true;
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
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
        if (getCurrentUser() == null){
            beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }

    }
}
