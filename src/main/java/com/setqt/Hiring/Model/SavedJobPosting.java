package com.setqt.Hiring.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "saved_job_posting")
public class SavedJobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="candidate_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value="saved_job_posting")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name="job_posting_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value="saved_job_posting")
    private JobPosting jobPosting;

    public SavedJobPosting() {
    }

    public SavedJobPosting(Candidate candidate, JobPosting jobPosting) {
        this.candidate = candidate;
        this.jobPosting = jobPosting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdJobPosting(){
        return this.jobPosting.getId();
    }

    public String getTitle(){
        return this.jobPosting.getTitle();
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }
}
