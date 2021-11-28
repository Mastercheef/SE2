package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("st")
@Table( name ="col_tab_student" , schema = "collhbrs" )
public class StudentUser extends User {

    private String graduation;
    private String skills;
    private String interests;
    private String website;
    private String description;
    private String subjectField;
    private Set<JobAdvertisement> applications;

    @Basic
    @Column(name = "graduation")
    public String getGraduation() {
        return graduation;
    }
    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    @Basic
    @Column(name = "skills")
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Basic
    @Column(name = "interests")
    public String getInterests() {
        return interests;
    }
    public void setInterests(String interests) {
        this.interests = interests;
    }

    @Basic
    @Column(name = "website")
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "student_description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "subject_field")
    public String getSubjectField() {
        return subjectField;
    }
    public void setSubjectField(String subjectField) {
        this.subjectField = subjectField;
    }

    @ManyToMany
    @JoinTable(name = "col_tab_application")
    public Set<JobAdvertisement> getApplications() {
        return applications;
    }
    public void setApplications(Set<JobAdvertisement> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return this.getId()  + ", " +
                this.getSalutation() + ", " +
                this.getTitle() + ", " +
                this.getFirstName() + ", " +
                this.getLastName() + ", " +
                this.getDateOfBirth()  + ", " +
                this.getPhone() + ", " +
                this.getAddress() + ", " +
                this.getEmail() + ", " +
                this.getPassword() + ", " +
                this.getGraduation() + ", " +
                this.getSkills() + ", " +
                this.getInterests() + ", " +
                this.getWebsite() + ", " +
                this.getDescription() + ", " +
                this.getSubjectField();
    }
}
