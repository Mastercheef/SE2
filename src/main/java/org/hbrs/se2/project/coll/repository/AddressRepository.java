package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address>   getAllByPostalCode(String postalCode);
    List<Address>   getByIdAfter(int id);
    Address         getById(int id);
}
