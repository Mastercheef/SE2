package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InboxControlTest {

    @InjectMocks
    InboxControl inboxControl;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Mock
    private ContactPersonRepository contactPersonRepository;

    @Mock
    MessageDTO messageDTO;

    MessageDTO messageDTOMethod;

    private String message = "message";
    private String subbject = "Subject";
    private LocalDate date = LocalDate.of(2000,1 ,12);
    @Test
    void getMessages() {
        ArrayList<MessageDTO> messageDTOArrayList = new ArrayList<>();
        messageDTOArrayList.add(messageDTO);
        when(messageRepository.findMessagesByRecipient(100)).thenReturn(messageDTOArrayList);
        assertEquals(1 , inboxControl.getMessages(100).size());
        messageDTOArrayList.add(messageDTO);
        assertEquals(2 , inboxControl.getMessages(100).size());
    }

    @Test
    void prepareSending() {
        when(messageDTO.getRecipient()).thenReturn(200);
        when(messageDTO.getSender()).thenReturn(400);
        when(messageDTO.getSubject()).thenReturn(subbject);
        when(messageDTO.getDate()).thenReturn(date);

        messageDTOMethod = inboxControl.prepareSending(messageDTO,message);

        assertEquals(message , messageDTOMethod.getContent());
        assertEquals(400 , messageDTOMethod.getRecipient());
        assertEquals(200 , messageDTOMethod.getSender());
        assertEquals(subbject , messageDTOMethod.getSubject());
        assertEquals(message , messageDTOMethod.getContent());
        assertEquals(LocalDate.now() , messageDTOMethod.getDate());



    }
}