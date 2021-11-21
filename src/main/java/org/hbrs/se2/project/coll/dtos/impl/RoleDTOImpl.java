package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.RoleDTO;

public class RoleDTOImpl implements RoleDTO {

    private String bezeichnung;

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichhnung() {
        return this.bezeichnung;
    }

    @Override
    public String toString() {
        return "RolleDTOImpl{" +
                "bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}