package org.hbrs.se2.project.coll.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.HasMenuItems;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.control.SettingsControl;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.util.UtilCurrent;
import org.hbrs.se2.project.coll.util.UtilNavigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/views/main/main-view.css")
@JsModule("./styles/shared-styles.js")
public class AppView extends AppLayout implements BeforeEnterObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppView.class);

    private Tabs menu;
    private H1 helloUser;
    MenuBar navigationBar = new MenuBar();

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SettingsControl settingsControl;

    // Constants
    private static final String NOTIFICATION_COLOR = "rgb(66, 221, 21)";

    public AppView() {
        if (UtilCurrent.getCurrentUser() == null) {
            LOGGER.info("LOG: In Constructor of App View - No User given!");
        }
        setUpUI();
    }

    public void setUpUI() {
        getElement().getStyle().set("background-color", "white");
        // Anzeige des Toggles über den Drawer
        setPrimarySection(Section.DRAWER);

        // Erstellung der horizontalen Statusleiste (Header)
        addToNavbar(true, createHeaderContent());

        // Erstellung der vertikalen Navigationsleiste (Drawer)
        menu = createMenu();

    }

    /**
     * Erzeugung der horizontalen Leiste (Header).
     * @return
     */
    private Component   createHeaderContent() {
        // Ein paar Grund-Einstellungen. Alles wird in ein horizontales Layout gesteckt.
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setId("header");
        headerLayout.getThemeList().set("dark", true);
        headerLayout.setWidthFull();
        headerLayout.setSpacing(false);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setJustifyContentMode( FlexComponent.JustifyContentMode.EVENLY );

        // Hinzufügen des Toogle ('Big Mac') zum Ein- und Ausschalten des Drawers

        HorizontalLayout homeIconHorizontalLayout = new HorizontalLayout();
        homeIconHorizontalLayout.setHeightFull();
        homeIconHorizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        homeIconHorizontalLayout.setJustifyContentMode( FlexComponent.JustifyContentMode.CENTER );
        homeIconHorizontalLayout.setFlexGrow(1);
        homeIconHorizontalLayout.setPadding(true);

        H1 homeIcon = new H1("Coll@HBRS");
        homeIcon.getElement().getClassList().add("pointer");
        homeIcon.addClickListener(e -> UtilNavigation.navigateToMain());
        homeIconHorizontalLayout.add(homeIcon);
        headerLayout.add(homeIconHorizontalLayout);

        // Interner Layout
        HorizontalLayout headerNavigationPanel = new HorizontalLayout();
        headerNavigationPanel.setWidthFull();
        headerNavigationPanel.setJustifyContentMode( FlexComponent.JustifyContentMode.END );
        headerNavigationPanel.setAlignItems( FlexComponent.Alignment.CENTER );
        headerNavigationPanel.setFlexGrow(5);

        // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
        helloUser = new H1();
        headerNavigationPanel.add(helloUser);

        // If user is not logged in, show Login/Register Buttons
        if(!checkIfUserIsLoggedIn()) {
            navigationBar.addItem("Registrieren" , e -> navigateToRegistration());
            navigationBar.addItem("Login" , e -> navigateToLogin());
        }
        headerNavigationPanel.add(navigationBar);
        headerLayout.add( headerNavigationPanel );
        return headerLayout;
    }

    private void initNavigationBar(boolean allMessagesRead, boolean allApplicationsRead) {
        /* Decide, depending on User TYPE (st = student, cp = contactperson) which button to load */
        String currentUserType = UtilCurrent.getCurrentUser().getType();
        if (Objects.equals(currentUserType, "st"))
            navigationBar.addItem("Mein Profil", e -> navigateToUserProfile());
        else if (Objects.equals(currentUserType, "cp"))
            navigationBar.addItem("Mein Firmenprofil", e -> navigateToCompanyProfile());

        // Inbox and Submenu (Messages, Applications)
        MenuItem inbox = createIconItem(navigationBar, VaadinIcon.ENVELOPE, "Posteingang", null);
        SubMenu inboxSubMenu = inbox.getSubMenu();

        MenuItem messages = createIconItem(inboxSubMenu, VaadinIcon.ENVELOPE, "Nachrichten",
                null, true);
        messages.addClickListener(e -> navigateToMessages());

        MenuItem applications = createIconItem(inboxSubMenu, VaadinIcon.FILE, "Bewerbungen",
                null, true);


        /*  Check if:
            - If a user has enabled notifications
            - There are unread messages
            - TODO: Check for unread applications
        */
        if(settingsControl.getUserSettings(UtilCurrent.getCurrentUser().getId()).getNotificationIsEnabled())
        {
            if(!allMessagesRead || !allApplicationsRead)
                colorItem(inbox, NOTIFICATION_COLOR);

            if(!allMessagesRead)
                colorItem(messages, NOTIFICATION_COLOR);

            if(!allApplicationsRead)
                colorItem(applications, NOTIFICATION_COLOR);
        }
        createIconItem(navigationBar, VaadinIcon.COG, "", null)
                .addClickListener(e -> navigateToSettings());
        navigationBar.addItem("Logout", e -> logoutUser());
    }

    /*  Used top create Icons for the app menu bar.
        Source: https://vaadin.com/docs/v14/ds/components/menu-bar
    */
    private MenuItem createIconItem(HasMenuItems menu, VaadinIcon iconName, String label, String ariaLabel) {
        return createIconItem(menu, iconName, label, ariaLabel, false);
    }
    private MenuItem createIconItem(HasMenuItems menu, VaadinIcon iconName, String label,
                                    String ariaLabel, boolean isChild) {
        Icon icon = new Icon(iconName);

        if (isChild) {
            icon.getStyle().set("width", "var(--lumo-icon-size-s)");
            icon.getStyle().set("height", "var(--lumo-icon-size-s)");
            icon.getStyle().set("marginRight", "var(--lumo-space-s)");
        }

        MenuItem item = menu.addItem(icon, e -> {
        });

        if (ariaLabel != null) {
            item.getElement().setAttribute("aria-label", ariaLabel);
        }

        if (label != null) {
            item.add(new Text(label));
        }

        return item;
    }

    private void colorItem(MenuItem item, String color) {
        item.getElement().getStyle().set("color", color);
    }

    private void navigateToUserProfile() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentUserId = Integer.toString(UtilCurrent.getCurrentUser().getId());
        if(!Objects.equals(currentLocation, Globals.Pages.PROFILE_VIEW + currentUserId))
            UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + currentUserId);
    }

    private void navigateToCompanyProfile() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentCompanyId = Integer.toString(getContactPersonsCompanyId());
        if(!Objects.equals(currentLocation, Globals.Pages.COMPANYPROFILE_VIEW + currentCompanyId))
            UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + currentCompanyId);
    }

    private void navigateToRegistration() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        if(!Objects.equals(currentLocation, Globals.Pages.REGISTER_VIEW))
            UI.getCurrent().navigate(Globals.Pages.REGISTER_VIEW);
    }

    private void navigateToLogin() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        if(!Objects.equals(currentLocation, Globals.Pages.LOGIN_VIEW))
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
    }

    private void navigateToMessages() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentUserId = Integer.toString(UtilCurrent.getCurrentUser().getId());
        if(!Objects.equals(currentLocation, Globals.Pages.INBOX_VIEW + currentUserId))
            UI.getCurrent().navigate(Globals.Pages.INBOX_VIEW + currentUserId);
    }

    private void navigateToSettings() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        if(!Objects.equals(currentLocation, Globals.Pages.SETTINGS_VIEW))
            UI.getCurrent().navigate(Globals.Pages.SETTINGS_VIEW);
    }

    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        UtilNavigation.navigateToMain();
    }



    /**
     * Erzeugung des Menu auf der vertikalen Leiste (Drawer)
     * @return
     */
    private Tabs createMenu() {

        // Anlegen der Grundstruktur
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");

        // Anlegen der einzelnen Menuitems
        return tabs;
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        navigateToLoginIfSessionNeeded();

        if ( checkIfUserIsLoggedIn() ) {
            // Setzen des Vornamens von dem aktuell eingeloggten Benutzer
            helloUser.setText("Hallo " + this.getCurrentNameOfUser() );
            navigationBar.removeAll();

            // Highlight des Posteingang-Tabs, wenn es ungelesene Nachrichten gibt
            // TODO: allApplicationsRead Parameter durch repository-call ersetzen für Applications
            initNavigationBar(
                    messageRepository.findMessagesByRecipientAndRead(UtilCurrent.getCurrentUser().getId(),false).isEmpty(),
                    true
                    );
        }

        // Der aktuell-selektierte Tab wird gehighlighted.
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = UtilCurrent.getCurrentUser();
        return userDTO != null;
    }

    private boolean navigateToLoginIfSessionNeeded() {
        // Falls der Benutzer nicht eingeloggt ist, und versucht eine interne Seite aufzurufen,
        // dann wird er auf die Startseite gelenkt
        UserDTO userDTO = UtilCurrent.getCurrentUser();
        String pageTitle = getCurrentPageTitle();
        if (userDTO == null && !pageTitle.equals(Globals.PageTitles.REGISTER_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.LOGIN_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.MAIN_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.JOBADVERTISEMENT_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.JOBLIST_PAGE_TITLE)) {
            navigateToLogin();
            return false;
        }
        return true;
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    private String getCurrentNameOfUser() {
        return UtilCurrent.getCurrentUser().getFirstName();
    }

    private int getContactPersonsCompanyId() {
        int contactPersonId = UtilCurrent.getCurrentUser().getId();
        ContactPerson contactPerson = contactPersonRepository.findContactPersonById(contactPersonId);
        return contactPerson.getCompany().getId();
    }

    @Override
    /**
     * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen.
     * Hier kann man die finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist
     * Dann erfolgt hier ein ReDirect auf die Login-Seite. Eine Navigation (Methode navigate)
     * ist hier nicht möglich, da die finale Navigation noch nicht stattgefunden hat.
     * Diese Methode in der AppLayout sichert auch den un-authorisierten Zugriff auf die innerliegenden
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (UtilCurrent.getCurrentUser() == null){
            LOGGER.info("Reroute");
        }

    }
}
