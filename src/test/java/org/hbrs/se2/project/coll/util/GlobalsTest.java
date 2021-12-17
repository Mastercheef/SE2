package org.hbrs.se2.project.coll.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class GlobalsTest {

    String errorMessageGlobalsClass = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals with modifiers \"private\"";
    String errorMessageGlobalsPagesClass =  "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$Pages with modifiers \"private\"";
    String errorMessageGlobalsPagesTitlesClass = "class org.hbrs.se2.project.coll.util.GlobalsTest cannot access a member of class org.hbrs.se2.project.coll.util.Globals$PageTitles with modifiers \"private\"";
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


}