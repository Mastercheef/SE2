package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.hbrs.se2.project.coll.entities.TestEntityFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CompanyProfileDTOimplTest {

    TestEntityFactory factory = new TestEntityFactory();
    private CompanyProfile companyProfile;

    @BeforeEach
    void setUp(){
        companyProfile = factory.createCompanyProfile();
    }
}
