package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.AuthorizationControl;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.control.JobApplicationControl;
import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.hbrs.se2.project.coll.views.grids.JobAdvertisementGrid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hbrs.se2.project.coll.util.Globals.DateRanges;

@Route(value = "dashboard", layout = AppView.class)
@PageTitle("Job Dashboard")
public class JobDashboardView extends Div implements AfterNavigationObserver, BeforeEnterObserver {

    @Autowired
    JobAdvertisementControl jobAdvertisementControl;
    @Autowired
    JobApplicationControl jobApplicationControl;
    @Autowired
    AuthorizationControl authorizationControl;

    JobAdvertisementGrid jobAdvertisementGrid;

    Grid<JobApplicationDTO> applicationGrid = new Grid<>();
    HorizontalLayout applicationGridLayout;

    Tab jobTab = new Tab("Stellenangebote");
    Tab appTab = new Tab("Bewerbungen");
    Tabs tabs       = new Tabs(jobTab, appTab);

    private int companyId = 0;

    //Applications
    TextField appHeadlineFilter         = new TextField();
    TextField appUserFilter             = new TextField();
    ComboBox<String> appDateFilter      = new ComboBox<>();

    private String content = "content";

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
        init();
        jobAdvertisementGrid.loadGridData();

        List<JobApplicationDTO> applications = jobApplicationControl.loadJobApplicationsFromCompany(getCurrentUser());
        applicationGrid.setItems(applications);
    }

    public JobDashboardView() {
        //Required for Vaadin
    }

    public void init() {
        H1 dashHeader = new H1("Dashboard");
        HorizontalLayout dashHeaderLayout = new HorizontalLayout(dashHeader);
        dashHeaderLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(dashHeaderLayout);

        createJobAppGridDiv();

        HorizontalLayout tabContent = new HorizontalLayout();
        tabContent.setWidthFull();

        tabs.setSizeFull();
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);

        jobTab.getElement().addEventListener("click", e -> {
            tabContent.removeAll();
            tabContent.addComponentAtIndex(0, jobAdvertisementGrid);
        });

        appTab.getElement().addEventListener("click", e -> {
            tabContent.removeAll();
            tabContent.addComponentAtIndex(0, applicationGridLayout);
        });

        add(tabs);

        jobAdvertisementGrid = new JobAdvertisementGrid(jobAdvertisementControl, jobApplicationControl,
                authorizationControl, companyId, true);
        jobAdvertisementGrid.setGridItemButtons(true, false, false, false);
        jobAdvertisementGrid.setHeight("800px");
        jobAdvertisementGrid.setWidthFull();
        tabContent.add(jobAdvertisementGrid);
        add(tabContent);

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

        appDateFilter.setItems(DateRanges.ALL, DateRanges.DAY, DateRanges.WEEK, DateRanges.MONTH);

        H2 applicationHeader = new H2("Bewerbungen");

        applicationGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        HeaderRow appFilterBar = applicationGrid.appendHeaderRow();

        applicationGrid.addComponentColumn(this::createAppCard).setKey(content);
        appFilterBar.getCell(applicationGrid.getColumnByKey(content)).setComponent(createApplicationHeaderCard());
        applicationGrid.setHeight("800px");

        VerticalLayout applicationDiv = new VerticalLayout(applicationHeader, applicationGrid);
        applicationDiv.setWidth("90%");
        applicationGridLayout = new HorizontalLayout(applicationDiv);
        applicationGridLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        applicationGridLayout.setWidthFull();
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

    private HorizontalLayout createApplicationHeaderCard() {
        HorizontalLayout card = new HorizontalLayout();
        card.setAlignItems(FlexComponent.Alignment.BASELINE);

        Button filterDelete = new Button("Alle Filter löschen");
        filterDelete.addClickListener(e-> {
            appHeadlineFilter.setValue("");
            appHeadlineFilter.setPlaceholder("Überschrift filtern ...");
            appUserFilter.setValue("");
            appUserFilter.setPlaceholder("Benutzer filtern ...");
            appDateFilter.setValue(DateRanges.ALL);
        });

        card.add(appHeadlineFilter, appUserFilter, appDateFilter, filterDelete);
        return card;
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
