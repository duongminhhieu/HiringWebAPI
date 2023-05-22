package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Entity
@Data
@Table(name = "Company")
public class Company implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String taxCode;
	private String address;
	private String domain;


	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	@JsonManagedReference(value="job_company")
	private List<JobPosting> jobPostingList;

	@OneToMany (mappedBy = "company", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Employer> employers;

	private static final long serialVersionUID = -297553281792804396L;
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
	public List<Employer> getEmployers() {
		return employers;
	}

	@JsonBackReference
	public void setEmployers(List<Employer> employers) {
		this.employers = employers;
	}
}
