package org.hbrs.se2.project.coll.control.factories;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class MessageFactoryTest {

    private static final String ERROR_MESSAGE = "class org.hbrs.se2.project.coll.control.factories.MessageFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.MessageFactory with modifiers \"private\"";

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingJobFactory() throws NoSuchMethodException {
        Constructor<MessageFactory> constructor = MessageFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(ERROR_MESSAGE, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }
}