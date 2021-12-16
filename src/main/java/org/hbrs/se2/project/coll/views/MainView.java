package org.hbrs.se2.project.coll.views;
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
import org.apache.tomcat.util.digester.Digester;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;
import org.hbrs.se2.project.coll.util.Globals;

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
        H1 suchenText = new H1("Was suchen Sie");
        VerticalLayout start = new VerticalLayout();
        start.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        start.add(logo,slogan,suchenText);
        start.setAlignSelf(Alignment.BASELINE);

        TextField textField = new TextField();
        textField.getElement().setAttribute("aria-label", "search");
        textField.setPlaceholder("z.B. Beruf,Stichwort ");
        textField.setClearButtonVisible(true);
        textField.setPrefixComponent(VaadinIcon.SEARCH.create());
        add(textField);

        ComboBox<String> comboBox;
        comboBox = new ComboBox<>("Arbeit");
        comboBox.setItems("Job" , "Praktikum");

        comboBox.setAllowCustomValue(false);
        comboBox.setPlaceholder("Arbeit");

        HorizontalLayout hl = new HorizontalLayout(comboBox, textField);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        hl.setSpacing(false);
        hl.setWidthFull();
        hl.setJustifyContentMode(JustifyContentMode.CENTER);
        VerticalLayout body = new VerticalLayout(start,hl);
        body.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        body.setSizeFull();

        return body;
    }
}