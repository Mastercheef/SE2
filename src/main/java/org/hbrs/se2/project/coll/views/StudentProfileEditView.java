package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.hbrs.se2.project.coll.control.StudentProfileControl;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.impl.StudentUserDTOImpl;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "profile_edit", layout = MainLayout.class)
@PageTitle("Edit your Profile")
public class StudentProfileEditView extends VerticalLayout implements HasUrlParameter<String>  {

    @Autowired
    private StudentProfileControl profileControl;

    private StudentUserDTO profileDTO;

    //automatisches binding der Textfelder in ein DTO kann nicht durchgeführt werden,
    //da address eigene Entity ist und kein String
    //private Binder<StudentUserDTOImpl> binder = new Binder(StudentUserDTOImpl.class);

    Label       lfirstname     = new Label("Vorname:");
    Label       llastname      = new Label("Nachname:");
    Label       lgraduation = new Label("Abschluss:");
    Label       ldateOfBirth = new Label("Geburtsdatum:");
    Label       laddress       = new Label("Adresse:");
    Label       lskills        = new Label("Skills:");
    Label       lemail         = new Label("E-Mail:");
    Label       lphone = new Label("Telefon:");
    Label       linterests     = new Label("Interessen:");
    Label       lwebsite       = new Label("Webseite:");
    Label       ldescription = new Label("Über mich:");
    TextField   firstName    = new TextField();
    TextField   lastName     = new TextField();
    TextField   graduation   = new TextField();
    DatePicker   dateOfBirth    = new DatePicker();
    TextField   address      = new TextField();
    TextField   skills       = new TextField();
    TextField   email        = new TextField();
    TextField   phone       = new TextField();
    TextField   interests    = new TextField();
    TextField   website      = new TextField();
    TextField   description      = new TextField();

    Div         div           = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (!Utils.StringIsEmptyOrNull(parameter)) {
            profileDTO = profileControl.loadProfileDataByUserId(Integer.parseInt(parameter));
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
        // TODO: Get Data from UserDTO
        HorizontalLayout hfirstname = new HorizontalLayout();
        hfirstname.add(lfirstname, firstName);
        HorizontalLayout hlastname = new HorizontalLayout();
        hlastname.add(llastname, lastName);
        HorizontalLayout hoccupation = new HorizontalLayout();
        hoccupation.add(lgraduation, graduation);
        HorizontalLayout hbirthdate = new HorizontalLayout();
        hbirthdate.add(ldateOfBirth, dateOfBirth);
        HorizontalLayout haddress = new HorizontalLayout();
        haddress.add(laddress, address);
        HorizontalLayout hskills = new HorizontalLayout();
        hskills.add(lskills, skills);
        HorizontalLayout hemail = new HorizontalLayout();
        hemail.add(lemail, email);
        HorizontalLayout hnumber = new HorizontalLayout();
        hnumber.add(lphone, phone);
        HorizontalLayout hinterests = new HorizontalLayout();
        hinterests.add(linterests, interests);
        HorizontalLayout hwebsite = new HorizontalLayout();
        hwebsite.add(lwebsite, website);
        HorizontalLayout haboutme = new HorizontalLayout();
        haboutme.add(ldescription, description);

        // TODO: save in ProfileDTO (o.ä.)
        HorizontalLayout hbuttons = new HorizontalLayout();
        Button saveButton   = new Button("Speichern");
        saveButton.addClickListener(e -> {
            if (!checkForEmptyInput()) {
                profileControl.saveStudentUser(createStudenUserDTOImpl());
                UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + profileDTO.getUserId());
            }
        });
        Button cancelButton = new Button("Abbrechen");
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW +
                profileDTO.getUserId()));
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

    public StudentUserDTOImpl createStudenUserDTOImpl() {

        StudentUserDTOImpl studentUserDTO = new StudentUserDTOImpl();
        studentUserDTO.setUserId(profileDTO.getUserId());
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

        return studentUserDTO;
    }

    public boolean checkForEmptyInput() {
        return checkForEmptyTextField(firstName) ||
                checkForEmptyTextField(lastName) ||
                checkForEmptyTextField(graduation) ||
                checkForEmptyTextField(skills) ||
                checkForEmptyTextField(email) ||
                checkForEmptyTextField(phone) ||
                checkForEmptyTextField(interests) ||
                checkForEmptyTextField(website) ||
                checkForEmptyTextField(description);
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
}
