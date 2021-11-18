package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "" )
@PWA(name = "Coll@HBRS", shortName = "Coll")

public class MainView extends VerticalLayout {

    public MainView()  {
        createHeader();
        createBody();
        createFooter();

    }

    private void createHeader() {
        Button student = new Button("Student Button");
        Button arbeitgeber = new Button("Arbeitgeber Button");

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
        menuBar.addItem(student);
        menuBar.addItem(arbeitgeber);

        HorizontalLayout header = new HorizontalLayout(menuBar);
        header.setSpacing(false);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.END);
        add(header);
    }

    private void createBody() {

        Image logo = new Image("https://www.imgur.com/UJYNMct.png", "logoImage");
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

        ComboBox<String> comboBox = new ComboBox("Arbeit");
        comboBox.setItems("Job" , "Praktikum");

        comboBox.setAllowCustomValue(false);
        comboBox.setPlaceholder("Arbeit");

        HorizontalLayout hl = new HorizontalLayout(comboBox, textField);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        hl.setSpacing(false);
        hl.setWidthFull();
        hl.setJustifyContentMode(JustifyContentMode.CENTER);
        VerticalLayout body = new VerticalLayout(start,hl);
        add(body);


    }

    private void createFooter() {
        H2 copyright = new H2("Copyright 2021-2022");
        Button kontakt = new Button("Kontakt Button");
        Button impressum = new Button("Impressum Button");
        Button datenschutz = new Button("Datenschutz Button");

        MenuBar menuBar = new MenuBar();
        menuBar.addItem(copyright);
        menuBar.addItem(kontakt);
        menuBar.addItem(impressum);
        menuBar.addItem(datenschutz);

        HorizontalLayout footer = new HorizontalLayout(menuBar);
        add(footer);
    }
}
