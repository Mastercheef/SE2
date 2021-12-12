package org.hbrs.se2.project.coll.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.hbrs.se2.project.coll.views.ContactView;
import org.hbrs.se2.project.coll.views.DataProtectionView;
import org.hbrs.se2.project.coll.views.ImpressumView;
import org.hbrs.se2.project.coll.views.StudentProfileView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/views/main/main-view.css")
@JsModule("./styles/shared-styles.js")
public class AppView extends AppLayout implements BeforeEnterObserver {

    private Tabs menu;
    private H1 viewTitle;
    private H1 helloUser;
    private Label copyright = new Label("Copyright © 2021-2022");

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    public AppView() {
        if (getCurrentUser() == null) {
            System.out.println("LOG: In Constructor of App View - No User given!");
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
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode( FlexComponent.JustifyContentMode.EVENLY );

        // Hinzufügen des Toogle ('Big Mac') zum Ein- und Ausschalten des Drawers
        //layout.add(new DrawerToggle());
        viewTitle = new H1("Coll@HBRS");
        viewTitle.getElement().getClassList().add("pointer");
        viewTitle.setWidthFull();
        viewTitle.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW));
        layout.add( viewTitle );

        // Interner Layout
        HorizontalLayout topRightPanel = new HorizontalLayout();
        topRightPanel.setWidthFull();
        topRightPanel.setJustifyContentMode( FlexComponent.JustifyContentMode.END );
        topRightPanel.setAlignItems( FlexComponent.Alignment.CENTER );

        // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
        helloUser = new H1();
        topRightPanel.add(helloUser);

        // Logout-Button am rechts-oberen Rand.
        MenuBar bar = new MenuBar();

        if (checkIfUserIsLoggedIn()) {
            /* Decide, depending on User TYPE (st = student, cp = contactperson) which button to load */
            String currentUserType = getCurrentUser().getType();
            if(Objects.equals(currentUserType, "st"))
                bar.addItem("Mein Profil" , e -> navigateToUserProfile());
            else if(Objects.equals(currentUserType, "cp"))
                bar.addItem("Mein Firmenprofil", e -> navigateToCompanyProfile());
            bar.addItem("Logout" , e -> logoutUser());
        } else {
            bar.addItem("Registrieren" , e -> UI.getCurrent().navigate(Globals.Pages.REGISTER_VIEW));
            bar.addItem("Login" , e -> UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW));
        }

        topRightPanel.add(bar);

        layout.add( topRightPanel );
        return layout;
    }

    private void navigateToUserProfile() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentUserId = Integer.toString(getCurrentUser().getId());
        if(!Objects.equals(currentLocation, Globals.Pages.PROFILE_VIEW + currentUserId))
            UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW + currentUserId);
    }

    private void navigateToCompanyProfile() {
        String currentLocation = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        String currentCompanyId = Integer.toString(getContactPersonsCompanyId());
        if(!Objects.equals(currentLocation, Globals.Pages.COMPANYPROFILE_VIEW + currentCompanyId))
            UI.getCurrent().navigate(Globals.Pages.COMPANYPROFILE_VIEW + currentCompanyId);
    }

    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        ui.getPage().setLocation("/");
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
        footer.add(new RouterLink("Kontakt", ContactView.class));
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
            helloUser.setText("Hallo "  + this.getCurrentNameOfUser() );
        }

        // Der aktuell-selektierte Tab wird gehighlighted.
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Setzen des aktuellen Names des Tabs
        //viewTitle.setText(getCurrentPageTitle());
        System.out.println(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = this.getCurrentUser();
        if (userDTO == null) {
            return false;
        }
        return true;
    }

    private boolean navigateToLoginIfSessionNeeded() {
        // Falls der Benutzer nicht eingeloggt ist, und versucht eine interne Seite aufzurufen,
        // dann wird er auf die Startseite gelenkt
        UserDTO userDTO = this.getCurrentUser();
        String pageTitle = getCurrentPageTitle();
        if (userDTO == null && !pageTitle.equals(Globals.PageTitles.REGISTER_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.LOGIN_PAGE_TITLE) &&
                !pageTitle.equals(Globals.PageTitles.MAIN_PAGE_TITLE)) {
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
        return getCurrentUser().getFirstName();
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private int getContactPersonsCompanyId() {
        int contactPersonId = getCurrentUser().getId();
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
        if (getCurrentUser() == null){
            System.out.println("Reroute");
            //beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }

    }
}
