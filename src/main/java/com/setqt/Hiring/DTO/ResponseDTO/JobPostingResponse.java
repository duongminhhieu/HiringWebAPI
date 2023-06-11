package com.setqt.Hiring.DTO.ResponseDTO;

import java.util.Date;

public class JobPostingResponse {
    private Long id;
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

    public JobPostingResponse(Long id, String title, Date postDate, Date dueDate, String description, String benefits, String requirement, String gender, String experience, String salary, int number_candidates, String working_form, String address_work) {
        this.id = id;
        this.title = title;
        this.postDate = postDate;
        this.dueDate = dueDate;
        this.description = description;
        this.benefits = benefits;
        this.requirement = requirement;
        this.gender = gender;
        this.experience = experience;
        this.salary = salary;
        this.number_candidates = number_candidates;
        this.working_form = working_form;
        this.address_work = address_work;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
