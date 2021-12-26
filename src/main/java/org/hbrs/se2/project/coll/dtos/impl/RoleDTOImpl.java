package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.RoleDTO;

public class RoleDTOImpl implements RoleDTO {

    private String designation;

    @Override
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String getDesignation() {
        return this.designation;
    }

    @Override
    public String toString() {
        return "RoleDTOImpl{" +
                "designation='" + designation + '\'' +
                '}';
    }
}