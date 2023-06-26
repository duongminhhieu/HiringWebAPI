package com.setqt.Hiring.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "RatingCompany")
public class RatingCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double rate;
    @Column(columnDefinition="TEXT")
    private String content;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name="company_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value="rating_company")
    private Company company;

    @ManyToOne
    @JoinColumn(name="candidate_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value="rating_company")
    private Candidate candidate;


    public RatingCompany(Double rate, String content, Date createdDate, Company company, Candidate candidate) {
        this.rate = rate;
        this.content = content;
        this.createdDate = createdDate;
        this.company = company;
        this.candidate = candidate;
    }

    public RatingCompany() {

    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCompanyId(){
        return this.company.getId();
    }

    public String getNameCompany(){
        return this.company.getName();
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
