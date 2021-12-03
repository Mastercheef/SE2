package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;

@Route(value = "impressum", layout = LayoutAlternative.class)
@PageTitle("Impressum")
public class ImpressumView extends HorizontalLayout {

    public ImpressumView() {

    }
}
