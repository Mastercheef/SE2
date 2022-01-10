package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.AuthorizationControl;
import org.hbrs.se2.project.coll.control.JobAdvertisementControl;
import org.hbrs.se2.project.coll.control.JobApplicationControl;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.views.grids.JobAdvertisementGrid;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "joblist/:jobTitle?/:jobType?", layout = AppView.class)
@PageTitle(Globals.PageTitles.MAIN_PAGE_TITLE)
public class JobListView extends Div implements AfterNavigationObserver, BeforeEnterObserver {

    @Autowired
    private JobAdvertisementControl jobAdvertisementControl;
    @Autowired
    private JobApplicationControl jobApplicationControl;
    @Autowired
    private AuthorizationControl authorizationControl;

    JobAdvertisementGrid jobAdvertisementGrid;

    String preJobType   = null;
    String preJobTitle  = null;

    // Set prefilters. Is only used when search fields on MainView are used.
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().get("jobTitle").ifPresent(value -> preJobTitle = value);
        event.getRouteParameters().get("jobType").ifPresent(value -> preJobType = value);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        init();
        jobAdvertisementGrid.setPreFilterValues(preJobType, preJobTitle);
        jobAdvertisementGrid.loadGridData();
    }

    public JobListView() {
        //Required for Vaadin
    }

    public void init() {
        jobAdvertisementGrid = new JobAdvertisementGrid(jobAdvertisementControl, jobApplicationControl,
                authorizationControl, 0, true,
                true, true, true, true);
        add(jobAdvertisementGrid);
        this.setHeightFull();
    }
}
