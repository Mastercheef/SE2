package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.StudentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;

public interface StudentUserRepository extends JpaRepository<StudentUser, Integer> {

    StudentUserDTO findStudentUserByUserId(int userid);

}
