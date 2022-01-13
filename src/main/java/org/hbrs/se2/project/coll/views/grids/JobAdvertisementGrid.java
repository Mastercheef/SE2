package org.hbrs.se2.project.coll.views.grids;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.hbrs.se2.project.coll.control.AuthorizationControl;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.control.JobApplicationControl;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.hbrs.se2.project.coll.util.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class JobAdvertisementGrid extends Div {

    JobAdvertisementControl jobAdvertisementControl;
    JobApplicationControl jobApplicationControl;
    AuthorizationControl authorizationControl;

    Grid<JobAdvertisement> grid = new Grid<>();

    private int companyId = 0;

    // ItemCard Button Options
    private boolean details = true;
    private boolean contact = false;
    private boolean profile = false;
    private boolean apply = false;
    private boolean filterOption = true;
    HeaderRow filterBar;

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
    
    String filter = "Filtern ...";
    String content = "content";

    public void loadGridData() {
        List<JobAdvertisement> jobs = jobAdvertisementControl.getJobsByCompanyId(companyId);
        grid.setItems(jobs);

        updateGrid();
    }

    public void loadCompanyId() {
        companyId = jobAdvertisementControl.getCompanyIdFromUser(UtilCurrent.getCurrentUser());
    }

    public void setPreFilterValues(String preJobType, String preJobTitle) {
        if (!Utils.stringIsEmptyOrNull(preJobType))
            this.jobTypeFilter.setValue(preJobType);
        if (!Utils.stringIsEmptyOrNull(preJobTitle))
            this.jobTitleFilter.setValue(preJobTitle);
    }

    public void setResizeListener() {
        Page page = UI.getCurrent().getPage();
        page.addBrowserWindowResizeListener(event ->
                filterBar.getCell(grid.getColumnByKey(content))
                        .setComponent(createHeaderCard(event.getWidth()))
        );
    }

    public void setGridItemButtons(boolean details, boolean contact, boolean profile, boolean apply) {
        this.details = details;
        this.contact = contact;
        this.profile = profile;
        this.apply = apply;
    }

    public JobAdvertisementGrid(JobAdvertisementControl jobAdvertisementControl,
                                JobApplicationControl jobApplicationControl,
                                AuthorizationControl authorizationControl, int companyId, boolean filterOption) {

        // Setting Controller (Can't be autowired because of how vaadin works)
        this.jobAdvertisementControl = jobAdvertisementControl;
        this.jobApplicationControl = jobApplicationControl;
        this.authorizationControl = authorizationControl;
        // Setting Options for Grid
        this.companyId = companyId;
        this.filterOption = filterOption;
        this.setHeightFull();
        setResizeListener();

        H2 advertisementHeader = new H2("Stellenangebote");
        add(advertisementHeader);

        // Filter
        companyFilter.setPlaceholder("Firmen filtern ...");
        jobTitleFilter.setPlaceholder("Jobtitel filtern ...");
        requirementsFilter.setPlaceholder("Voraussetzungen filtern ...");
        jobTypeFilter.setPlaceholder("Jobtypen filtern ...");
        temporaryFilter.setPlaceholder("Kurzfristige Beschäftigung?");
        workingHoursFilter.setPlaceholder(filter);
        salaryFilter.setPlaceholder(filter);

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
        entryDateFilter.setWidth("128px");
        entryDateFilter.setValue(oldDate);
        entryDateFilter.addValueChangeListener(e -> {
            if(entryDateFilter.getValue() == null)
                entryDateFilter.setValue(oldDate);
            else
                updateGrid();
        });

        VerticalLayout addDiv = new VerticalLayout();
        addDiv.setHeightFull();
        addDiv.setWidth("90%");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();

        // Prepare Grid Header (for filters)
        filterBar = grid.appendHeaderRow();

        // Prepare Content of Grid (jobs)
        grid.addComponentColumn(this::createJobCard).setKey(content);

        // Set Header Content
        if (this.filterOption)
            filterBar.getCell(grid.getColumnByKey(content)).setComponent(createHeaderCard(2000));

        addDiv.add(advertisementHeader, grid);

        if (UtilCurrent.getCurrentUser() != null &&
                authorizationControl.isUserCompanyContactPerson(UtilCurrent.getCurrentUser(), companyId)) {
            Button addAdvertisementBtn = new Button("Stellenangebot hinzufügen");
            addAdvertisementBtn.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.RECRUITMENT_VIEW + companyId));
            addDiv.addComponentAtIndex(1, addAdvertisementBtn);
        }

        HorizontalLayout addGridLayout = new HorizontalLayout(addDiv);
        addGridLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        addGridLayout.setHeightFull();

        // Add to page
        add(addGridLayout);
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
        if (companyId > 0)
            filteredJobs = jobAdvertisementControl.filterCompaniesByCompanyId(filteredJobs, companyId);
        else
            if(!companyFilter.isEmpty())
                filteredJobs = jobAdvertisementControl.filterCompanies(filteredJobs, companyFilter.getValue());

        grid.setItems(filteredJobs);
    }

    private HorizontalLayout createJobCard(JobAdvertisement jobAdvertisement) {
        HorizontalLayout card = new HorizontalLayout();
        card.setSpacing(false);

        // Header
        Span jobTitle           = new Span(jobAdvertisement.getJobTitle());
        Span typeOfEmployment   = new Span(jobAdvertisement.getTypeOfEmployment());
        Span companyName        = new Span(jobAdvertisementControl.getCompanyName(jobAdvertisement));

        // Header styling
        for (Span span : new Span[]{ jobTitle, typeOfEmployment, companyName }) {
            span.getElement().getStyle().set("font-weight", "bold");
        }

        String jobDescriptionString = "";
        if (jobAdvertisement.getJobDescription().length() > 147) {
            jobDescriptionString = jobAdvertisement.getJobDescription().substring(0, 147) + "...";
        }
        // Mini-description
        Span jobDescription     = new Span(jobDescriptionString);
        Span startOfWork        = new Span(Utils.convertToGermanDateFormat(jobAdvertisement.getStartOfWork()));
        Span workingHours       = new Span(Short.toString(jobAdvertisement.getWorkingHours()));
        Span salary             = new Span(jobAdvertisement.getSalary() + " €");
        Span requirements       = new Span(jobAdvertisement.getRequirements());

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
        cardItem.getElement().getStyle().set("overflow", "hidden");
        cardItem.setAlignSelf(FlexComponent.Alignment.STRETCH);

        HorizontalLayout buttons = this.createJobCardButtons(jobAdvertisement);
        buttons.setAlignItems(FlexComponent.Alignment.CENTER);
        buttons.setWidth("auto");

        HorizontalLayout cardLayout = new HorizontalLayout(cardItem, buttons);
        cardLayout.setWidthFull();

        card.add(cardLayout);
        card.setHeight("auto");
        return card;
    }

    private HorizontalLayout createJobCardButtons(JobAdvertisement jobAdvertisement) {
        int compId = jobAdvertisementControl.getCompanyId(jobAdvertisement);
        HorizontalLayout buttons = new HorizontalLayout();
        Button messageBtn  = new Button("Frage stellen");
        Button profileBtn  = new Button("Profil besuchen");
        Button applyBtn    = new Button("Jetzt bewerben!");
        if (this.details) {
            Button detailsBtn  = new Button("Details");
            detailsBtn.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                    jobAdvertisement.getId()));
            buttons.add(detailsBtn);
        }
        if (this.contact) {
            messageBtn.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.CONTACTING_VIEW +
                    jobAdvertisement.getContactPerson().getId() + "/" + compId + "/" + jobAdvertisement.getId(), "_blank"));
        }
        if (this.apply) {
            applyBtn.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                    jobAdvertisement.getId() + "/" + Globals.Pages.APPLICATION_VIEW));
        }

        if(UtilCurrent.getCurrentUser() != null ) {
            if(this.profile) {
                profileBtn.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.COMPANYPROFILE_VIEW
                        + compId));
                buttons.add(profileBtn);
            }
            if (Objects.equals(UtilCurrent.getCurrentUser().getType(), "st")) {
                buttons.add(messageBtn);
                buttons.add(applyBtn);
            }
            if (this.authorizationControl.isUserCompanyContactPerson(UtilCurrent.getCurrentUser(), compId)) {
                Button delete = new Button("Löschen");
                delete.addClickListener(e -> {
                    // Preventing missclicks by opening a dialog box
                    Runnable yesRunnable = () -> {
                        try {
                            this.jobAdvertisementControl.deleteAdvertisement(jobAdvertisement);
                            Notification notification = Notification.show("Stellenangebot gelöscht", 5000,
                                    Notification.Position.TOP_END);
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            updateGrid();
                        } catch (DatabaseUserException ex) {
                            ex.printStackTrace();
                        }
                    };

                    String questionString = "Sind sie sicher, dass Sie dieses Stellenangebot löschen möchten?";
                    Dialog dialog   = Utils.getConfirmationDialog(questionString, yesRunnable);
                    dialog.open();
                });
                buttons.add(delete);
            }
        }

        return buttons;
    }

    private HorizontalLayout createHeaderCard(int pageWidth) {
        HorizontalLayout card = new HorizontalLayout();
        card.setAlignItems(FlexComponent.Alignment.BASELINE);

        Button filterDelete = new Button("Alle Filter löschen");
        filterDelete.addClickListener(e-> {
            companyFilter.setValue("");
            jobTitleFilter.setValue("");
            jobTitleFilter.setPlaceholder("Jobtitel filtern ...");
            jobTypeFilter.setValue("");
            jobTypeFilter.setPlaceholder("Jobtypen filtern ...");
            requirementsFilter.setValue("");
            requirementsFilter.setPlaceholder("Voraussetzungen filtern ...");
            temporaryFilter.setValue(null);
            temporaryFilter.setPlaceholder("Kurzfristige Beschäftigung?");
            workingHoursFilter.setValue(60);
            workingHoursFilter.setPlaceholder(filter);
            oldWorkingHours = 60;
            salaryFilter.setValue(1);
            salaryFilter.setPlaceholder(filter);
            oldSalary = 1;
            LocalDate newDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            oldDate = newDate;
            entryDateFilter.setValue(newDate);
        });

        if (companyId != 0 && pageWidth < 1600 || companyId == 0 && pageWidth < 1800) {
            HorizontalLayout firstFilterRow = new HorizontalLayout(jobTitleFilter, jobTypeFilter, requirementsFilter, temporaryFilter);
            HorizontalLayout secondFilterRow = new HorizontalLayout(workingHoursFilter, salaryFilter, entryDateFilter, filterDelete);
            if (companyId == 0)
                secondFilterRow.addComponentAtIndex(0, companyFilter);
            secondFilterRow.setAlignItems(FlexComponent.Alignment.BASELINE);
            VerticalLayout splitFilter = new VerticalLayout(firstFilterRow, secondFilterRow);
            card.add(splitFilter);
        } else {
            if (companyId == 0)
                card.add(companyFilter);
            card.add(jobTitleFilter, jobTypeFilter, requirementsFilter,
                    temporaryFilter, workingHoursFilter, salaryFilter, entryDateFilter, filterDelete);
        }

        return card;
    }

}
