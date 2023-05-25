package com.setqt.Hiring.DTO;

import java.io.Serializable;
import java.util.Date;

public class JobPostingDTO implements Serializable {

    private String title;
    private Date postDate;
    private Date dueDate;
    private String description;
    private String benefits;
    private String requirement;
    private String gender;
    private String experience;
    private String salary;
    private int number_candidates;
    private String working_form;
    private String address_work;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public int getNumber_candidates() {
        return number_candidates;
    }

    public void setNumber_candidates(int number_candidates) {
        this.number_candidates = number_candidates;
    }

    public String getWorking_form() {
        return working_form;
    }

    public void setWorking_form(String working_form) {
        this.working_form = working_form;
    }

    public String getAddress_work() {
        return address_work;
    }

    public void setAddress_work(String address_work) {
        this.address_work = address_work;
    }

    @Override
    public String toString() {
        return "JobPostingDTO{" +
                "title='" + title + '\'' +
                ", postDate=" + postDate +
                ", dueDate=" + dueDate +
                ", description='" + description + '\'' +
                ", benefits='" + benefits + '\'' +
                ", requirement='" + requirement + '\'' +
                ", gender='" + gender + '\'' +
                ", experience='" + experience + '\'' +
                ", salary='" + salary + '\'' +
                ", number_candidates=" + number_candidates +
                ", working_form='" + working_form + '\'' +
                ", address_work='" + address_work + '\'' +
                '}';
    }
}
