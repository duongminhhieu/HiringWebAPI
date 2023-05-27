package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.setqt.Hiring.DTO.CandidateDTO;
import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
@Table(name = "CurriculumVitae")
public class CV implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    @JsonBackReference(value="cv")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", referencedColumnName = "id")
    @JsonBackReference(value="job_posting")
    private JobPosting jobPosting;

    private String name;
    @Column(columnDefinition="TEXT")
    private String introLetter;
    @Column(columnDefinition="TEXT")
    private String fileCV;
    private Date dateCreated;


    public CV() {

    }

    public CV(Candidate candidate, JobPosting jobPosting, String introLetter, String fileCV, Date dateCreated) {
        this.candidate = candidate;
        this.jobPosting = jobPosting;
        this.introLetter = introLetter;
        this.fileCV = fileCV;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Long getIdCandidate(){
        return this.candidate.getId();
    }
    public CandidateDTO getInfoCandidate(){
        CandidateDTO candidateDTO = new CandidateDTO(this.candidate.getFullName(), this.candidate.getGender(),this.candidate.getPhone(), this.candidate.getAddress(), this.candidate.getDob(), this.candidate.getSkill(), this.candidate.getExperience());
        return candidateDTO;
    }
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //	@JsonBackReference(value="job_cv")
    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public String getIntroLetter() {
        return introLetter;
    }

    public void setIntroLetter(String introLetter) {
        this.introLetter = introLetter;
    }

    public String getFileCV() {
        return fileCV;
    }

    public void setFileCV(String fileCV) {
        this.fileCV = fileCV;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
