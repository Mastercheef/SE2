package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.AuthorizationControl;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.control.JobApplicationControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hbrs.se2.project.coll.util.Globals.DateRanges;

import java.time.LocalDate;
import java.util.List;

@Route(value = "dashboard", layout = AppView.class)
@PageTitle("Job Dashboard")
public class JobDashboardView extends Div implements AfterNavigationObserver, BeforeEnterObserver {

    @Autowired
    JobAdvertisementControl jobAdvertisementControl;
    @Autowired
    JobApplicationControl jobApplicationControl;
    @Autowired
    AuthorizationControl authorizationControl;

    Grid<JobAdvertisement> grid = new Grid<>();
    Grid<JobApplicationDTO> applicationGrid = new Grid<>();

    private int companyId = 0;

    TextField jobTitleFilter            = new TextField();
    TextField requirementsFilter        = new TextField();

    ComboBox<String> jobTypeFilter      = new ComboBox<>();
    ComboBox<Boolean> temporaryFilter   = new ComboBox<>();

    // old values to prevent nullptr errors
    DatePicker entryDateFilter          = new DatePicker();
    LocalDate oldDate;

    IntegerField workingHoursFilter     = new IntegerField();
    int oldWorkingHours;
    IntegerField salaryFilter           = new IntegerField();
    int oldSalary;

    //Applications
    TextField appHeadlineFilter         = new TextField();
    TextField appUserFilter             = new TextField();
    ComboBox<String> appDateFilter      = new ComboBox<String>();

    String preJobType   = null;
    String preJobTitle  = null;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        try {
            if (getCurrentUser().getType().equals("cp")) {
                companyId = jobAdvertisementControl.getCompanyIdFromUser(getCurrentUser());
            } else {
                UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
                event.rerouteTo(Globals.Pages.MAIN_VIEW);
            }
        } catch (Exception exception) {
            Utils.triggerDialogMessage("Fehler", "Es ist ein unerwareter Fehler aufgetreten");
        }

    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<JobAdvertisement> jobs = jobAdvertisementControl.getJobsByCompanyId(companyId);
        grid.setItems(jobs);

        updateGrid();

