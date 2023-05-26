package com.setqt.Hiring.DTO;

import java.io.Serializable;
import java.util.Date;

public class CandidateDTO implements Serializable {

	private String fullName;
	private String gender;
	private String phone;
	private String address;
	private Date dob;
	private String[] skill;
	private String experience;

	public CandidateDTO(String fullName, String gender, String phone, String address, Date dob, String[] skills, String experience)
	{
		this.fullName = fullName;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
		this.dob = dob;
		this.skill = skills;
		this.experience = experience;
	}

	 public String getFullname() {
		return fullName;
	}
	public void setFullname(String fullname) {
		this.fullName = fullname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String[] getSkill() {
		return skill;
	}

	public void setSkill(String[] skill) {
		this.skill = skill;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}
}
