package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


class UtilsTest {

    String errorMessage = "class org.hbrs.se2.project.coll.util.UtilsTest cannot access a member of class org.hbrs.se2.project.coll.util.Utils with modifiers \"private\"";


    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancing() throws NoSuchMethodException {
        Constructor<Utils> constructor = Utils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }



    @Test
    void appendLength() {
        Integer[] numberArray = {1, 2, 3};
        Integer[] numberArrayAppended = Utils.append(numberArray, 4);
        assertEquals(numberArray.length + 1, numberArrayAppended.length);
    }

    @Test
    void appendCheckObjectAppended() {

        Integer[] numberArray = {1, 2, 3};
        Integer[] numberArrayAppended = Utils.append(numberArray, 4);
        assertEquals(4, numberArrayAppended[numberArrayAppended.length - 1]);

    }

    @Test
    void stringIsEmpty() {
        String emptyString = "";
        assertTrue(Utils.stringIsEmptyOrNull(emptyString));
    }
    @Test
    void stringIsNotEmpty() {
        String notEmptyString ="String";
        assertFalse(Utils.stringIsEmptyOrNull(notEmptyString));
    }

    @Test
    void stringIsNotNull() {
        String notNullString = "StringNotNull";
        assertFalse(Utils.stringIsEmptyOrNull(notNullString));
    }
    @Test
    void stringIsNull() {
        String nullString = null;
        assertTrue(Utils.stringIsEmptyOrNull(nullString));
    }

    @Test
    void convertToGermanDateFormat() {
        LocalDate date1 = LocalDate.of(2001, 7, 9);
        assertEquals("9.7.2001", Utils.convertToGermanDateFormat(date1));
    }

    @Test
    void triggerDialogMessage() {
        UI ui = new UI();
        UI.setCurrent(ui);

        VaadinSession session = Mockito.mock(VaadinSession.class);
        Mockito.when(session.hasLock()).thenReturn(true);
        ui.getInternals().setSession(session);
        Utils.triggerDialogMessage("headerText" , "message");
        UI.setCurrent(null);


    }
}