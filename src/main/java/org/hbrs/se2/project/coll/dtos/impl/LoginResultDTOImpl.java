package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.LoginResultDTO;

public class LoginResultDTOImpl implements LoginResultDTO {

    private boolean result;
    private String reason;

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean getResult() {
        return this.result;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

}
