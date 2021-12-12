package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.coll.layout.AppView;

import java.util.logging.Logger;

@Route(value = "inbox", layout = AppView.class)
@PageTitle("Posteingang")
public class InboxView extends VerticalLayout implements HasUrlParameter<String> {

    private static final Logger LOGGER = Logger.getLogger(InboxView.class.getName());

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        /*try {
            if (!Objects.equals(parameter, "")) {
                CompanyDTO profileDTO = profileControl.findCompanyProfileByCompanyId(Integer.parseInt(parameter));
                address = profileDTO.getAddress();
                initLabels(profileDTO);
                createProfile();
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO,e.toString());
        }
*/
    }


    public InboxView() {
        //Required because of VAADIN
    }

}
