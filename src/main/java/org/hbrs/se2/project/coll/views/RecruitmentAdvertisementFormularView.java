package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.CompanyProfileControl;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route(value = "recruitment_formular", layout = AppView.class)
@PageTitle("Anlegen eines Stellenangebots")
public class RecruitmentAdvertisementFormularView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    ContactPersonRepository contactPersonRepository;
    @Autowired
    private CompanyProfileControl profileControl;
    int companyId;

    Label jobName                   = new Label("Jobtitel");
    Label typeOfEmployment          = new Label("Typ");
    Label formOfEmployment          = new Label("Arbeitszeit");
    Label jobStart                  = new Label("Eintrittsdatum");
    Label workingLocation           = new Label("Arbeitsort");
    Label jobDescription            = new Label("Stellenbeschreibung");
    Label requirementForApplicants  = new Label("Anforderungen");
    Label businessAdress            = new Label("Adresse");
    Label telephoneNumber           = new Label("Telefon");
    Label temporaryEmployment       = new Label("kurzfristige Beschäftigung ");
    Label contactPerson             = new Label("Kontaktperson");
    Label emailAdress               = new Label("E-Mail");

    TextField       ljobName                    = new TextField();
    Select<String>  lTypeOfEmployment           = new Select<>();
    Select<String>  lFormOfEmployment           = new Select<>();
    DatePicker      lJobStart                   = new DatePicker ();
    TextField       lWorkingLocation            = new TextField();
    TextArea        lJobDescription             = new TextArea("Description");
    TextArea        lRequirementForApplicants   = new TextArea("Description");
    TextField       lStreet                     = new TextField("Straße");
    TextField       lHouseNumber                = new TextField( "Hausnummer");
    TextField       lPostalCode                 = new TextField("PLZ");
    TextField       lCity                       = new TextField("Stadt");
    NumberField     lTelephoneNumber            = new NumberField ("Nummer");
    Select<String>  lTemporaryEmployment        = new Select<>();
    TextField       lContactPerson              = new TextField();
    TextField       lEmailAdress                = new TextField("email");

    Div div           = new Div();

    // Recruitment Formular can only be accesses via ID and only as a Contact Person User
    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        try {
            if (!Objects.equals(parameter, "")) {
                if(checkIfUserIsLoggedIn()) {
                    CompanyProfileDTO profileDTO = profileControl
                            .findCompanyProfileByCompanyId(Integer.parseInt(parameter));
                    companyId = profileDTO.getId();
                    boolean ownership = checkIfUserIsProfileOwner(Integer.parseInt(parameter));
                    if (ownership)
                        initFormular();
                }
            }
        } catch (Exception e) {
            System.out.println("An exception has occured.");
            e.printStackTrace();
        }
    }

    public void initFormular() {
        H2 h2 = new H2("Job Formular");
        setSizeFull();

        for (Label label : new Label[]{jobName, typeOfEmployment, formOfEmployment, jobStart, workingLocation,
                jobDescription, requirementForApplicants, businessAdress, telephoneNumber, temporaryEmployment,
                contactPerson, emailAdress}) {
            label.getElement().getStyle().set("font-weight", "bold");
        }
        HorizontalLayout hJobName = new HorizontalLayout();
        hJobName.add(jobName, ljobName);
        hJobName.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        HorizontalLayout hlabelTypeOfEmployment = new HorizontalLayout();

        lTypeOfEmployment.setItems("Vollzeit", "Teilzeit", "Minijob", "Praktiktum");
        hlabelTypeOfEmployment.add(typeOfEmployment, lTypeOfEmployment);

        HorizontalLayout hBeginnOfJob = new HorizontalLayout();
        hBeginnOfJob.add(jobStart, lJobStart);

        HorizontalLayout hworkingLocation = new HorizontalLayout();
        hworkingLocation.add(workingLocation, lWorkingLocation);

        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(hJobName, hlabelTypeOfEmployment, hBeginnOfJob, hworkingLocation);


        VerticalLayout vJobDescription = new VerticalLayout();
        lJobDescription.setWidthFull();
        lJobDescription.getStyle().set("minHeight", "150px");
        vJobDescription.add(jobDescription, lJobDescription);

        VerticalLayout vRequirementForApplicants = new VerticalLayout();
        lRequirementForApplicants.setWidthFull();
        lRequirementForApplicants.getStyle().set("minHeight", "150px");
        vRequirementForApplicants.add(requirementForApplicants, lRequirementForApplicants);


        HorizontalLayout hbusinessAdress = new HorizontalLayout();
        hbusinessAdress.add(businessAdress, lStreet, lHouseNumber, lPostalCode, lCity);


        HorizontalLayout htelephoneNumber = new HorizontalLayout();
        lTelephoneNumber.setPlaceholder("0123456789");
        htelephoneNumber.add(telephoneNumber, lTelephoneNumber);

        HorizontalLayout hContactPerson = new HorizontalLayout();
        lContactPerson.setPlaceholder("Max Mustermann");
        hContactPerson.add(contactPerson, lContactPerson);

        HorizontalLayout hEmailAdress = new HorizontalLayout();
        hEmailAdress.add(emailAdress, lEmailAdress);

        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.add(htelephoneNumber,hContactPerson,hEmailAdress);
        for(HorizontalLayout hlayout : new HorizontalLayout[] {
                hJobName,hlabelTypeOfEmployment,hBeginnOfJob,hworkingLocation,hbusinessAdress,
                htelephoneNumber,hContactPerson,hEmailAdress,layout2
        }) {
            hlayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
            hlayout.setJustifyContentMode(JustifyContentMode.START);
            hlayout.getElement().getStyle().set("margin-top", "10px");
        }

        div.add(h2, h1,vJobDescription,
                vRequirementForApplicants,hbusinessAdress,layout2);
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
        int contactPersonId = contactPersonRepository.findContactPersonByCompany_Id(parameter).getId();

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

    public RecruitmentAdvertisementFormularView() {

    }
}
