package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
/**
 * JPA-Repository für die Verwaltung von Autos (cars). Die Bezeichnung einer Methode
 * bestimmt dabei die Selektionsbedingung (den WHERE-Teil). Der Rückgabewert einer
 * Methode bestimmt den Projectionsbedingung (den SELECT-Teil).
 * Mehr Information über die Entwicklung von Queries in JPA:
 * https://www.baeldung.com/spring-data-jpa-projections
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
 *
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    CompanyDTO findCompanyProfileById(int id );
    CompanyDTO findCompanyByCompanyNameAndEmailAndWebsite(
            String name, String email, String website);
}



