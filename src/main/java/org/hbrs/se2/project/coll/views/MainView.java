package org.hbrs.se2.project.coll.views;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.util.Globals;

import java.util.Objects;

@Route(value = "" , layout = AppView.class)
@RouteAlias(value = "main" , layout = AppView.class)
@PWA(name = "Coll@HBRS", shortName = "Coll")
@PageTitle(Globals.PageTitles.MAIN_PAGE_TITLE)
public class MainView extends VerticalLayout {

    @Id("main")
    VerticalLayout main;
    HorizontalLayout temporaryLinks = new HorizontalLayout();

    public MainView()  {
        setSizeFull();

        main = createBody();
        main.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(main);

        add(temporaryLinks);
    }

    private VerticalLayout createBody() {
        Image logo = new Image("images/CollHBRSLogo.png", "logoImage");
        logo.setWidth("400px");
        logo.getElement().getStyle().set("border", "1px solid black");
        H2 slogan = new H2("Über 1 Milliarden vermittelte Stellen");
        H1 suchenText = new H1("Was suchen Sie?");
        VerticalLayout start = new VerticalLayout();
        start.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        start.add(logo,slogan,suchenText);
        start.setAlignSelf(Alignment.BASELINE);

        ComboBox<String> comboBox;
        comboBox = new ComboBox<>("Arbeit");
        comboBox.setItems("Praktikum", "Minijob", "Vollzeit", "Teilzeit");

        comboBox.setAllowCustomValue(false);
        comboBox.setPlaceholder("Arbeit");

        TextField textField = new TextField();
        textField.getElement().setAttribute("aria-label", "search");
        textField.setPlaceholder("z.B. Beruf,Stichwort ");
        textField.setClearButtonVisible(true);
        textField.setPrefixComponent(VaadinIcon.SEARCH.create());
        add(textField);

        // Fetch values from fields and redirect to JobListView, after that use filter-function
        Button searchButton = new Button("Suchen!");
        searchButton.addClickListener(e -> {
            if(!comboBox.isEmpty() || !textField.isEmpty())
            {
                // Vaadin ComboBox is not great. We have to specifically handle null-values.
                if(comboBox.isEmpty())
                    UI.getCurrent().navigate(Globals.Pages.JOBLIST_VIEW +
                        textField.getValue());
                else
                    UI.getCurrent().navigate(Globals.Pages.JOBLIST_VIEW +
                            textField.getValue() + "/" + comboBox.getValue());
            }
            // Used when both fields are empty, just a normal call of the JobListView
            else
                UI.getCurrent().navigate(Globals.Pages.JOBLIST_VIEW);
        });

        Button jobList = new Button("... oder alle Stellenangebote ansehen!");
        jobList.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.JOBLIST_VIEW));

        HorizontalLayout hl = new HorizontalLayout(comboBox, textField, searchButton);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        hl.setSpacing(false);
        hl.setWidthFull();
        hl.setJustifyContentMode(JustifyContentMode.CENTER);
        VerticalLayout body = new VerticalLayout(start, hl, jobList);
        body.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        body.setSizeFull();

        return body;
    }
}