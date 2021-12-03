package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.layout.LayoutAlternative;

@Route(value = "recruitment_formular", layout = LayoutAlternative.class)
@PageTitle("Formular für Job")
public class RecruitmentAdvertisementFormularView extends VerticalLayout {

    Label labelJobName = new Label("Jobtitel");
    TextField textjobName = new TextField();


    Label llabelCompanyName = new Label("Mustermann GmbH");

    Label labelTypeOfEmployment = new Label("Typ");
    Select<String> selectTypeOfEmployment = new Select<>();

    Label labelFormOfEmployment = new Label("Arbeitszeit");
    Select<String> selectFormOfEmployment = new Select<>();

    //TODO: Nur vom aktuellen datum
    Label labelBeginnOfJob = new Label("Eintrittsdatum");
    DatePicker datePicker = new DatePicker ();

    Label labelWorkingLocation = new Label("Arbeitsort");
    TextField textWorkingLocation = new TextField();

    Label labelJobDescription = new Label("Stellenbeschreibung");
    TextArea textJobDescription  = new TextArea("Description");

    Label labelRequirementForApplicants = new Label("Anforderungen");
    TextArea textRequirementForApplicants  = new TextArea("Description");

    Label labelBusinessAdress = new Label("Adresse");
    TextField textStreet = new TextField("Straße");
    TextField textHouseNumber = new TextField( "Hausnummer");
    TextField textPostalCode = new TextField("PLZ");
    TextField textCity = new TextField("Stadt");

    Label labelTelephoneNumber = new Label("Telefon");
    NumberField numberTelephoneNumber = new NumberField ("Nummer");

    Label labelTemporaryEmployment = new Label("kurzfristige Beschäftigung ");
    Select<String> selectTemporaryEmployment = new Select<>();

    Label labelContactPerson = new Label("Kontaktperson");
    TextField textContactPerson = new TextField();

    Label labelEmailAdress = new Label("E-Mail");
    TextField textEmailAdress = new TextField("email");

    Div div           = new Div();

    public RecruitmentAdvertisementFormularView() {

        setSizeFull();

        H2 h2 = new H2("Job Formular");

        for (Label label : new Label[]{ labelJobName,labelTypeOfEmployment,labelFormOfEmployment,labelBeginnOfJob,labelWorkingLocation,
                labelJobDescription,labelRequirementForApplicants,labelBusinessAdress,labelTelephoneNumber,labelTemporaryEmployment,
                labelContactPerson,labelEmailAdress}) {
            label.getElement().getStyle().set("font-weight", "bold");
        }
        HorizontalLayout hJobName = new HorizontalLayout();
        hJobName.add(labelJobName,textjobName);
        hJobName.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        HorizontalLayout hlabelTypeOfEmployment = new HorizontalLayout();

        selectTypeOfEmployment.setItems("Vollzeit", "Teilzeit","Minijob","Praktiktum");
        hlabelTypeOfEmployment.add(labelTypeOfEmployment,selectTypeOfEmployment);

        HorizontalLayout hBeginnOfJob = new HorizontalLayout();
        hBeginnOfJob.add(labelBeginnOfJob,datePicker);

        HorizontalLayout hworkingLocation = new HorizontalLayout();
        hworkingLocation.add(labelWorkingLocation,textWorkingLocation);

        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(hJobName,hlabelTypeOfEmployment,hBeginnOfJob,hworkingLocation);


        VerticalLayout vJobDescription = new VerticalLayout();
        textJobDescription.setWidthFull();
        textJobDescription.getStyle().set("minHeight", "150px");
        vJobDescription.add(labelJobDescription,textJobDescription);

        VerticalLayout vRequirementForApplicants = new VerticalLayout();
        textRequirementForApplicants.setWidthFull();
        textRequirementForApplicants.getStyle().set("minHeight", "150px");
        vRequirementForApplicants.add(labelRequirementForApplicants,textRequirementForApplicants);


        HorizontalLayout hbusinessAdress = new HorizontalLayout();
        hbusinessAdress.add(labelBusinessAdress,textStreet,textHouseNumber,textPostalCode,textCity);


        HorizontalLayout htelephoneNumber = new HorizontalLayout();
        numberTelephoneNumber.setPlaceholder("0123456789");
        htelephoneNumber.add(labelTelephoneNumber, numberTelephoneNumber);

        HorizontalLayout hContactPerson = new HorizontalLayout();
        textContactPerson.setPlaceholder("Max Mustermann");
        hContactPerson.add(labelContactPerson,textContactPerson);

        HorizontalLayout hEmailAdress = new HorizontalLayout();
        hEmailAdress.add(labelEmailAdress,textEmailAdress);

        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.add(htelephoneNumber,hContactPerson,hEmailAdress);
        for(HorizontalLayout hlayout : new HorizontalLayout[] {
                hJobName,hlabelTypeOfEmployment,hBeginnOfJob,hworkingLocation,hbusinessAdress,
                htelephoneNumber,hContactPerson,hEmailAdress,layout2
        }) {
            hlayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
            hlayout.setJustifyContentMode(JustifyContentMode.START);
            hlayout.getElement().getStyle().set("margin-top", "10px");
        }

        div.add(h2, h1,vJobDescription,
                vRequirementForApplicants,hbusinessAdress,layout2);
        div.setSizeFull();

        add(div);
    }
}
