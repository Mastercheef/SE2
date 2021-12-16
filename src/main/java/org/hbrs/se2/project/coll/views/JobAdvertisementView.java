package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;

//TODO: UserDTO für Daten auslesen und in Textfelder einfüllen

@Route(value = "recruitment", layout = LayoutAlternative.class)
@PageTitle("Coll | Registration")
public class JobAdvertisementView extends VerticalLayout{

    H2 headerJobName = new H2("Datenbank-Experte");

    Label llabelCompanyName = new Label("Mustermann GmbH");

    Label labelTypeOfEmployment = new Label("Typ");
    Label llabelTypeOfEmployment = new Label("Arbeit");

    Label labelFormOfEmployment = new Label("Arbeitszeit");
    Label llabelFormOfEmployment = new Label("Vollzeit");

    Label labelBeginnOfJob = new Label("Eintrittsdatum");
    Label llabelBeginnOfJob = new Label("ab sofort");

    Label labelWorkingLocation = new Label("Arbeitsort");
    Label llabelWorkingLocation = new Label("HomeOffice");

    Label labelJobDescription = new Label("Stellenbeschreibung");
    Label llabelJobDescription = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");

    Label labelRequirementForApplicants = new Label("Anforderungen");
    Label llabelRequirementForApplicants = new Label("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt");

    Label labelBusinessAdress = new Label("Adresse");
    Label lbusinessAdress = new Label("Musterstrasse 1, 12345 Musterstadt");

    Label labelTelephoneNumber = new Label("Telefon");
    Label ltelephoneNumber = new Label("01234 567890");

    Label labelTemporaryEmployment = new Label("kurzfristige Beschäftigung ");
    Label ltemporaryEmployment = new Label("Nein ");
    Label dateOfTemporaryEmployment;

    Label labelContactPerson = new Label("Kontaktperson");
    Label lcontactPerson = new Label("Markus Mustermann ");

    Label labelEmailAdress = new Label("E-Mail");
    Label lemailAdress = new Label("maxmustermann@email.de");

    Div div           = new Div();

    public JobAdvertisementView() {

        //TODO: Methods for grabbing UserDTO data

        for (Label label : new Label[]{llabelCompanyName, labelTypeOfEmployment, labelFormOfEmployment, labelBeginnOfJob,
                labelWorkingLocation,labelJobDescription,labelRequirementForApplicants,labelBusinessAdress,
                labelTelephoneNumber,labelTemporaryEmployment,labelContactPerson,labelEmailAdress }) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");
        }

        // TODO: Get Data from UserDTO
        HorizontalLayout hjobName = new HorizontalLayout();
        hjobName.add(headerJobName);
        HorizontalLayout hlabelCompanyName = new HorizontalLayout();
        hlabelCompanyName.add((llabelCompanyName));

        HorizontalLayout hlabelTypeOfEmployment = new HorizontalLayout();
        hlabelTypeOfEmployment.add(labelTypeOfEmployment,llabelTypeOfEmployment);

        HorizontalLayout hformOfEmployment = new HorizontalLayout();
        hformOfEmployment.add(labelFormOfEmployment,llabelFormOfEmployment);

        HorizontalLayout hlabelBeginnOfJob = new HorizontalLayout();
        hlabelBeginnOfJob.add(labelBeginnOfJob,llabelBeginnOfJob);

        HorizontalLayout hlabelWorkingLocation = new HorizontalLayout();
        hlabelWorkingLocation.add(labelWorkingLocation,llabelWorkingLocation);


        div.add(hjobName,hlabelCompanyName,hlabelTypeOfEmployment,hformOfEmployment,
                hlabelBeginnOfJob,hlabelWorkingLocation);

        add(div);

        VerticalLayout vlabelJobDescription = new VerticalLayout();
        vlabelJobDescription.add(labelJobDescription,llabelJobDescription);

        VerticalLayout vlabelRequirementForApplicants = new VerticalLayout();
        vlabelRequirementForApplicants.add(labelRequirementForApplicants,llabelRequirementForApplicants);

        add(vlabelJobDescription,vlabelRequirementForApplicants);


        HorizontalLayout hbusinessAdress = new HorizontalLayout();
        hbusinessAdress.add(labelBusinessAdress,lbusinessAdress);

        HorizontalLayout htelephoneNumber = new HorizontalLayout();
        htelephoneNumber.add(labelTelephoneNumber,ltelephoneNumber);

        HorizontalLayout htemporaryEmployment = new HorizontalLayout();
        htemporaryEmployment.add(labelTemporaryEmployment,ltemporaryEmployment);

        HorizontalLayout hcontactPerson = new HorizontalLayout();
        hcontactPerson.add(labelContactPerson,lcontactPerson);

        HorizontalLayout hemailAdress = new HorizontalLayout();
        hemailAdress.add(labelEmailAdress,lemailAdress);

        add(hbusinessAdress,htelephoneNumber,htemporaryEmployment,hcontactPerson,hemailAdress);

    }

}
