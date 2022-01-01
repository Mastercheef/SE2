package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.entities.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class MessageFactoryTest {

    private static final String ERROR_MESSAGE = "class org.hbrs.se2.project.coll.control.factories.MessageFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.MessageFactory with modifiers \"private\"";

    @Mock
    MessageDTO messageDTO;

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingJobFactory() throws NoSuchMethodException {
        Constructor<MessageFactory> constructor = MessageFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(ERROR_MESSAGE, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void createMessage() {

        String messageDTOContent = "messageDTO Content";
        String messageDTOSubject = "messageDTO Subject";
        LocalDate date = LocalDate.of(2000,1,2);

        when(messageDTO.getId()).thenReturn(100);
        when(messageDTO.getRecipient()).thenReturn(200);
        when(messageDTO.getSender()).thenReturn(300);

        when(messageDTO.getContent()).thenReturn(messageDTOContent);
        when(messageDTO.getSubject()).thenReturn(messageDTOSubject);
        when(messageDTO.getDate()).thenReturn(date);
        Message message = MessageFactory.createMessage(messageDTO);
        assertEquals(100 , message.getId());
    }

}