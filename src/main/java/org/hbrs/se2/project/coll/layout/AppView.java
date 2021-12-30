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
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
import org.hbrs.se2.project.coll.util.Utils;
import org.hbrs.se2.project.coll.views.DataProtectionView;
import org.hbrs.se2.project.coll.views.ImpressumView;
import org.hbrs.se2.project.coll.views.StudentProfileView;
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
    private final Label copyright = new Label("Copyright © 2021-2022");
    MenuBar navigationBar = new MenuBar();

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SettingsControl settingsControl;

    public AppView() {
        if (Utils.getCurrentUser() == null) {
            LOGGER.info("LOG: In Constructor of App View - No User given!");
        }
        setUpUI();
    }

    public void setUpUI() {
        getElement().getStyle().set("background-color", "#fffdeb");
        // Anzeige des Toggles über den Drawer
        setPrimarySection(Section.DRAWER);

        // Erstellung der horizontalen Statusleiste (Header)
        addToNavbar(true, createHeaderContent());

        // Erstellung der vertikalen Navigationsleiste (Drawer)
        menu = createMenu();
        //addToDrawer(createDrawerContent(menu));
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
        //layout.add(new DrawerToggle());
        HorizontalLayout homeIconHorizontalLayout = new HorizontalLayout();
        homeIconHorizontalLayout.setHeightFull();
        homeIconHorizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        homeIconHorizontalLayout.setJustifyContentMode( FlexComponent.JustifyContentMode.CENTER );
        homeIconHorizontalLayout.setFlexGrow(1);
        homeIconHorizontalLayout.setPadding(true);

        H1 homeIcon = new H1("Coll@HBRS");
        homeIcon.getElement().getClassList().add("pointer");
        homeIcon.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW));
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
            navigationBar.addItem("Registrieren" , e -> UI.getCurrent().navigate(Globals.Pages.REGISTER_VIEW));
            navigationBar.addItem("Login" , e -> UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW));
        }
        headerNavigationPanel.add(navigationBar);
        headerLayout.add( headerNavigationPanel );
        return headerLayout;
    }

    private void initNavigationBar(boolean unreadMessages) {
        /* Decide, depending on User TYPE (st = student, cp = contactperson) which button to load */
        String currentUserType = Utils.getCurrentUser().getType();
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

        // TODO: Add click listener for application tab
        MenuItem applications = createIconItem(inboxSubMenu, VaadinIcon.FILE, "Bewerbungen",
                null, true);
        //applications.addClickListener(e -> navigateToApplications());

        /*  Check if:
            - If a user has enabled notifications
            - There are unread messages
            - TODO: Check for unread applications
        */
        if(settingsControl.getUserSettings(Utils.getCurrentUser().getId()).getNotificationIsEnabled())
        {
            if(unreadMessages)
            {
                inbox.getElement().getStyle().set("color", "rgb(66, 221, 21)");
                messages.getElement().getStyle().set("color", "rgb(66, 221, 21)");
            }
            // TODO
            /*if(unreadApplications)
            {
                inbox.getElement().getStyle().set("color", "rgb(66, 221, 21)");
                applications.getElement().getStyle().set("color", "rgb(66, 221, 21)");
            }*/
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

    private void navigateToUserProfile() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentUserId = Integer.toString(Utils.getCurrentUser().getId());
        if(!Objects.equals(currentLocation, Globals.Pages.PROFILE_VIEW + currentUserId))
            UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + currentUserId);
    }

    private void navigateToCompanyProfile() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentCompanyId = Integer.toString(getContactPersonsCompanyId());
        if(!Objects.equals(currentLocation, Globals.Pages.COMPANYPROFILE_VIEW + currentCompanyId))
            UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + currentCompanyId);
    }

    private void navigateToMessages() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentUserId = Integer.toString(Utils.getCurrentUser().getId());
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
        ui.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
    }


    /**
     * Hinzufügen der vertikalen Leiste (Drawer)
     * Diese besteht aus dem Logo ganz oben links sowie den Menu-Einträgen (menu items).
     * Die Menu Items sind zudem verlinkt zu den internen Tab-Components.
     * @param menu
     * @return
     */
    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();

        // Hinzufügen des Logos
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "HelloCar logo"));
        logoLayout.add(new H1("HelloCar"));

        HorizontalLayout footer = new HorizontalLayout(copyright);
        footer.add(new RouterLink("Impressum", ImpressumView.class));
        footer.add(new RouterLink("Datenschutz", DataProtectionView.class));
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        footer.setWidth("100%");
        footer.setPadding(true);
        footer.getElement().getStyle().set("background-color", "#233348");
        footer.getElement().getStyle().set("color", "white");
        footer.getElement().getStyle().set("clear", "both");
        footer.getElement().getStyle().set("bottom", "0");
        footer.getElement().getStyle().set("left", "0");
        footer.getElement().getStyle().set("position", "fixed");

        // Hinzufügen des Menus inklusive der Tabs
        layout.add(logoLayout, menu, footer);
        return layout;
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
        //tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {

        // Jeder User sollte Autos sehen können, von daher wird dieser schon mal erzeugt und
        // und dem Tabs-Array hinzugefügt. In der Methode createTab wird ein (Key, Value)-Pair übergeben:
        // Key: der sichtbare String des Menu-Items
        // Value: Die UI-Component, die nach dem Klick auf das Menuitem angezeigt wird.
        return new Tab[]{ createTab( "Profil", StudentProfileView.class) };

        // ToDo für die Teams: Weitere Tabs aus ihrem Projekt hier einfügen!

    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
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
            initNavigationBar(messageRepository.findMessagesByRecipientAndRead(Utils.getCurrentUser().getId(),
                    false).size() > 0);
        }

        // Der aktuell-selektierte Tab wird gehighlighted.
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Setzen des aktuellen Names des Tabs
        //viewTitle.setText(getCurrentPageTitle());

    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = Utils.getCurrentUser();
        return userDTO != null;
    }

    private boolean navigateToLoginIfSessionNeeded() {
        // Falls der Benutzer nicht eingeloggt ist, und versucht eine interne Seite aufzurufen,
        // dann wird er auf die Startseite gelenkt
        UserDTO userDTO = Utils.getCurrentUser();
        String pageTitle = getCurrentPageTitle();
        if (userDTO == null && !pageTitle.equals(Globals.PageTitles.REGISTER_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.LOGIN_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.MAIN_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.JOBADVERTISEMENT_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.JOBLIST_PAGE_TITLE)) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            //UI.getCurrent().getPage().reload();
            return false;
        }
        return true;
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    private String getCurrentNameOfUser() {
        return Utils.getCurrentUser().getFirstName();
    }

    private int getContactPersonsCompanyId() {
        int contactPersonId = Utils.getCurrentUser().getId();
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
        if (Utils.getCurrentUser() == null){
            LOGGER.info("Reroute");
            //beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }

    }
}
