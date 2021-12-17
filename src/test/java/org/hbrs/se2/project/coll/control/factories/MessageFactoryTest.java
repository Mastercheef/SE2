package org.hbrs.se2.project.coll.control.factories;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class MessageFactoryTest {

    private final String errorMessage = "class org.hbrs.se2.project.coll.control.factories.MessageFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.MessageFactory with modifiers \"private\"";
    @Test
    void createMessage() {
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingJobFactory() throws NoSuchMethodException {
        Constructor<MessageFactory> constructor = MessageFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }
}