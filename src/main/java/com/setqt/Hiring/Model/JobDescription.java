package com.setqt.Hiring.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name="JobDescription")
public class JobDescription implements Serializable {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@OneToOne(mappedBy = "jobDescription",cascade=CascadeType.ALL)
	@JsonBackReference(value="job_descript")
	private JobPosting jobPosting;
	private static final long serialVersionUID = -297553111792804396L;

	@Column(columnDefinition="TEXT")
	private String description;
	@Column(columnDefinition="TEXT")
	private String benefits;
	@Column(columnDefinition="TEXT")
	private String requirement;
	private String gender;
	@Column(columnDefinition="TEXT")
	private String experience;
	private String salary;
	private int number_candidates;
	@Column(columnDefinition="TEXT")
	private String working_form;
	@Column(columnDefinition="TEXT")
	private String address_work;

	public JobDescription(JobPosting jobPosting, String description, String benefits, String requirement, String gender, String experience, String salary, int number_candidates, String working_form, String address_work) {
		this.jobPosting = jobPosting;
		this.description = description;
		this.benefits = benefits;
		this.requirement = requirement;
		this.gender = gender;
		this.experience = experience;
		this.salary = salary;
		this.number_candidates = number_candidates;
		this.working_form = working_form;
		this.address_work = address_work;
	}

	public JobDescription(String description, String benefits, String requirement, String gender, String experience, String salary, int number_candidates, String working_form, String address_work) {
		this.description = description;
		this.benefits = benefits;
		this.requirement = requirement;
		this.gender = gender;
		this.experience = experience;
		this.salary = salary;
		this.number_candidates = number_candidates;
		this.working_form = working_form;
		this.address_work = address_work;
	}

	public JobDescription() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBenefits() {
		return benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getNumber_candidates() {
		return number_candidates;
	}

	public void setNumber_candidates(int number_candidates) {
		this.number_candidates = number_candidates;
	}

	public String getWorking_form() {
		return working_form;
	}

	public void setWorking_form(String working_form) {
		this.working_form = working_form;
	}

	public String getAddress_work() {
		return address_work;
	}

	public void setAddress_work(String address_work) {
		this.address_work = address_work;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	@JsonBackReference
	public JobPosting getJobPosting() {
		return jobPosting;
	}

	public void setJobPosting(JobPosting jobPosting) {
		this.jobPosting = jobPosting;
	}

}
