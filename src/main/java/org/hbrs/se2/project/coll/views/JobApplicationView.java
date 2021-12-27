package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.*;
import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.html.*;

import org.hbrs.se2.project.coll.control.*;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = Globals.Pages.JOBADVERTISEMENT_VIEW + ":adID/" + Globals.Pages.APPLICATION_VIEW , layout = AppView.class)
@PageTitle(Globals.PageTitles.APPLICATION_PAGE_TITLE)
public class JobApplicationView extends Div implements BeforeEnterObserver {

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private JobApplicationControl jobApplicationControl;
    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    private JobAdvertisement jobAdvertisement;
    private StudentUserDTO studentUserDTO;

    H2 pageHeadline = new H2("Bewerbung");

    Span sSalutation = new Span();
    Span sName = new Span();
    Span sDateOfBirth = new Span();
    Span sEmail = new Span();
    Span sPhone = new Span();

    Span sAddress = new Span();
    Span sLocation = new Span();
    Span sCountry = new Span();

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        try {
            loadStudentUserInformation();
            jobAdvertisement = jobAdvertisementRepository.findJobAdvertisementById(Integer.parseInt(event.getRouteParameters().get("adID").get()));

            if (jobAdvertisement != null) {
                pageHeadline = new H2("Bewerbung auf " + jobAdvertisement.getJobTitle() + " (" + jobAdvertisement.getId() + ")");
                createJobApplicationView();
            } else {
                openErrorDialog("Fehler", "Das angegebene Stellenangebot existiert nicht");
            }
        } catch (NumberFormatException e) {
            openErrorDialog("Fehler", "Es handelt sich um keine gültige Stellenagebits ID");
        } catch (Exception exception) {
            openErrorDialog("Fehler", "Beim Laden des Bewerbungsformulars, ist ein Fehler aufgetreten");
        }

    }

    TextField   headline            = new TextField("Betreff");
    TextArea    text                = new TextArea("Bewerbung");

    class ApplicationForm extends Div {

        ApplicationForm(){

            text.setMinHeight("350px");
            text.setMaxLength(3000);
            text.setValueChangeMode(ValueChangeMode.EAGER);
            text.addValueChangeListener(e -> {
                e.getSource().setHelperText(e.getValue().length() + "/" + 3000);
            });

            FormLayout formLayout = new FormLayout();
            formLayout.add(
                    headline,
                    text
            );
            formLayout.setColspan(headline, 2);
            formLayout.setColspan(text, 2);
            formLayout.setSizeUndefined();
            this.add(formLayout);
        }

        //public UserDTOImpl createNewApplicationDTO() {}
    }

    public void createJobApplicationView() {

        VerticalLayout personalInformation = new VerticalLayout(sSalutation, sName, sAddress, sLocation, sCountry, sEmail, sPhone);
        personalInformation.setSpacing(false);
        personalInformation.setPadding(false);

        Details personalDetails = new Details("Persönliche Informationen", personalInformation);
        personalDetails.setOpened(true);

        HorizontalLayout detailLayout = new HorizontalLayout(personalDetails);
        detailLayout.setWidthFull();

        VerticalLayout section = new VerticalLayout();
        section.setWidth("50%");

        ApplicationForm form = new ApplicationForm();
        form.setWidth("100%");
        form.getElement().getStyle().set("Margin", "30px");
        Button applyButton = new Button("Bewerbung absenden");
        section.add(pageHeadline, detailLayout, form, applyButton);

        section.setPadding(true);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setAlignSelf(FlexComponent.Alignment.CENTER);

        HorizontalLayout siteLayout = new HorizontalLayout();
        siteLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        siteLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        siteLayout.setSizeFull();
        siteLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        siteLayout.add(section);

        applyButton.addClickListener(e -> {

        });
        add(siteLayout);
    }

    public void loadStudentUserInformation() {
        try {
            if(getCurrentUser() != null) {
                studentUserDTO = studentUserRepository.findStudentUserById(this.getCurrentUser().getId());
                sSalutation = new Span(studentUserDTO.getSalutation() + " " + studentUserDTO.getTitle());
                sName = new Span(studentUserDTO.getFirstName() + " " + studentUserDTO.getLastName());
                sDateOfBirth = new Span(Utils.convertToGermanDateFormat(studentUserDTO.getDateOfBirth()));
                sEmail = new Span(studentUserDTO.getEmail());
                sPhone = new Span(studentUserDTO.getPhone());

                sAddress = new Span(studentUserDTO.getAddress().getStreet() + " " + studentUserDTO.getAddress().getHouseNumber());
                sLocation = new Span(studentUserDTO.getAddress().getPostalCode() + " " + studentUserDTO.getAddress().getCity());
                sCountry = new Span(studentUserDTO.getAddress().getCountry());
            }
        } catch (Exception exception) {
            openErrorDialog("Fehler", "Beim Laden der Benutzerinformationen ist ein Fehler aufgetreten: " + exception);
        }
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    public void openErrorDialog(String headerText, String message) {
        Dialog dialog = new Dialog();

        H3 header = new H3(headerText);
        Label contentText = new Label(message);

        Button ok = new Button("Ok");

        ok.addClickListener(e -> dialog.close());

        HorizontalLayout head = new HorizontalLayout(header);
        HorizontalLayout text = new HorizontalLayout(contentText);
        HorizontalLayout butt = new HorizontalLayout(ok);

        VerticalLayout dialogContent = new VerticalLayout(header, text, butt);
        dialogContent.setAlignItems(FlexComponent.Alignment.CENTER);
        dialog.add(dialogContent);
        dialog.open();
    }

}
