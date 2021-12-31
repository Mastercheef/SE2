package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.control.JobApplicationControl;
import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = Globals.Pages.JOBAPPLICATION_VIEW + ":appID" , layout = AppView.class)
@PageTitle(Globals.PageTitles.APPLICATION_PAGE_TITLE)
public class JobApplicationView extends Div implements BeforeEnterObserver {

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private JobApplicationControl jobApplicationControl;
    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    private JobApplicationDTO jobApplication;
    private JobAdvertisement jobAdvertisement;

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
            jobApplication = jobApplicationControl.loadJobApplication(Integer.parseInt(event.getRouteParameters().get("appID").get()));

            if (jobApplication != null) {
                if (checkForAuthorization()) {
                    pageHeadline = new H2(jobApplication.getHeadline() + " (" + jobApplication.getId() + ")");
                    loadStudentUserInformation();
                    createJobApplicationView();
                } else {
                    Utils.triggerDialogMessage("Zugriff verweigert", "Sie haben keinen Zugriff auf die angegebene Bewerbung");
                }
            } else {
                Utils.triggerDialogMessage("Fehler", "Die angegebene Bewerbung existiert nicht");
            }
        } catch (NumberFormatException e) {
            Utils.triggerDialogMessage("Fehler", "Es handelt sich um keine gültige Bewerbungs ID");
        } catch (Exception exception) {
            Utils.triggerDialogMessage("Fehler", "Beim Laden der Bewerbung ist ein unerwarteter Felher aufgetreten");
        }

    }

    TextField   headline            = new TextField("Betreff");
    TextArea    text                = new TextArea("Bewerbung");
    Label       date                = new Label();

    class Application extends Div {

        Application(){

            headline.setValue(jobApplication.getHeadline());
            text.setValue(jobApplication.getText());
            date.setText(Utils.convertToGermanDateFormat(jobApplication.getDate()));

            headline.setReadOnly(true);
            text.setMinHeight("350px");
            text.setReadOnly(true);

            FormLayout formLayout = new FormLayout();
            formLayout.add(
                    date,
                    headline,
                    text
            );
            formLayout.setColspan(headline, 2);
            formLayout.setColspan(text, 2);
            formLayout.setSizeUndefined();
            this.add(formLayout);
        }

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

        Button contactApplicant = new Button("Bewerber kontaktieren");
        contactApplicant.addClickListener(e -> {

        });

        Application application = new Application();
        application.setWidth("100%");
        application.getElement().getStyle().set("Margin", "30px");
        section.add(pageHeadline, detailLayout, application);

        if (checkIfCurrentUserIsContactPerson()) {
            section.add(contactApplicant);
        }

        section.setPadding(true);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setAlignSelf(FlexComponent.Alignment.CENTER);

        HorizontalLayout siteLayout = new HorizontalLayout();
        siteLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        siteLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        siteLayout.setSizeFull();
        siteLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        siteLayout.add(section);

        add(siteLayout);
    }

    public void setErrorFields(List<JobApplicationResultDTO.ReasonType> reasons) {
        for (JobApplicationResultDTO.ReasonType reason : reasons) {
            if (reason == JobApplicationResultDTO.ReasonType.UNEXPECTED_ERROR) {
                Utils.triggerDialogMessage(Globals.View.ERROR, "Es ist ein unerwarteter Fehler aufgetreten");
            }
            if (reason == JobApplicationResultDTO.ReasonType.HEADLINE_MISSING) {
                headline.setErrorMessage("Bitte geben Sie einen Betreff ein");
                headline.setInvalid(true);
            }
            if (reason == JobApplicationResultDTO.ReasonType.TEXT_MISSING) {
                text.setErrorMessage("Bitte geben Sie einen Bewerbungstext ein");
                text.setInvalid(true);
            }
        }
    }

    public void loadStudentUserInformation() {
        try {
            if(Utils.getCurrentUser() != null) {
                StudentUser studentUser = jobApplication.getStudentUser();
                System.out.println(studentUser.getId());
                sSalutation = new Span(studentUser.getSalutation() + " " + studentUser.getTitle());
                sName = new Span(studentUser.getFirstName() + " " + studentUser.getLastName());
                sDateOfBirth = new Span(Utils.convertToGermanDateFormat(studentUser.getDateOfBirth()));
                sEmail = new Span(studentUser.getEmail());
                sPhone = new Span(studentUser.getPhone());

                sAddress = new Span(studentUser.getAddress().getStreet() + " " + studentUser.getAddress().getHouseNumber());
                sLocation = new Span(studentUser.getAddress().getPostalCode() + " " + studentUser.getAddress().getCity());
                sCountry = new Span(studentUser.getAddress().getCountry());
            }
        } catch (Exception exception) {
            Utils.triggerDialogMessage("Fehler", "Beim Laden der Benutzerinformationen ist ein Fehler aufgetreten: " + exception);
        }
    }

    private boolean checkIfCurrentUserIsApplicant() {
        return jobApplication.getStudentUser().getId() == Utils.getCurrentUser().getId();
    }

    private boolean checkIfCurrentUserIsContactPerson() {
        return jobApplication.getJobAdvertisement().getContactPerson().getId() == Utils.getCurrentUser().getId();
    }

    private boolean checkForAuthorization() {
        return checkIfCurrentUserIsApplicant() || checkIfCurrentUserIsContactPerson();
    }
}
