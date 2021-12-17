package org.hbrs.se2.project.coll.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class GlobalsTest {

    String errorMessageGlobalsClass = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals with modifiers \"private\"";
    String errorMessageGlobalsPagesClass =  "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$Pages with modifiers \"private\"";
    String errorMessageGlobalsPagesTitlesClass = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$PageTitles with modifiers \"private\"";
    String errorMessageGlobalsPagesLogMessage = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$LogMessage with modifiers \"private\"";
    String errorMessageGlobalsPagesExceptionMessage = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$ExceptionMessage with modifiers \"private\"";
    String errorMessageGlobalsPagesView = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$View with modifiers \"private\"";
    String errorMessageGlobalsPagesRegex = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$Regex with modifiers \"private\"";
    String errorMessageGlobalsPagesRoles = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$Roles with modifiers \"private\"";
    String errorMessageGlobalsPagesErrors = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$Errors with modifiers \"private\"";
    String errorMessageGlobalsPagesCountries = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$Countries with modifiers \"private\"";



    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingGlobals() throws NoSuchMethodException {
        Constructor<Globals> constructor = Globals.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsClass, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingGlobalsPages() throws NoSuchMethodException {
        Constructor<Globals.Pages> constructor = Globals.Pages.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesClass, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingGlobalsPageTitles() throws NoSuchMethodException {
        Constructor<Globals.PageTitles> constructor = Globals.PageTitles.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesTitlesClass, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingLogMessage() throws NoSuchMethodException {
        Constructor<Globals.LogMessage> constructor = Globals.LogMessage.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesLogMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingExceptionMessage() throws NoSuchMethodException {
        Constructor<Globals.ExceptionMessage> constructor = Globals.ExceptionMessage.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesExceptionMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingView() throws NoSuchMethodException {
        Constructor<Globals.View> constructor = Globals.View.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesView, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingRegex() throws NoSuchMethodException {
        Constructor<Globals.Regex> constructor = Globals.Regex.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesRegex, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingErrors() throws NoSuchMethodException {
        Constructor<Globals.Errors> constructor = Globals.Errors.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesErrors, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingRoles() throws NoSuchMethodException {
        Constructor<Globals.Roles> constructor = Globals.Roles.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesRoles, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingCountries() throws NoSuchMethodException {
        Constructor<Globals.Countries> constructor = Globals.Countries.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessageGlobalsPagesCountries, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }
    @Test
    void validEmailInput() {
       assertTrue(Globals.Regex.validateEmailInput("wow@gmx.de"));
       assertTrue(Globals.Regex.validateEmailInput("studi@hbrs.de"));
       assertTrue(Globals.Regex.validateEmailInput("admin@admin.de"));
    }

    @Test
    void invalidEmailInput() {
        assertFalse(Globals.Regex.validateEmailInput("wowgmx.de"));
        assertFalse(Globals.Regex.validateEmailInput("@hbrs.de"));
        assertFalse(Globals.Regex.validateEmailInput("hbrs.de"));
        assertFalse(Globals.Regex.validateEmailInput("studi@hbrs"));
        assertFalse(Globals.Regex.validateEmailInput("studihbrs.de"));
    }

    @Test
    void validNameInput() {
        assertTrue(Globals.Regex.validateNameInput("Dan"));
        assertTrue(Globals.Regex.validateNameInput("Jordan"));
        assertTrue(Globals.Regex.validateNameInput("Steve"));
        assertTrue(Globals.Regex.validateNameInput("Emerick Jones"));
        assertTrue(Globals.Regex.validateNameInput("Gary Allen"));
    }
    @Test
    void invalidNameInput() {
        assertFalse(Globals.Regex.validateNameInput("Steve P."));
        assertFalse(Globals.Regex.validateNameInput("Sierra %O'Neil"));
        assertFalse(Globals.Regex.validateNameInput("$John"));
    }

    @Test
    void getCountries() {
        assertEquals(249 , Globals.Countries.getCountries().size());
        assertEquals("Andorra" , Globals.Countries.getCountries().get(0));




    }
}