package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleDTOImplTest {

    private RoleDTOImpl roleDTO;

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTOImpl();
        roleDTO.setBezeichnung("Manager");
    }

    @Test
    void setBezeichnung() {
        assertEquals("Manager" , roleDTO.getBezeichhnung());
    }

    @Test
    void getBezeichhnung() {
        assertEquals("Manager" , roleDTO.getBezeichhnung());

    }

    @Test
    void testToString() {
        assertEquals("RolleDTOImpl{bezeichnung='Manager'}" , roleDTO.toString());
    }
}