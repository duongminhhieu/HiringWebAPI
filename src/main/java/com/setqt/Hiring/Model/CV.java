package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CurriculumVitae")
public class CV implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cv_id;
	
	@OneToOne
	@JoinColumn(name="candidate_id")
	private Candidate candidate;
	@ManyToMany(cascade ={ CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.LAZY)
	@JoinTable(
	        name = "job_cv",
	        joinColumns = @JoinColumn(name = "cv_id"),
	        inverseJoinColumns = @JoinColumn(name = "job_id")
	    )
	private Set<JobPosting> job = new HashSet<>();
	public CV(Long id, Candidate candidate, Set<JobPosting>  job, String introLetter, String fileCV, Date dateCreated) {
		super();
		this.cv_id = id;
		this.candidate = candidate;
		this.job = job;
		this.introLetter = introLetter;
		this.fileCV = fileCV;
		this.dateCreated = dateCreated;
	}
	public Long getId() {
		return cv_id;
	}
	public void setId(Long id) {
		this.cv_id = id;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public Set<JobPosting>  getJob() {
		return job;
	}
	public void setJob(Set<JobPosting>  job) {
		this.job = job;
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
	private String introLetter;
	private String fileCV;
	private Date dateCreated;
	
	
}
