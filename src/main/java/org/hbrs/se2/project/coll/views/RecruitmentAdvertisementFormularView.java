package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.CompanyControl;
import org.hbrs.se2.project.coll.control.RecruitmentAdvertisementControl;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.RecruitmentAdvertisingDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "recruitment_formular", layout = AppView.class)
@PageTitle("Anlegen eines Stellenangebots")
public class RecruitmentAdvertisementFormularView extends VerticalLayout implements HasUrlParameter<String> {

    private final static Logger LOGGER = Logger.getLogger(CompanyProfileView.class.getName());

    @Autowired
    ContactPersonRepository contactPersonRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private CompanyControl profileControl;
    int companyId;

    @Autowired
    private RecruitmentAdvertisementControl control;

    Label infoText              = new Label("Mit (*) markierte Felder sind notwendig.");
    Label jobTitle              = new Label("Jobtitel (*)");
    Label temporaryEmployment   = new Label("kurzfristige Beschäftigung (*)");
    Label typeOfEmployment      = new Label("Typ (*)");
    Label workingHours          = new Label("Stunden/Woche (*)");
    Label requirements          = new Label("Anforderungen");
    Label startOfWork           = new Label("Eintrittsdatum");
    Label endOfWork             = new Label("Enddatum");
    Label jobDescription        = new Label("Stellenbeschreibung (*)");

    TextField       lJobTitle            = new TextField();
    Select<String>  lTemporaryEmployment = new Select<>();
    Select<String>  lTypeOfEmployment    = new Select<>();
    TextField       lWorkingHours        = new TextField();
    TextArea        lRequirements        = new TextArea();
    DatePicker      lStartOfWork         = new DatePicker();
    DatePicker      lEndOfWork           = new DatePicker();
    TextArea        lJobDescription      = new TextArea();

    Div div           = new Div();

    // Recruitment Formular can only be accesses via ID and only as a Contact Person User
    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        try {
            if (!Objects.equals(parameter, "")) {
                if(checkIfUserIsLoggedIn()) {
                    CompanyDTO profileDTO = profileControl
                            .findCompanyProfileByCompanyId(Integer.parseInt(parameter));
                    companyId = profileDTO.getId();
                    boolean ownership = checkIfUserIsProfileOwner(Integer.parseInt(parameter));
                    if (ownership)
                        initFormular();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO,e.toString());
        }
    }

