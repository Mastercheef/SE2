package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "joblist", layout = AppView.class)
@PageTitle("Liste der Stellenangebote")
public class JobListView extends Div implements AfterNavigationObserver {

    @Autowired
    JobAdvertisementControl jobAdvertisementControl;

    // TODO: put this in control later for filtering
    @Autowired
    JobAdvertisementRepository jobAdvertisementRepository;

    Grid<JobAdvertisement> grid = new Grid<>();

    TextField jobTypeFilter = new TextField();

    public JobListView() {

        // Filter
        jobTypeFilter.setPlaceholder("Nach Jobtyp filtern ...");
        jobTypeFilter.setClearButtonVisible(true);
        jobTypeFilter.setValueChangeMode(ValueChangeMode.EAGER);
        jobTypeFilter.addValueChangeListener(e-> updateGrid());

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

    // TODO: in control auslagern
    public void updateGrid() {
        grid.setItems(jobAdvertisementRepository.findJobAdvertisementsByTypeOfEmploymentIsContaining(jobTypeFilter.getValue()));
    }

    // TODO: Weitere filter-felder schreiben
    // Header (filter)
    private HorizontalLayout createHeaderCard() {
        HorizontalLayout card = new HorizontalLayout();
        card.setAlignItems(FlexComponent.Alignment.BASELINE);

        jobTypeFilter.setLabel("Typ:");

        Button filterDelete = new Button("Alle Filter löschen");
        filterDelete.addClickListener(e-> {
           jobTypeFilter.setValue("");
           jobTypeFilter.setPlaceholder("Nach Jobtyp filtern ...");
        });

        card.add(jobTypeFilter, filterDelete);
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
        // TODO: Link zum tatsächlichen Bewerbungsformular
        //apply


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
