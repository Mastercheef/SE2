package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileControl {

    @Autowired
    private UserRepository repository;

    public UserDTO loadProfileDataByUserId(int userid) { return repository.findUserByUserId(userid); }
}
