package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
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
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.impl.JobApplicationDTOImpl;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.layout.AppView;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.JobApplicationUtil;
import org.hbrs.se2.project.coll.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Route(value = Globals.Pages.JOBADVERTISEMENT_VIEW + ":adID/" + Globals.Pages.APPLICATION_VIEW , layout = AppView.class)
@PageTitle(Globals.PageTitles.APPLICATION_PAGE_TITLE)
public class JobApplicationFormularView extends Div implements BeforeEnterObserver {

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private JobApplicationControl jobApplicationControl;
    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    private JobAdvertisement jobAdvertisement;
    private StudentUserDTO studentUserDTO;

    JobApplicationFormularVariables jobApplicationFormularVariables = new JobApplicationFormularVariables();


    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        try {
            loadStudentUserInformation();
            jobAdvertisement = jobAdvertisementRepository.findJobAdvertisementById(Integer.parseInt(event.getRouteParameters().get("adID").get()));

            if (jobAdvertisement != null) {
                jobApplicationFormularVariables.setPageHeadline(new H2("Bewerbung auf " + jobAdvertisement.getJobTitle() + " (" + jobAdvertisement.getId() + ")"));
                createJobApplicationView();
            } else {
                Utils.triggerDialogMessage(jobApplicationFormularVariables.getError(), "Das angegebene Stellenangebot existiert nicht");
            }
        } catch (NumberFormatException e) {
            Utils.triggerDialogMessage(jobApplicationFormularVariables.getError(), "Es handelt sich um keine gültige Stellenagebots ID");
        } catch (Exception exception) {
            Utils.triggerDialogMessage(jobApplicationFormularVariables.getError(), "Beim Laden des Bewerbungsformulars, ist ein Fehler aufgetreten");
        }

    }

    TextField   headline            = new TextField("Betreff");
    TextArea    text                = new TextArea("Bewerbung");

    class ApplicationForm extends Div {

        ApplicationForm(){

            text.setMinHeight("350px");
            text.setMaxLength(3000);
            text.setValueChangeMode(ValueChangeMode.EAGER);
            text.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + 3000)
            );

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

        public JobApplicationDTOImpl createNewApplicationDTO() {
            JobApplicationDTOImpl applicationDTO = new JobApplicationDTOImpl();
            applicationDTO.setStudentUser(UserFactory.createStudentUser(studentUserDTO));
            applicationDTO.setJobAdvertisementDTO(jobAdvertisement);
            applicationDTO.setHeadline(headline.getValue());
            applicationDTO.setText(text.getValue());
            applicationDTO.setDate(LocalDate.now());

            return applicationDTO;
        }
    }

    public void createJobApplicationView() {

        VerticalLayout personalInformation = new VerticalLayout(
                jobApplicationFormularVariables.getsSalutation(), jobApplicationFormularVariables.getsName(), jobApplicationFormularVariables.getsAddress(),
                jobApplicationFormularVariables.getsLocation(), jobApplicationFormularVariables.getsCountry(), jobApplicationFormularVariables.getsEmail(),
                jobApplicationFormularVariables.getsPhone());
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
        section.add(jobApplicationFormularVariables.getPageHeadline(), detailLayout, form, applyButton);

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
            JobApplicationResultDTO applicationResult = jobApplicationControl.createJobApplication(form.createNewApplicationDTO());
            if (applicationResult.getResult()) {
                Utils.triggerDialogMessage("Bewerbung übermittelt", "Wir haben Ihre Bewerbung erfolgreich übermittelt");
                navigateToJobApplication(applicationResult.getApplicationID());
            } else {
                setErrorFields(applicationResult.getReasons());
            }
        });
        add(siteLayout);
    }
    public static void navigateToJobApplication(int id) {
        if(!Objects.equals(Utils.getCurrentLocation(), Globals.Pages.JOBAPPLICATION_VIEW))
            UI.getCurrent().navigate(Globals.Pages.JOBAPPLICATION_VIEW + id);
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
                studentUserDTO = studentUserRepository.findStudentUserById(Utils.getCurrentUser().getId());
                StudentUser studentUser =  UserFactory.createStudentUser(studentUserDTO);
                JobApplicationUtil.loadStudentUserInfo(studentUser);
            }
        } catch (Exception exception) {
            Utils.triggerDialogMessage(jobApplicationFormularVariables.getError(), "Beim Laden der Benutzerinformationen ist ein Fehler aufgetreten: " + exception);
        }
    }

}
