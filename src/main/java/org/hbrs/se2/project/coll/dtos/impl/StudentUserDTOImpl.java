package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;

import java.util.*;

public class StudentUserDTOImpl extends UserDTOImpl implements StudentUserDTO {
    private String graduation;
    private String skills;
    private String interests;
    private String website;
    private String description;

    public void setGraduation(String graduation) { this.graduation = graduation; }

    public void setSkills(String skills) { this.skills = skills; }

    public void setInterests(String interests) { this.interests = interests; }

    public void setWebsite(String website) { this.website = website; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String getGraduation() { return this.graduation; }

    @Override
    public String getSkills() { return this.skills; }

    @Override
    public String getInterests() { return this.interests; }

    @Override
    public String getWebsite() { return this.website; }

    @Override
    public String getDescription() { return this.description; }

}
