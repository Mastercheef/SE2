package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;

import java.util.List;

public class JobApplicationResultDTOImpl implements JobApplicationResultDTO {
    private boolean result;
    private List<JobApplicationResultDTO.ReasonType> reasons;

    public void setResult(boolean result) { this.result = result; }

    @Override
    public boolean getResult() { return result; }

    @Override
    public List<JobApplicationResultDTO.ReasonType> getReasons() { return reasons; }

    @Override
    public void setReasons(List<JobApplicationResultDTO.ReasonType> reasons) { this.reasons = reasons; }

    @Override
    public void addReason(JobApplicationResultDTO.ReasonType reason) { this.reasons.add(reason); }
}
