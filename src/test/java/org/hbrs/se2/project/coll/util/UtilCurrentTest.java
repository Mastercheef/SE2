package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UtilCurrentTest {

    String errorMessage = "class org.hbrs.se2.project.coll.util.UtilCurrentTest cannot access a member of class org.hbrs.se2.project.coll.util.UtilCurrent with modifiers \"private\"";


    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancing() throws NoSuchMethodException {
        Constructor<UtilCurrent> constructor = UtilCurrent.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void getCurrentLocation() {
        uiSetup();
        assertEquals("" , UtilCurrent.getCurrentLocation());
        shutdown();


    }

    @Test
    void getCurrentUser() {
        uiSetup();
        assertNull( UtilCurrent.getCurrentUser());
        shutdown();
    }


    private void uiSetup() {
        UI ui = new UI();
        UI.setCurrent(ui);

        VaadinSession session = Mockito.mock(VaadinSession.class);
        Mockito.when(session.hasLock()).thenReturn(true);
        ui.getInternals().setSession(session);
    }

    private void shutdown() {
        UI.setCurrent(null);
    }

}