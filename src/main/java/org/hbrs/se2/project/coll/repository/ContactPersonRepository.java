package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
@Component
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer> {

    ContactPerson findContactPersonById(int id);
    ContactPerson findContactPersonByCompanyId(int id);

}