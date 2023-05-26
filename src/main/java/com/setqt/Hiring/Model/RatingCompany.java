package com.setqt.Hiring.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "RatingCompany")
public class RatingCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double rate;
    private String content;

    @ManyToOne
    @JoinColumn(name="company_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value="rating_company")
    private Company company;

    @ManyToOne
    @JoinColumn(name="candidate_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value="rating_company")
    private Candidate candidate;


    public RatingCompany(Double rate, String content, Company company, Candidate candidate) {
        this.rate = rate;
        this.content = content;
        this.company = company;
        this.candidate = candidate;
    }

    public RatingCompany() {

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
