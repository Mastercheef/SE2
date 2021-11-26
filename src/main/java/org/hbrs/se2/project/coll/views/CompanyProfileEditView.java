package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.hbrs.se2.project.coll.control.CompanyProfileControl;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.layout.MainLayout;
import org.hbrs.se2.project.coll.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "companyprofile_edit", layout = MainLayout.class)
@PageTitle("Edit your Profile")
public class CompanyProfileEditView extends VerticalLayout  implements HasUrlParameter<String> {

    @Autowired
    private CompanyProfileControl profileControl;
    private CompanyProfileDTO profileDTO;
    int companyId;

    Label companyname   = new Label("Firmenname:");
    Label address       = new Label("Adresse:");
    Label email         = new Label("E-Mail:");
    Label phone         = new Label("Telefon:");
    Label fax           = new Label("Fax:");
    Label website       = new Label("Webseite:");
    Label description   = new Label("Beschreibung:");

    TextField lcompanyname  = new TextField();
    TextField laddress      = new TextField();
    TextField lemail        = new TextField();
    TextField lphone        = new TextField();
    TextField lfax          = new TextField();
    TextField lwebsite      = new TextField();
    TextField ldescription  = new TextField();

    Div div = new Div();

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        if (parameter != "") {
            profileDTO = profileControl.findCompanyProfileByCompanyId(Integer.parseInt(parameter));
            initLabels(profileDTO);
            createProfile();
        }
    }

    // Used to read DTO data and inject them into the labels
    public void initLabels(CompanyProfileDTO profileDTO) {
        companyId = profileDTO.getId();
        lcompanyname.setPlaceholder(profileDTO.getCompanyName());
        laddress.setPlaceholder(profileDTO.getAddress().toString());
        lemail.setPlaceholder(profileDTO.getEmail());
        lphone.setPlaceholder(String.valueOf(profileDTO.getPhoneNumber()));
        lfax.setPlaceholder(String.valueOf(profileDTO.getFaxNumber()));
        lwebsite.setPlaceholder(profileDTO.getWebsite());
        ldescription.setPlaceholder(profileDTO.getDescription());
    }

    // TODO: Profilbild

    public void createProfile() {

        //TODO: Update UserDTO after editing

        // Layout
        H2 h2 = new H2("Editiere mein Firmenprofil");
        //h2.getElement().getStyle().set("margin-top", "55px");

        // TODO: Get Image from Database
        // Profile Image
        Image profileImage = new Image("https://thispersondoesnotexist.com/image", "placeholder");
        profileImage.setWidth("200px");
        profileImage.getElement().getStyle().set("border", "1px solid black");


        // Styling
        for (Label label : new Label[]{ companyname, address, email, phone, fax, website, description}) {
            label.getElement().getStyle().set("font-weight", "bold");
            label.getElement().getStyle().set("width", "200px");        // For alignment
        }

        for (TextField textfield : new TextField[]{ lcompanyname, laddress, lemail, lphone, lfax,
                lwebsite, ldescription}) {
            textfield.getElement().getStyle().set("height", "20px");
            textfield.getElement().getStyle().set("width", "300px");
        }

        // Profile Data
        HorizontalLayout hcompanyname = new HorizontalLayout();
        hcompanyname.add(companyname, lcompanyname);
        HorizontalLayout haddress = new HorizontalLayout();
        haddress.add(address, laddress);
        HorizontalLayout hemail = new HorizontalLayout();
        hemail.add(email, lemail);
        HorizontalLayout hphone = new HorizontalLayout();
        hphone.add(phone, lphone);
        HorizontalLayout hfax = new HorizontalLayout();
        hfax.add(fax, lfax);
        HorizontalLayout hwebsite = new HorizontalLayout();
        hwebsite.add(website, lwebsite);
        HorizontalLayout hdescription = new HorizontalLayout();
        hdescription.add(description, ldescription);

        // Buttons for saving/cancelling
        HorizontalLayout hbuttons   = new HorizontalLayout();
        Button saveButton           = new Button("Speichern");
        Button cancelButton         = new Button("Abbrechen");

        // TODO: save in ProfileDTO (o.ä.)
        saveButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW +
                companyId));
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW +
                companyId));
        hbuttons.add(saveButton, cancelButton);

        // Alignment of profile information
        for (HorizontalLayout HL : new HorizontalLayout[]{ hcompanyname, haddress, hemail, hphone, hfax, hwebsite,
                hdescription, hbuttons }) {
            HL.getElement().getStyle().set("margin-top", "11px");
        }

        // Append everything to the site
        div.add(h2, profileImage, hcompanyname, haddress, hemail, hphone, hfax, hwebsite,
                hdescription, hbuttons);
        add(div);

    }

    public void StudentProfileEditView() {

    }
}
