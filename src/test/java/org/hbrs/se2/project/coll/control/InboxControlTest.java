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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
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
    @Test
    void getMessages() {
        ArrayList<MessageDTO> messageDTOArrayList = new ArrayList<MessageDTO>();
        messageDTOArrayList.add(messageDTO);
        when(messageRepository.findMessagesByRecipient(100)).thenReturn(messageDTOArrayList);
        assertEquals(1 , inboxControl.getMessages(100).size());
        messageDTOArrayList.add(messageDTO);
        assertEquals(2 , inboxControl.getMessages(100).size());
    }
}