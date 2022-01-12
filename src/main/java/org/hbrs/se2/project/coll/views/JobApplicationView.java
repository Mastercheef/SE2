package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
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
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.layout.JobApplicationFormularLayout;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

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
    private String error ="Fehler";

    JobApplicationFormularLayout jobApplicationFormularLayout = new JobApplicationFormularLayout();

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
                Utils.triggerDialogMessage(error, "Die angegebene Bewerbung existiert nicht");
            }
        } catch (NumberFormatException e) {
            Utils.triggerDialogMessage(error, "Es handelt sich um keine gültige Bewerbungs ID");
        } catch (Exception exception) {
            Utils.triggerDialogMessage(error, "Beim Laden der Bewerbung ist ein unerwarteter Felher aufgetreten");
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
        Details personalDetails = new Details("Persönliche Informationen",
                jobApplicationFormularLayout.personalInformationVerticalLayout());
        personalDetails.setOpened(true);

        HorizontalLayout detailLayout = new HorizontalLayout(personalDetails);
        detailLayout.setWidthFull();

        VerticalLayout section = new VerticalLayout();
        section.setWidth("50%");

        Button contactApplicant = new Button("Bewerber kontaktieren");
        contactApplicant.addClickListener(e ->
                UI.getCurrent().navigate(Globals.Pages.CONTACTING_VIEW +
                        jobApplication.getStudentUser().getId()));

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


    public void loadStudentUserInformation() {
        try {
            StudentUser studentUser = jobApplication.getStudentUser();
            jobApplicationFormularLayout.loadStudentUserInfo(studentUser);
        } catch (Exception exception) {
            Utils.triggerDialogMessage(error, "Beim Laden der Benutzerinformationen ist ein Fehler aufgetreten: " + exception);
        }
    }

    private boolean checkIfCurrentUserIsApplicant() {
        return jobApplication.getStudentUser().getId() == UtilCurrent.getCurrentUser().getId();
    }

    private boolean checkIfCurrentUserIsContactPerson() {
        return jobApplication.getJobAdvertisement().getContactPerson().getId() == UtilCurrent.getCurrentUser().getId();
    }

    private boolean checkForAuthorization() {
        return checkIfCurrentUserIsApplicant() || checkIfCurrentUserIsContactPerson();
    }
}
