package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "Company")
public class Company implements Serializable{

	private static final long serialVersionUID = -297553281792804396L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String taxCode;
	private String address;
	private String domain;
	private String logo;
	private String companySize;
	private String 	workTime;
	private String description;
	private Double rate;


	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	@JsonManagedReference(value="job_company")
	private List<JobPosting> jobPostingList;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	@JsonManagedReference(value="rating_company")
	private List<RatingCompany> ratingCompanies;

	@OneToOne (cascade = CascadeType.ALL)
	@JsonBackReference(value="company_employer")
	private Employer employer;


	public Company(String name, Long id, String taxCode, String address, String domain) {
		super();
		this.name = name;
		this.id = id;
		this.taxCode = taxCode;
		this.address = address;
		this.domain = domain;
	}
	public Company() {

	}

	public void updateRating(){
		double t = 0 ;
		for(RatingCompany a : this.ratingCompanies){
			t += a.getRate();
		}
		this.rate = t/ this.ratingCompanies.size();
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@JsonBackReference
	public List<JobPosting> getJobPostingList() {
		return jobPostingList;
	}

	@JsonBackReference
	public void setJobPostingList(List<JobPosting> jobPostingList) {
		this.jobPostingList = jobPostingList;
	}

	@JsonBackReference
	public Employer getEmployer() {
		return employer;
	}

	@JsonBackReference
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
}
