package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "JobAdvertisement" )
@PageTitle("Profile")

public class JobAdvertisementView {

    Label labelCompanyName = new Label("Max Mustermann GMBH");

    Label labelFormOfEmployment = new Label("Vollzeit");
    Label labelWorkingHours = new Label("40");
    Label labelBeginnOfJob = new Label("Jetzt");
    Label labelWorkingLocation = new Label("HomeOffice");

    Label labelJobDescription = new Label("Stellenbeschreibung:Lorem Ipsum");

    Label labelRequirementForApplicants = new Label("Anforderungen:Lorem Ipsum");
    Label labelBusinessAdress = new Label("Beispielstraße 2");
    Label labelTemporaryEmployment = new Label("Nicht befristet");
    Label labelContactPerson = new Label("Ansprechparter ist: ");
    Label labelEmailAdress = new Label("bewerung@unternehmen.de");

    Div div           = new Div();

    public JobAdvertisementView() {
        H2 h2 = new H2("Job1");
        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.add(labelCompanyName);

        labelEmailAdress.getStyle().set("width", "200px");
        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.add(labelFormOfEmployment, labelWorkingHours, labelBeginnOfJob, labelWorkingLocation);

        HorizontalLayout thirdRow = new HorizontalLayout(labelJobDescription);

        HorizontalLayout fourthRow = new HorizontalLayout(labelRequirementForApplicants);

        HorizontalLayout fifthRow = new HorizontalLayout(labelBusinessAdress, labelTemporaryEmployment, labelContactPerson, labelEmailAdress);

        div.add(firstRow,secondRow,thirdRow,fourthRow,fifthRow);


    }


}
