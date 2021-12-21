package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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

@Route(value = "joblist", layout = AppView.class)
@PageTitle("Liste der Stellenangebote")
public class JobListView extends Div implements AfterNavigationObserver {

    @Autowired
    JobAdvertisementControl jobAdvertisementControl;

    Grid<JobAdvertisement> grid = new Grid<>();

    public JobListView() {

        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addComponentColumn(this::createJobCard);
        add(grid);
    }

    private void createFilter() {
        TextField jobType = new TextField();
        jobType.setLabel("Typ:");

        Button filterButton = new Button("Filtern");
        Button filterDelete = new Button("Alle Filter löschen");

        add(new HorizontalLayout(jobType, filterButton, filterDelete));
    }

    // Initialize cards (Styling, etc)
    private HorizontalLayout createJobCard(JobAdvertisement jobAdvertisement) {
        HorizontalLayout card = new HorizontalLayout();
        card.setSpacing(false);

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

        // Append
        HorizontalLayout header = new HorizontalLayout(jobTitle, new Span("-"),
                typeOfEmployment, new Span("-"), companyName);

        HorizontalLayout dateAndHours = new HorizontalLayout(new Span("Ab:"), startOfWork,
                new Span("Stunden/Woche:"), workingHours);

        HorizontalLayout salaryInfo = new HorizontalLayout(new Span("Vergütung:"), salary);
        HorizontalLayout requirementsInfo = new HorizontalLayout(new Span("Voraussetzungen:"), requirements);

        VerticalLayout cardLayout = new VerticalLayout(header, jobDescription, dateAndHours, salaryInfo,
                requirementsInfo);
        cardLayout.setSpacing(false);

        card.add(cardLayout);

        // Open Advertisement in new tab onclick
        card.addClickListener(e-> UI.getCurrent().getPage().open(Globals.Pages.JOBADVERTISEMENT_VIEW +
                        jobAdvertisement.getId(),
                "_blank"));
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<JobAdvertisement> jobs = jobAdvertisementControl.getAllJobs();
        grid.setItems(jobs);
    }

    // TODO: Filter einbauen.
}
