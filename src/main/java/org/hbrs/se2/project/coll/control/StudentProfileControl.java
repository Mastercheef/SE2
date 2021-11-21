package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentProfileControl {

    @Autowired
    private StudentUserRepository repository;

    public StudentUserDTO loadProfileData(String userid) { return repository.findUserByUserid(userid); }
}
