package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route(value = "joblist", layout = AppView.class)
@PageTitle("Liste der Stellenangebote")
public class JobListView extends Div implements AfterNavigationObserver {

    @Autowired
    JobAdvertisementControl jobAdvertisementControl;

    Grid<JobAdvertisement> grid = new Grid<>();

    TextField jobTitleFilter        = new TextField();
    //TextField jobTypeFilter         = new TextField();
    ComboBox<String> jobTypeFilter  = new ComboBox<>();
    TextField requirementsFilter    = new TextField();

    public JobListView() {

        // Filter
        jobTitleFilter.setPlaceholder("Nach Jobtitel filtern ...");
        requirementsFilter.setPlaceholder("Nach Voraussetzungen filtern ...");

        jobTypeFilter.setItems("Praktikum", "Minijob", "Vollzeit", "Teilzeit");

        // Dropdown for Job Type
        //jobTypeFilter.setItems("Praktikum", "Minijob", "Teilzeit", "Vollzeit");
        //jobTitleFilter.addValueChangeListener(e-> updateGrid());

        // Clicklistener etc
        for(TextField textfield : new TextField[] { jobTitleFilter, requirementsFilter}) {
            textfield.setClearButtonVisible(true);
            textfield.setValueChangeMode(ValueChangeMode.EAGER);
            textfield.addValueChangeListener(e-> updateGrid());
        }
        jobTypeFilter.addValueChangeListener(e -> updateGrid());

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

    /* We have to differentiate between an empty job type filter or not, as Vaadin Combo Boxes don't work well with
        null values.
    */
    public void updateGrid() {
        if(jobTypeFilter.isEmpty())
            grid.setItems(jobAdvertisementControl.filterJobs(jobTitleFilter.getValue(), requirementsFilter.getValue()));
        else
            grid.setItems(jobAdvertisementControl.filterJobs(jobTitleFilter.getValue(),
                    jobTypeFilter.getValue(), requirementsFilter.getValue()));
    }

    // TODO: Weitere filter-felder schreiben
    // Header (filter)
    private HorizontalLayout createHeaderCard() {
        HorizontalLayout card = new HorizontalLayout();
        card.setAlignItems(FlexComponent.Alignment.BASELINE);

        jobTitleFilter.setLabel("Titel:");
        jobTypeFilter.setLabel("Typ:");
        requirementsFilter.setLabel("Voraussetzungen:");

        Button filterDelete = new Button("Alle Filter löschen");
        filterDelete.addClickListener(e-> {
            jobTitleFilter.setValue("");
            jobTitleFilter.setPlaceholder("Nach Jobtitel filtern ...");
            jobTypeFilter.setValue("");
            jobTypeFilter.setPlaceholder("Nach Jobtyp filtern ...");
            requirementsFilter.setValue("");
            requirementsFilter.setPlaceholder("Nach Voraussetzungen filtern ...");
        });

        card.add(jobTitleFilter, jobTypeFilter, requirementsFilter, filterDelete);
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
        Span companyName        = new Span( jobAdvertisementControl.getCompanyName(jobAdvertisement));

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
        Button message = new Button("Frage stellen");
        Button apply = new Button("Jetzt bewerben!");

        // Button functionality
        details.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                jobAdvertisement.getId()));
        message.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.CONTACTING_VIEW +
                companyId + "/" + jobAdvertisement.getId(), "_blank"));
        apply.addClickListener(e -> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                jobAdvertisement.getId() + "/" + Globals.Pages.APPLICATION_VIEW));


        // Append
        HorizontalLayout header = new HorizontalLayout(jobTitle, new Span("-"),
                typeOfEmployment, new Span("-"), companyName);

        HorizontalLayout dateAndHours = new HorizontalLayout(new Span("Ab:"), startOfWork,
                new Span("Stunden/Woche:"), workingHours);

        HorizontalLayout buttons            = new HorizontalLayout(details, message, apply);
        HorizontalLayout salaryInfo         = new HorizontalLayout(new Span("Vergütung:"), salary);
        HorizontalLayout requirementsInfo   = new HorizontalLayout(new Span("Voraussetzungen:"), requirements,
                                                buttons);

        VerticalLayout cardLayout = new VerticalLayout(header, jobDescription, dateAndHours, salaryInfo,
                requirementsInfo, buttons);
        cardLayout.setSpacing(false);

        card.add(cardLayout);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<JobAdvertisement> jobs = jobAdvertisementControl.getAllJobs();
        grid.setItems(jobs);
    }

}
