package org.hbrs.se2.project.coll.dtos;

public class LoginResultDTO {
    private boolean result;
    private String reason;

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}