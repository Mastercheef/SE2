package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;

import java.util.ArrayList;
import java.util.List;


public class RegistrationResultDTOImpl implements RegistrationResultDTO {
    private boolean result;
    private List<ReasonType> reasons;

    public RegistrationResultDTOImpl() { this.reasons = new ArrayList<>(); }

    @Override
    public boolean getResult() { return this.result; }

    public void setResult(boolean result) { this.result = result; }

    @Override
    public List<ReasonType> getReasons() { return this.reasons; }

    @Override
    public void setReasons(List<ReasonType> reasons) { this.reasons = reasons; }

    @Override
    public void addReason(ReasonType reason) { this.reasons.add(reason); }

    public String toString(){
        return this.getReasons().toString();
    }
}
