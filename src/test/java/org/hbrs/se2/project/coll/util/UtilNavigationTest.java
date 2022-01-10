package org.hbrs.se2.project.coll.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class UtilNavigationTest {

    String errorMessage = "class org.hbrs.se2.project.coll.util.UtilNavigationTest cannot access a member of class org.hbrs.se2.project.coll.util.UtilNavigation with modifiers \"private\"";


    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancing() throws NoSuchMethodException {
        Constructor<UtilNavigation> constructor = UtilNavigation.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }


}