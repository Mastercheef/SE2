package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
@Component
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer> {

    ContactPerson findContactPersonById(int id);
    ContactPerson findContactPersonByCompany_Id(int id);

}