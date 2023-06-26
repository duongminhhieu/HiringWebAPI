package com.setqt.Hiring.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name="report")
public class Report {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "id_job_posting")
    @JsonBackReference(value="job_report")
    private JobPosting jobPosting;

    @ManyToOne
    @JoinColumn(name = "id_cd")
    @JsonBackReference(value="report_candidate")
    private Candidate candidate;

    public Report(String content, JobPosting jobPosting, Candidate candidate) {
        this.content = content;
        this.jobPosting = jobPosting;
        this.candidate = candidate;
    }

    public Report() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
