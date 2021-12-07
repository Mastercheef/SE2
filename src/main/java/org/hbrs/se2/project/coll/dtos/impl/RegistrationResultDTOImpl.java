package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;


public class RegistrationResultDTOImpl implements RegistrationResultDTO {
    private boolean result = false;
    private String reason = "";

    public void RegistrationResult(boolean result_, String reason_){
        this.setResult(result_);
        this.setReason(reason_);
    }

    public boolean getResult(){
        return this.result;
    }

    public boolean setResult(boolean result){
        this.result = result;
        return this.result;
    }

    public String getReason(){
        return this.reason;
    }


    public String setReason(String reason){
        this.reason = reason;
        return this.reason;
    }

    public String toString(){
        return this.getReason();
    }
}
