package com.setqt.Hiring.DTO.APIResponse;

import java.util.Date;

public class SearchResponse {
    private Long id_JobPosting;
    private String title;
    private Date postDate;
    private Long companyId;
    private String companyName;
    private String companyLogo;
    private String salary;
    private String addressWork;
    private String experience;

    public SearchResponse(Long id_JobPosting, String title, Date postDate, Long companyId, String companyName, String companyLogo, String salary, String addressWork, String experience) {
        this.id_JobPosting = id_JobPosting;
        this.title = title;
        this.postDate = postDate;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.salary = salary;
        this.addressWork = addressWork;
        this.experience = experience;
    }

    public Long getId_JobPosting() {
        return id_JobPosting;
    }

    public void setId_JobPosting(Long id_JobPosting) {
        this.id_JobPosting = id_JobPosting;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAddressWork() {
        return addressWork;
    }

    public void setAddressWork(String addressWork) {
        this.addressWork = addressWork;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
