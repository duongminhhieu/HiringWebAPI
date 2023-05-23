package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Entity
//@Data
@ToString
@Table(name="JobPosting")
public class JobPosting implements Serializable {
	private static final long serialVersionUID = -297553221792804396L;

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	
	
	@ManyToOne
	@JoinColumn(name="company_id", nullable = false, referencedColumnName = "id")
//	@JoinColumn(name="company_id", nullable = false, referencedColumnName = "id")

	@JsonBackReference(value="job_company")
	private Company company;
	
	
	@ManyToMany(mappedBy="job")
	
//	@JsonManagedReference(value="job_cv")
	@JsonIgnore
	private Set<CV> listCV = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "job_description_id")
	@JsonManagedReference(value="job_descript")
	private JobDescription jobDescription;

	@OneToMany(mappedBy = "jobPosting")
	@JsonManagedReference(value="job_report")
	private List<Report> reports;

	private String title;
	private Date postDate;
	private Date dueDate;
	private int view;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}
	public Long getCompanyID() {
		return company.getId();
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<CV> getListCV() {
		return listCV;
	}

	public void setListCV(Set<CV> listCV) {
		this.listCV = listCV;
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
	public JobPosting(Long id, Company company, Set<CV> listCV, JobDescription jobDescription, List<Report> reports,
			String title, Date postDate, Date dueDate, int view) {
		super();
		this.id = id;
		this.company = company;
		this.listCV = listCV;
		this.jobDescription = jobDescription;
		this.reports = reports;
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