    public void initFormular() {
        H2 h2 = new H2("Job Formular");
        setSizeFull();

        for (Label label : new Label[]{jobTitle, temporaryEmployment, typeOfEmployment, workingHours, requirements,
                startOfWork, endOfWork, jobDescription}) {
            label.getElement().getStyle().set("font-weight", "bold");
        }

        // Title
        HorizontalLayout hJobTitle = new HorizontalLayout(jobTitle, lJobTitle);
        hJobTitle.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        // Type
        HorizontalLayout hTypeOfEmployment = new HorizontalLayout(typeOfEmployment, lTypeOfEmployment);
        lTypeOfEmployment.setItems("Vollzeit", "Teilzeit", "Minijob", "Praktikum");
        lTypeOfEmployment.setValue("Vollzeit"); // Default

        // Start/End of Job
        HorizontalLayout hStartOfWork = new HorizontalLayout(startOfWork, lStartOfWork);
        HorizontalLayout hEndOfWork   = new HorizontalLayout(endOfWork, lEndOfWork);

        // Temporary - Yes/No
        HorizontalLayout hTemporaryEmployment = new HorizontalLayout(temporaryEmployment, lTemporaryEmployment);
        lTemporaryEmployment.setItems("Ja", "Nein");
        lTemporaryEmployment.setValue("Nein"); // Default

        // Working Hours per Week
        HorizontalLayout hWorkingHours = new HorizontalLayout(workingHours, lWorkingHours);

        // Append
        HorizontalLayout first = new HorizontalLayout(hJobTitle, hTypeOfEmployment, hStartOfWork, hEndOfWork);
        HorizontalLayout second = new HorizontalLayout(hTemporaryEmployment, hWorkingHours);

        // Description
        VerticalLayout vJobDescription = new VerticalLayout(jobDescription, lJobDescription);
        lJobDescription.setWidthFull();
        lJobDescription.setMinHeight("150px");
        //lJobDescription.getElement().getStyle().set("height", "150px");

        // Requirements
        VerticalLayout vRequirements = new VerticalLayout(requirements, lRequirements);
        lRequirements.setWidthFull();
        lRequirements.setMinHeight("150px");

        // Alignment
        for(HorizontalLayout hlayout : new HorizontalLayout[] { hJobTitle, hTypeOfEmployment,
                hStartOfWork, hEndOfWork, hTemporaryEmployment, hWorkingHours}) {
            hlayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
            hlayout.setJustifyContentMode(JustifyContentMode.START);
            hlayout.getElement().getStyle().set("margin-top", "10px");
        }


        // Buttons to save or cancel
        HorizontalLayout hbuttons   = new HorizontalLayout();
        Button saveButton           = new Button("Speichern");
        Button cancelButton         = new Button("Abbrechen");

        saveButton.addClickListener(e -> {
            if(!checkForEmptyInput()) {
                createAndSaveNewJob();
                UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + companyId);
            }
        });
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW +
                companyId));
        hbuttons.add(saveButton, cancelButton);

        // Append to site
        div.add(h2, infoText, first, second, vJobDescription, vRequirements, hbuttons);
        div.setSizeFull();
        add(div);
    }

    // If the user is not logged in, they get redirected to the login page
    private boolean checkIfUserIsLoggedIn() {
        UserDTO userDTO = this.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            return false;
        }
        return true;
    }

    // If the user is not the owner of this profile, they get redirected to the profile
    private boolean checkIfUserIsProfileOwner(int parameter) {
        int userId = this.getCurrentUser().getId();
        int contactPersonId = contactPersonRepository.findContactPersonByCompanyId(parameter).getId();

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

    public boolean checkForEmptyInput() {
        return checkForEmptyTextField(lJobTitle) ||
                checkForEmptyTextField(lWorkingHours) ||
                checkForEmptyTextArea(lJobDescription);
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

    public boolean checkForEmptyTextArea(TextArea textArea) {
        boolean empty = Utils.stringIsEmptyOrNull(textArea.getValue());
        if (empty) {
            textArea.setInvalid(true);
            Notification notification = new Notification("Bitte geben Sie in das markierte Feld einen " +
                    "gültigen Wert ein.", 3000);
            notification.open();
        } else {
            textArea.setInvalid(false);
        }
        return empty;
    }

    public void createAndSaveNewJob() {
        RecruitmentAdvertisingDTOImpl newJob = new RecruitmentAdvertisingDTOImpl();
        newJob.setJobTitle(lJobTitle.getValue());
        newJob.setTypeOfEmployment(lTypeOfEmployment.getValue());
        newJob.setWorkingHours(Short.parseShort(lWorkingHours.getValue()));
        newJob.setRequirements(lRequirements.getValue());
        newJob.setStartOfWork(lStartOfWork.getValue());
        newJob.setEndOfWork(lEndOfWork.getValue());
        newJob.setJobDescription(lJobDescription.getValue());

        newJob.setTemporaryEmployment(Objects.equals(lTemporaryEmployment.getValue(), "Ja"));

        // Address
        Address address = companyRepository.findCompanyProfileById(companyId).getAddress();
        newJob.setAddress(address);

        // Contact Person Id
        ContactPerson contactPerson = contactPersonRepository.findContactPersonByCompanyId(companyId);
        newJob.setContactPerson(contactPerson);

        // Save in DB
        control.saveAdvertisement(newJob);
    }

    public RecruitmentAdvertisementFormularView() {
        //Required for Vaadin
    }
}
