package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.setqt.Hiring.DTO.ResponseDTO.CandidateResponse;
import com.setqt.Hiring.DTO.ResponseDTO.JobPostingResponse;
import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
@Table(name = "CurriculumVitae")
public class CV implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    @JsonBackReference(value = "cv")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", referencedColumnName = "id")
    @JsonBackReference(value = "job_posting")
    private JobPosting jobPosting;

    private String name;
    @Column(columnDefinition = "TEXT")
    private String introLetter;
    @Column(columnDefinition = "TEXT")
    private String fileCV;
    private Date dateCreated;

    
    @Transient
    private Company company;

    public CV() {

    }

    public CV(Candidate candidate, JobPosting jobPosting, String introLetter, String fileCV, Date dateCreated) {
        this.candidate = candidate;
        this.jobPosting = jobPosting;
        this.introLetter = introLetter;
        this.fileCV = fileCV;
        this.dateCreated = dateCreated;
    }
    public CV(Candidate candidate, JobPosting jobPosting, String introLetter, String fileCV, Date dateCreated, Company com) {
    	this.candidate = candidate;
    	this.jobPosting = jobPosting;
    	this.introLetter = introLetter;
    	this.company=com;
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

//    public CandidateResponse getInfoCandidate() {
////        return new CandidateResponse(candidate.getId(), candidate.getFullName(), candidate.getGender(), candidate.getPhone(), candidate.getAddress(),candidate.getDob(), candidate.getSkill(), candidate.getExperience());
//    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }


    public JobPostingResponse getInfoJobPosting() {

        return new JobPostingResponse(jobPosting.getId(), jobPosting.getTitle(), jobPosting.getPostDate(), jobPosting.getDueDate(), jobPosting.getJobDescription().getDescription(),
                jobPosting.getJobDescription().getBenefits(), jobPosting.getJobDescription().getRequirement(), jobPosting.getJobDescription().getGender(), jobPosting.getJobDescription().getExperience(), jobPosting.getJobDescription().getSalary(),
                jobPosting.getJobDescription().getNumber_candidates(), jobPosting.getJobDescription().getWorking_form(), jobPosting.getJobDescription().getAddress_work());
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
