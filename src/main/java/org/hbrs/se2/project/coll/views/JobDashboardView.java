package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.AuthorizationControl;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Route(value = "dashboard", layout = AppView.class)
@PageTitle("Job Dashboard")
public class JobDashboardView extends Div implements AfterNavigationObserver, BeforeEnterObserver {

    @Autowired
    private AuthorizationControl authorizationControl;
    @Autowired
    JobAdvertisementControl jobAdvertisementControl;

    Grid<JobAdvertisement> grid = new Grid<>();

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
    }

    public JobDashboardView() {

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

        // Add to page
        add(grid);
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

        HorizontalLayout buttons            = new HorizontalLayout(details);
        buttons.setAlignItems(FlexComponent.Alignment.CENTER);
        HorizontalLayout salaryInfo         = new HorizontalLayout(new Span("Vergütung:"), salary);
        HorizontalLayout requirementsInfo   = new HorizontalLayout(new Span("Voraussetzungen:"), requirements);

        VerticalLayout cardItem= new VerticalLayout(header, jobDescription, dateAndHours, salaryInfo,
                requirementsInfo);
        cardItem.setSpacing(false);
        HorizontalLayout cardLayout            = new HorizontalLayout(cardItem, buttons);

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

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
