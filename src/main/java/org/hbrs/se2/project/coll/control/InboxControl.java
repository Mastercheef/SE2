package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InboxControl {

    @Autowired
    private MessageRepository messageRepository;

}
