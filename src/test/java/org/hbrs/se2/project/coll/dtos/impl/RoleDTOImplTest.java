package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleDTOImplTest {

    private RoleDTOImpl roleDTO;

    private final String manager = "Manager";

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTOImpl();
        roleDTO.setDesignation(manager);
    }

    @Test
    void setLabel() {
        assertEquals(manager , roleDTO.getDesignation());
    }

    @Test
    void getBezeichhnung() {
        assertEquals(manager , roleDTO.getDesignation());
    }

    @Test
    void testToString() {
        assertEquals("RoleDTOImpl{designation='Manager'}" , roleDTO.toString());
    }
}