        List<JobApplicationDTO> applications = jobApplicationControl.loadJobApplicationsFromCompany(getCurrentUser());
        applicationGrid.setItems(applications);
    }

    public JobDashboardView() {
        H1 dashHeader = new H1("Dashboard");
        HorizontalLayout dashHeaderLayout = new HorizontalLayout(dashHeader);
        dashHeaderLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(dashHeaderLayout);

        createJobAdGridDiv();
        createJobAppGridDiv();
    }

    public void createJobAdGridDiv() {
        H2 advertisementHeader = new H2("Stellenangebote");
        add(advertisementHeader);

        Button addAdvertisementBtn = new Button("Stellenangebot hinzufügen");
        addAdvertisementBtn.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.RECRUITMENT_VIEW + companyId);
        });

        // Filter
        jobTitleFilter.setPlaceholder("Jobtitel filtern ...");
        requirementsFilter.setPlaceholder("Voraussetzungen filtern ...");
        jobTypeFilter.setPlaceholder("Jobtypen filtern ...");
        temporaryFilter.setPlaceholder("Kurzfristige Beschäftigung?");
        workingHoursFilter.setPlaceholder("Filtern ...");
        salaryFilter.setPlaceholder("Filtern ...");

        jobTitleFilter.setLabel("Titel:");
        jobTypeFilter.setLabel("Typ:");
        requirementsFilter.setLabel("Voraussetzungen:");
        temporaryFilter.setLabel("Kurzfristige Beschäftigung:");
        entryDateFilter.setLabel("Einstiegsdatum:");
        workingHoursFilter.setLabel("Stunden/Woche:");
        salaryFilter.setLabel("Lohn:");

        // Dropdown for Job Type
        jobTypeFilter.setItems("Praktikum", "Minijob", "Vollzeit", "Teilzeit");
        temporaryFilter.setItems(true, false);
        temporaryFilter.setItemLabelGenerator(bool -> { return bool ? "Ja" : "Nein";});


        // Clicklistener etc
        for(TextField textfield : new TextField[] { jobTitleFilter, requirementsFilter}) {
            textfield.setClearButtonVisible(true);
            textfield.setValueChangeMode(ValueChangeMode.EAGER);
            textfield.addValueChangeListener(e-> updateGrid());
        }
        jobTypeFilter.addValueChangeListener(e -> updateGrid());
        temporaryFilter.addValueChangeListener(e -> updateGrid());

        workingHoursFilter.setHasControls(true);
        workingHoursFilter.setMin(1);
        workingHoursFilter.setMax(60);
        workingHoursFilter.setHelperText("(1 - 60 Stunden)");
        oldWorkingHours = 60;
        workingHoursFilter.setValue(oldWorkingHours);
        workingHoursFilter.addValueChangeListener(e -> {
            if(workingHoursFilter.getValue() == null)
                workingHoursFilter.setValue(oldWorkingHours);
            else
                updateGrid();
        });

        salaryFilter.setHasControls(true);
        salaryFilter.setMin(1);
        oldSalary = 1;
        salaryFilter.setValue(oldSalary);
        salaryFilter.addValueChangeListener(e -> {
            if(salaryFilter.getValue() == null)
                salaryFilter.setValue(oldSalary);
            else
                updateGrid();
        });
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        salaryFilter.setSuffixComponent(euroSuffix);

        oldDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        entryDateFilter.setValue(oldDate);
        entryDateFilter.addValueChangeListener(e -> {
            if(entryDateFilter.getValue() == null)
                entryDateFilter.setValue(oldDate);
            else
                updateGrid();
        });

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // Prepare Grid Header (for filters)
        HeaderRow filterBar = grid.appendHeaderRow();

        // Prepare Content of Grid (jobs)
        grid.addComponentColumn(this::createJobCard).setKey("content");

        // Set Header Content
        filterBar.getCell(grid.getColumnByKey("content")).setComponent(createHeaderCard());

        VerticalLayout addDiv = new VerticalLayout(advertisementHeader, addAdvertisementBtn, grid);
        addDiv.setWidth("90%");
        HorizontalLayout addGridLayout = new HorizontalLayout(addDiv);
        addGridLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Add to page
        add(addGridLayout);
    }

    public void createJobAppGridDiv() {
        appHeadlineFilter.setPlaceholder("Überschrift filtern ...");
        appUserFilter.setPlaceholder("Benutzer filter ...");
        appDateFilter.setPlaceholder("Zeitraum filter ...");

        appHeadlineFilter.setLabel("Überschrift:");
        appUserFilter.setLabel("Benutzer:");
        appDateFilter.setLabel("Zeitraum:");

        appHeadlineFilter.addValueChangeListener(e -> updateApplicationGrid());
        appUserFilter.addValueChangeListener(e -> updateApplicationGrid());
        for(TextField textfield : new TextField[] { appHeadlineFilter, appUserFilter}) {
            textfield.setClearButtonVisible(true);
            textfield.setValueChangeMode(ValueChangeMode.EAGER);
            textfield.addValueChangeListener(e-> updateApplicationGrid());
        }
        appDateFilter.addValueChangeListener(e -> updateApplicationGrid());

        appDateFilter.setItems(DateRanges.all, DateRanges.day, DateRanges.week, DateRanges.month);

        H2 applicationHeader = new H2("Bewerbungen");

        applicationGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        HeaderRow appFilterBar = applicationGrid.appendHeaderRow();

        applicationGrid.addComponentColumn(this::createAppCard).setKey("content");
        appFilterBar.getCell(applicationGrid.getColumnByKey("content")).setComponent(createApplicationHeaderCard());

        VerticalLayout applicationDiv = new VerticalLayout(applicationHeader, applicationGrid);
        applicationDiv.setWidth("90%");
        HorizontalLayout applicationGridLayout = new HorizontalLayout(applicationDiv);
        applicationGridLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(applicationGridLayout);
    }

    public void updateGrid() {

        // Update old values for combo boxes/integer fields
        oldDate = entryDateFilter.getValue();
        oldWorkingHours = workingHoursFilter.getValue();
        oldSalary = salaryFilter.getValue();

        List<JobAdvertisement> filteredJobs;
        if(jobTypeFilter.isEmpty() && temporaryFilter.isEmpty())
            filteredJobs = jobAdvertisementControl.filterJobs(jobTitleFilter.getValue(), requirementsFilter.getValue(),
                    entryDateFilter.getValue(), workingHoursFilter.getValue().shortValue(), salaryFilter.getValue());
        else if(jobTypeFilter.isEmpty())
            filteredJobs = jobAdvertisementControl.filterJobs(jobTitleFilter.getValue(),
                    requirementsFilter.getValue(), temporaryFilter.getValue(),
                    entryDateFilter.getValue(), workingHoursFilter.getValue().shortValue(), salaryFilter.getValue());
        else if(temporaryFilter.isEmpty())
            filteredJobs = jobAdvertisementControl.filterJobs(jobTitleFilter.getValue(),
                    jobTypeFilter.getValue(), requirementsFilter.getValue(),
                    entryDateFilter.getValue(), workingHoursFilter.getValue().shortValue(), salaryFilter.getValue());
        else
            filteredJobs = jobAdvertisementControl.filterJobs(jobTitleFilter.getValue(),
                    jobTypeFilter.getValue(), requirementsFilter.getValue(), temporaryFilter.getValue(),
                    entryDateFilter.getValue(), workingHoursFilter.getValue().shortValue(), salaryFilter.getValue());


        /* Filter for companies. Has to be done in an extra step b/c we only have the companyId in
            JobAdvertisements, therefore has to be resolved in JobAdvertisementControl. */
        filteredJobs = jobAdvertisementControl.filterCompaniesByCompnayId(filteredJobs, companyId);

        grid.setItems(filteredJobs);
    }

    public void updateApplicationGrid() {
        List<JobApplicationDTO> applications = jobApplicationControl.loadJobApplicationsFromCompany(getCurrentUser());

        String headlineString = appHeadlineFilter.getValue();
        String userString = appUserFilter.getValue();
        String dateString = appDateFilter.getValue();

        if (!Utils.stringIsEmptyOrNull(headlineString)) {
            applications = jobApplicationControl.filterApplicationsByHeadline(applications, headlineString);
        } else if (!Utils.stringIsEmptyOrNull(userString)) {
            applications = jobApplicationControl.filterApplicationsByUsername(applications, userString);
        } else if (!Utils.stringIsEmptyOrNull(dateString)) {
            applications = jobApplicationControl.filterApplicationsByDateRange(applications, dateString);
        }

        applicationGrid.setItems(applications);
    }

    private HorizontalLayout createAppCard(JobApplicationDTO jobApplication) {
        HorizontalLayout card = new HorizontalLayout();
        card.setSpacing(false);

        Span appHeadline           = new Span(jobApplication.getHeadline());
        appHeadline.getElement().getStyle().set("font-weight", "bold");

        Span appApplicant           = new Span(jobApplication.getStudentUser().getFirstName() + " " +
                jobApplication.getStudentUser().getLastName());
        Span appDate           = new Span(Utils.convertToGermanDateFormat(jobApplication.getDate()));

        // Buttons for engagement
        Button details = new Button("Details");

        // Button functionality
        details.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBAPPLICATION_VIEW +
                jobApplication.getId()));

        HorizontalLayout buttons = new HorizontalLayout(details);
        buttons.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout cardItem= new VerticalLayout(appHeadline, appApplicant, appDate);
        cardItem.setSpacing(false);

        HorizontalLayout cardLayout = new HorizontalLayout(cardItem, buttons);
        cardLayout.setWidthFull();

        card.add(cardLayout);
        return card;
    }

    private HorizontalLayout createJobCard(JobAdvertisement jobAdvertisement) {
        HorizontalLayout card = new HorizontalLayout();
        card.setSpacing(false);

        // Company variable for later use
        int companyId = jobAdvertisementControl.getCompanyId(jobAdvertisement);

        // Header
        Span jobTitle           = new Span(jobAdvertisement.getJobTitle());
        Span typeOfEmployment   = new Span(jobAdvertisement.getTypeOfEmployment());
        Span companyName        = new Span(jobAdvertisementControl.getCompanyName(jobAdvertisement));

        // Header styling
        for (Span span : new Span[]{ jobTitle, typeOfEmployment, companyName }) {
            span.getElement().getStyle().set("font-weight", "bold");
        }

        // Mini-description
        Span jobDescription     = new Span(jobAdvertisement.getJobDescription());
        Span startOfWork        = new Span(jobAdvertisement.getStartOfWork().toString());
        Span workingHours       = new Span(Short.toString(jobAdvertisement.getWorkingHours()));
        Span salary             = new Span(jobAdvertisement.getSalary() + " €");
        Span requirements       = new Span(jobAdvertisement.getRequirements());

        // Buttons for engagement
        Button details = new Button("Details");
        // Button functionality
        details.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                jobAdvertisement.getId()));

        // Append
        HorizontalLayout header = new HorizontalLayout(jobTitle, new Span("-"),
                typeOfEmployment, new Span("-"), companyName);

        HorizontalLayout dateAndHours = new HorizontalLayout(new Span("Ab:"), startOfWork,
                new Span("Stunden/Woche:"), workingHours);

        HorizontalLayout salaryInfo         = new HorizontalLayout(new Span("Vergütung:"), salary);
        HorizontalLayout requirementsInfo   = new HorizontalLayout(new Span("Voraussetzungen:"), requirements);

        VerticalLayout cardItem= new VerticalLayout(header, jobDescription, dateAndHours, salaryInfo,
                requirementsInfo);
        cardItem.setSpacing(false);

        HorizontalLayout buttons            = new HorizontalLayout(details);

        if (authorizationControl.isUserCompanyContactPerson(UtilCurrent.getCurrentUser(), companyId)) {
            Button delete = new Button("Löschen");
            delete.addClickListener(e -> {
                // Preventing missclicks by opening a dialog box
                Dialog dialog   = new Dialog();
                Label question  = new Label("Sind sie sicher, dass Sie dieses Stellenangebot löschen möchten?");
                Label info      = new Label("(Dieser Vorgang ist unwiderruflich.)");
                Button yesButton = new Button("Ja");
                Button noButton  = new Button ("Nein");

                yesButton.addClickListener(jo -> {
                    dialog.close();
                    try {
                        this.jobAdvertisementControl.deleteAdvertisement(jobAdvertisement);
                        Notification notification = Notification.show("Stellenangebot gelöscht", 5000,
                                Notification.Position.TOP_END);
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        updateGrid();
                    } catch (DatabaseUserException ex) {
                        ex.printStackTrace();
                    }
                });
                noButton.addClickListener(no -> dialog.close());

                HorizontalLayout dialogButtons = new HorizontalLayout(yesButton, noButton);
                VerticalLayout dialogContent = new VerticalLayout(question, info, dialogButtons);
                dialogContent.setAlignItems(FlexComponent.Alignment.CENTER);
                dialog.add(dialogContent);
                dialog.open();
            });
            buttons.add(delete);
        }

        buttons.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout cardLayout = new HorizontalLayout(cardItem, buttons);
        cardLayout.setWidthFull();

        card.add(cardLayout);
        return card;
    }

    private HorizontalLayout createHeaderCard() {
        HorizontalLayout card = new HorizontalLayout();
        card.setAlignItems(FlexComponent.Alignment.BASELINE);

        Button filterDelete = new Button("Alle Filter löschen");
        filterDelete.addClickListener(e-> {
            jobTitleFilter.setValue("");
            jobTitleFilter.setPlaceholder("Jobtitel filtern ...");
            jobTypeFilter.setValue("");
            jobTypeFilter.setPlaceholder("Jobtypen filtern ...");
            requirementsFilter.setValue("");
            requirementsFilter.setPlaceholder("Voraussetzungen filtern ...");
            temporaryFilter.setValue(null);
            temporaryFilter.setPlaceholder("Kurzfristige Beschäftigung?");
            workingHoursFilter.setValue(60);
            workingHoursFilter.setPlaceholder("Filtern ...");
            oldWorkingHours = 60;
            salaryFilter.setValue(1);
            salaryFilter.setPlaceholder("Filtern ...");
            oldSalary = 1;
            LocalDate newDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            oldDate = newDate;
            entryDateFilter.setValue(newDate);
        });

        card.add(jobTitleFilter, jobTypeFilter, requirementsFilter,
                temporaryFilter, workingHoursFilter, salaryFilter, entryDateFilter, filterDelete);
        return card;
    }

    private HorizontalLayout createApplicationHeaderCard() {
        HorizontalLayout card = new HorizontalLayout();
        card.setAlignItems(FlexComponent.Alignment.BASELINE);

        Button filterDelete = new Button("Alle Filter löschen");
        filterDelete.addClickListener(e-> {
            appHeadlineFilter.setValue("");
            appHeadlineFilter.setPlaceholder("Überschrift filtern ...");
            appUserFilter.setValue("");
            appUserFilter.setPlaceholder("Benutzer filtern ...");
            appDateFilter.setValue(DateRanges.all);
        });

        card.add(appHeadlineFilter, appUserFilter, appDateFilter, filterDelete);
        return card;
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
