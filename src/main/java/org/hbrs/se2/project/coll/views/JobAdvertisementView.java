package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


@Route(value = "jobadvertisement", layout = AppView.class)
@PageTitle(Globals.PageTitles.JOBADVERTISEMENT_PAGE_TITLE)
public class JobAdvertisementView extends VerticalLayout implements HasUrlParameter<String>{

    @Autowired
    JobAdvertisementControl jobAdvertisementControl;

    @Autowired
    JobAdvertisementRepository jobAdvertisementRepository;

    Label typeOfEmployment      = new Label("Typ");
    Label workingHours          = new Label("Stunden/Woche");
    Label startOfWork           = new Label("Eintrittsdatum");
    Label endOfWork             = new Label("Austrittsdatum");
    Label jobDescription        = new Label("Stellenbeschreibung");
    Label requirements          = new Label("Anforderungen");
    Label address               = new Label("Adresse");
    Label phoneNumber           = new Label("Telefon");
    Label temporaryEmployment   = new Label("kurzfristige Beschäftigung ");
    Label contactPerson         = new Label("Kontaktperson");
    Label emailAddress          = new Label("E-Mail");
    Label salary                = new Label("Lohn");

    H2    jobTitle;
    Label lCompanyName;
    Label lTypeofEmployment;
    Label lWorkingHours;
    Label lStartOfWork;
    Label lEndOfWork;
    Label lJobDescription;
    Label lRequirements;
    Label lAddress;
    Label lPhoneNumber;
    Label lContactPerson;
    Label lEmailAddress;
    Label lTemporaryEmployment;
    Label lSalary;

    Button applyButton;

    Div div = new Div();

    private static final Logger LOGGER = Logger.getLogger(JobAdvertisementView.class.getName());

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        try {
            if (!Objects.equals(parameter, "")) {
                JobAdvertisement jobAdvertisement = jobAdvertisementControl.getJob(Integer.parseInt(parameter));
                initLabels(jobAdvertisement);
                setupJob();
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.toString());
        }
    }

    // Used to read DTO data and inject it into the labels
    public void initLabels(JobAdvertisement job) {
        jobTitle            = new H2(job.getJobTitle());
        lCompanyName        = new Label(jobAdvertisementControl.getCompanyName(job));
        lTypeofEmployment   = new Label(job.getTypeOfEmployment());
        lWorkingHours       = new Label(Short.toString(job.getWorkingHours()));
        lAddress            = new Label(jobAdvertisementControl.getCompanyAddress(job).toString());
        lPhoneNumber        = new Label(jobAdvertisementControl.getCompanyPhoneNumber(job));
        lContactPerson      = new Label(jobAdvertisementControl.getContactPersonName(job));
        lEmailAddress       = new Label(jobAdvertisementControl.getContactPersonEmail(job));
        lSalary             = new Label(Integer.toString(job.getSalary()));

        // Possible Null Fields
        lStartOfWork        = new Label(job.getStartOfWork() != null ? job.getStartOfWork().toString() : "");
        lEndOfWork          = new Label(job.getEndOfWork() != null ? job.getEndOfWork().toString() : "");
        lRequirements       = new Label(job.getRequirements() != null ? job.getRequirements() : "");
        lJobDescription     = new Label(job.getJobDescription() != null ? job.getJobDescription() : "");

        if(job.getTemporaryEmployment())
            lTemporaryEmployment = new Label("Ja");
        else
            lTemporaryEmployment = new Label("Nein");

        applyButton = new Button("Jetzt bewerben!");
        applyButton.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                job.getId() + "/" + Globals.Pages.APPLICATION_VIEW));
    }

    // Build content
    public void setupJob() {
        for (Label label : new Label[]{lCompanyName, typeOfEmployment, workingHours, startOfWork,
                endOfWork, jobDescription, requirements, address,
                phoneNumber, temporaryEmployment, contactPerson, emailAddress, salary}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");
        }

        HorizontalLayout hJobTitle          = new HorizontalLayout(jobTitle);
        HorizontalLayout hCompanyName       = new HorizontalLayout(lCompanyName);
        HorizontalLayout hTypeOfEmployment  = new HorizontalLayout(typeOfEmployment, lTypeofEmployment);
        HorizontalLayout hWorkingHours      = new HorizontalLayout(workingHours, lWorkingHours);
        HorizontalLayout hStartOfWork       = new HorizontalLayout(startOfWork, lStartOfWork);
        HorizontalLayout hEndOfWork         = new HorizontalLayout(endOfWork, lEndOfWork);
        HorizontalLayout hSalary            = new HorizontalLayout(salary, lSalary, new Label("€"));

        div.add(hJobTitle, hCompanyName, hTypeOfEmployment, hWorkingHours, hStartOfWork, hEndOfWork, hSalary);
        add(div);

        VerticalLayout vJobDescription  = new VerticalLayout(jobDescription, lJobDescription);
        VerticalLayout vRequirements    = new VerticalLayout(requirements, lRequirements);
        add(vJobDescription, vRequirements);

        HorizontalLayout hAddress               = new HorizontalLayout(address, lAddress);
        HorizontalLayout hPhoneNumber           = new HorizontalLayout(phoneNumber, lPhoneNumber);
        HorizontalLayout hTemporaryEmployment   = new HorizontalLayout(temporaryEmployment, lTemporaryEmployment);
        HorizontalLayout hContactPerson         = new HorizontalLayout(contactPerson, lContactPerson);
        HorizontalLayout hEmailAddress          = new HorizontalLayout(emailAddress, lEmailAddress);

        add(hAddress, hPhoneNumber, hTemporaryEmployment, hContactPerson, hEmailAddress);
        if (UtilCurrent.getCurrentUser().getType().equals("st"))
            add(applyButton);
    }

    JobAdvertisementView() {

    }

}
