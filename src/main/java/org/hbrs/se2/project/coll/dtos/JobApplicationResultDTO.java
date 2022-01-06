package org.hbrs.se2.project.coll.dtos;

import java.util.List;

public interface JobApplicationResultDTO {
    public boolean getResult();

    public enum ReasonType {
        SUCCESS,
        HEADLINE_MISSING,
        TEXT_MISSING,
        UNEXPECTED_ERROR
    }

    public List<JobApplicationResultDTO.ReasonType> getReasons();
    public void setReasons(List<JobApplicationResultDTO.ReasonType> reasons);
    public void addReason(JobApplicationResultDTO.ReasonType reason);
    public int getApplicationID();
}
