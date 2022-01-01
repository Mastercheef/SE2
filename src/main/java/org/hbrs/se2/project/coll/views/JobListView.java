package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Route(value = "joblist/:jobTitle?/:jobType?", layout = AppView.class)
@PageTitle(Globals.PageTitles.MAIN_PAGE_TITLE)
public class JobListView extends Div implements AfterNavigationObserver, BeforeEnterObserver {

    @Autowired
    JobAdvertisementControl jobAdvertisementControl;

    Grid<JobAdvertisement> grid = new Grid<>();

    TextField companyFilter             = new TextField();
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


    String preJobType   = null;
    String preJobTitle  = null;

    // Set prefilters. Is only used when search fields on MainView are used.
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().get("jobTitle").ifPresent(value -> preJobTitle = value);
        event.getRouteParameters().get("jobType").ifPresent(value -> preJobType = value);
    }

    public JobListView() {

        // Filter
        setAllPlaceholders();

        companyFilter.setLabel("Firma:");
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
        temporaryFilter.setItemLabelGenerator(bool -> bool ? "Ja" : "Nein");


        // Clicklistener etc
        for(TextField textfield : new TextField[] { companyFilter, jobTitleFilter, requirementsFilter}) {
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

        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // Prepare Grid Header (for filters)
        HeaderRow filterBar = grid.appendHeaderRow();

        // Prepare Content of Grid (jobs)
        grid.addComponentColumn(this::createJobCard).setKey("content");

        // Set Header Content
        filterBar.getCell(grid.getColumnByKey("content")).setComponent(createHeaderCard());

        // Add to page
        add(grid);
    }

    /* We have to differentiate between an empty ComboBoxes or not, as Vaadin Combo Boxes don't work well with
        null values.
    */
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
        if(!companyFilter.isEmpty())
            filteredJobs = jobAdvertisementControl.filterCompanies(filteredJobs, companyFilter.getValue());

        grid.setItems(filteredJobs);
    }

    // Header (filter)
    private HorizontalLayout createHeaderCard() {
        HorizontalLayout card = new HorizontalLayout();
        card.setAlignItems(FlexComponent.Alignment.BASELINE);

        Button filterDelete = new Button("Alle Filter löschen");
        filterDelete.addClickListener(e-> {
            companyFilter.setValue("");
            jobTitleFilter.setValue("");
            jobTypeFilter.setValue("");
            requirementsFilter.setValue("");
            temporaryFilter.setValue(null);
            workingHoursFilter.setValue(60);
            oldWorkingHours = 60;
            salaryFilter.setValue(1);
            oldSalary = 1;
            LocalDate newDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            oldDate = newDate;
            setAllPlaceholders();
            entryDateFilter.setValue(newDate);
        });

        card.add(companyFilter, jobTitleFilter, jobTypeFilter, requirementsFilter,
                temporaryFilter, workingHoursFilter, salaryFilter, entryDateFilter, filterDelete);
        return card;
    }

    // Initialize cards (Styling, etc)
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
        Button details  = new Button("Details");
        Button message  = new Button("Frage stellen");
        Button profile  = new Button("Profil besuchen");
        Button apply    = new Button("Jetzt bewerben!");

        // Button functionality
        details.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                jobAdvertisement.getId()));
        message.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.CONTACTING_VIEW +
                companyId + "/" + jobAdvertisement.getId(), "_blank"));
        profile.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.COMPANYPROFILE_VIEW
                + companyId));
        apply.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                jobAdvertisement.getId() + "/" + Globals.Pages.APPLICATION_VIEW));


        // Append
        HorizontalLayout header = new HorizontalLayout(jobTitle, new Span("-"),
                typeOfEmployment, new Span("-"), companyName);

        HorizontalLayout dateAndHours = new HorizontalLayout(new Span("Ab:"), startOfWork,
                new Span("Stunden/Woche:"), workingHours);

        HorizontalLayout buttons            = new HorizontalLayout(details, message, profile, apply);
        HorizontalLayout salaryInfo         = new HorizontalLayout(new Span("Vergütung:"), salary);
        HorizontalLayout requirementsInfo   = new HorizontalLayout(new Span("Voraussetzungen:"), requirements,
                                                buttons);

        VerticalLayout cardLayout = new VerticalLayout(header, jobDescription, dateAndHours, salaryInfo,
                requirementsInfo, buttons);
        cardLayout.setSpacing(false);

        card.add(cardLayout);
        return card;
    }

    private void setAllPlaceholders() {
        companyFilter.setPlaceholder("Firmen filtern ...");
        jobTitleFilter.setPlaceholder("Jobtitel filtern ...");
        requirementsFilter.setPlaceholder("Voraussetzungen filtern ...");
        jobTypeFilter.setPlaceholder("Jobtypen filtern ...");
        temporaryFilter.setPlaceholder("Kurzfristige Beschäftigung?");
        workingHoursFilter.setPlaceholder("Filtern ...");
        salaryFilter.setPlaceholder("Filtern ...");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

        List<JobAdvertisement> jobs = jobAdvertisementControl.getAllJobs();
        grid.setItems(jobs);

        // Handle prefilters if they are set.
        if(preJobTitle != null || preJobType != null)
        {
            if(preJobTitle != null)
                jobTitleFilter.setValue(preJobTitle);
            if(preJobType != null)
                jobTypeFilter.setValue(preJobType);
            updateGrid();
        }

        // Grid Items / Job Advertisements always get sorted in descending order by their StartOfWork-Attribute.
    }

}
