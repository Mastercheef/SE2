package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;


@Route(value = "datenschutz", layout = AppView.class)
@PageTitle("Datenschutz")
public class DataProtectionView extends HorizontalLayout {

    public DataProtectionView() {
            //Required for Vaadin
    }
}
