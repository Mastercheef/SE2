package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleDTOImplTest {

    private RoleDTOImpl roleDTO;

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTOImpl();
        roleDTO.setDesignation("Manager");
    }

    @Test
    void setLabel() {
        assertEquals("Manager" , roleDTO.getDesignation());
    }

    @Test
    void getBezeichhnung() {
        assertEquals("Manager" , roleDTO.getDesignation());
    }

    @Test
    void testToString() {
        assertEquals("RoleDTOImpl{designation='Manager'}" , roleDTO.toString());
    }
}