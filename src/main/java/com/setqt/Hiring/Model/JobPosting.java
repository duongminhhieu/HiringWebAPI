package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.ToString;


@Entity
//@Data
@ToString
@Table(name = "JobPosting")
public class JobPosting implements Serializable {
    private static final long serialVersionUID = -297553221792804396L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value = "job_company")
    private Company company;


    @OneToMany(mappedBy = "jobPosting")
    @JsonManagedReference(value = "job_posting")
    private List<CV> listCV;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_description_id")
    @JsonManagedReference(value = "job_descript")
    private JobDescription jobDescription;

    @OneToMany(mappedBy = "jobPosting")
    @JsonManagedReference(value = "job_report")
    private List<Report> reports;

    @OneToMany(mappedBy = "jobPosting")
    @JsonManagedReference(value = "saved_job_posting")
    private List<SavedJobPosting> savedJobPostingList;

    private String title;
    private Date postDate;
    private Date dueDate;
    private int view;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public Company getCompanyInfo() {

        Company companyResponse = new Company();

        companyResponse.setId(company.getId());
        companyResponse.setLogo(company.getLogo());
        companyResponse.setTaxCode(company.getTaxCode());
        companyResponse.setCompanySize(company.getCompanySize());
        companyResponse.setWorkTime(company.getWorkTime());
        companyResponse.setDescription(company.getDescription());
        companyResponse.setDomain(company.getDomain());
        companyResponse.setName(company.getName());
        companyResponse.setEmployer(null);
        companyResponse.setJobPostingList(null);
        companyResponse.setRate(company.getRate());
        companyResponse.setRatingCompanies(null);
        companyResponse.setAddress(company.getAddress());

        return companyResponse;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JobDescription getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(JobDescription jobDescription) {
        this.jobDescription = jobDescription;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public JobPosting() {

    }

    public List<CV> getListCV() {
        return listCV;
    }

    public void setListCV(List<CV> listCV) {
        this.listCV = listCV;
    }

    public List<SavedJobPosting> getSavedJobPostingList() {
        return savedJobPostingList;
    }

    public void setSavedJobPostingList(List<SavedJobPosting> savedJobPostingList) {
        this.savedJobPostingList = savedJobPostingList;
    }

    public JobPosting(Company company, List<CV> listCV, JobDescription jobDescription, List<Report> reports, List<SavedJobPosting> savedJobPostingList, String title, Date postDate, Date dueDate, int view) {
        this.company = company;
        this.listCV = listCV;
        this.jobDescription = jobDescription;
        this.reports = reports;
        this.savedJobPostingList = savedJobPostingList;
        this.title = title;
        this.postDate = postDate;
        this.dueDate = dueDate;
        this.view = view;
    }

    @Override
    public String toString() {
        return "JobPosting [id=" + id
                + ", title=" + title + ", postDate=" + postDate + ", dueDate="
                + dueDate + ", view=" + view + "]";
    }


}
