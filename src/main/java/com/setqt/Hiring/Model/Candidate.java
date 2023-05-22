package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.setqt.Hiring.Security.Model.User;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
//@IdClass(CandidatePK.class)
@Table(name = "Candidate")
public class Candidate implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	@JsonBackReference(value="candidate_user")
	private User user;

	@OneToMany(mappedBy = "candidate")
	@JsonManagedReference(value="report_candidate")
	private List<Report> reports;


	private String fullName;
	
	private String email;
	private String gender;
	private String phone;
	private String address;
	private Date dob;
	private String avatar;
	private String[] skill;
	private String experience;
	public Candidate(User user, String fullName, String email, String gender, String phone, String address, Date dob,
			String avatar, String[] skill, String experience) {
		super();
		this.user = user;
		this.fullName = fullName;
		this.email = email;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.skill = skill;
		this.experience = experience;
	}

	public Candidate() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
