package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UtilCurrentTest {

    String errorMessage = "class org.hbrs.se2.project.coll.util.UtilCurrentTest cannot access a member of class org.hbrs.se2.project.coll.util.UtilCurrent with modifiers \"private\"";

    static UI ui = new UI();

    @BeforeAll
    public static void setUp() {
        UI.setCurrent(ui);

        VaadinSession session = Mockito.mock(VaadinSession.class);
        Mockito.when(session.hasLock()).thenReturn(true);
        ui.getInternals().setSession(session);
    }

    @AfterEach
    public void tearDown() {
        UI.setCurrent(null);
    }

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
        assertEquals("" , UtilCurrent.getCurrentLocation());

    }

    @Test
    void getCurrentUser() {
        assertNull( UtilCurrent.getCurrentUser());
    }
}