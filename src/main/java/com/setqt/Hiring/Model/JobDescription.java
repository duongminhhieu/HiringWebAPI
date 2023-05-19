package com.setqt.Hiring.Model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="JobDescription")
public class JobDescription implements Serializable {
	
	@Id
	@OneToOne
	@JoinColumn(name="JobPosting_id")
	private JobPosting job;
	private String desciption;
	private String benefit;
	private String requirement;
	private String gender;
	private String experience;
	private String salary;
	public JobPosting getJob() {
		return job;
	}
	public void setJob(JobPosting job) {
		this.job = job;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTypeWorking() {
		return typeWorking;
	}
	public void setTypeWorking(String typeWorking) {
		this.typeWorking = typeWorking;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public JobDescription(JobPosting job, String desciption, String benefit, String requirement, String gender,
			String experience, String salary, int number, String typeWorking, String address) {
		super();
		this.job = job;
		this.desciption = desciption;
		this.benefit = benefit;
		this.requirement = requirement;
		this.gender = gender;
		this.experience = experience;
		this.salary = salary;
		this.number = number;
		this.typeWorking = typeWorking;
		this.address = address;
	}
	private int number;
	private String typeWorking;
	private String address;
	
	
}
