package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;

import com.setqt.Hiring.Security.Model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table; 

@Entity
//@IdClass(CandidatePK.class)
@Table(name = "Candidate")
public class Candidate implements Serializable{
	
	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User user;
	private String fullName;
	
	private String email;
	private String gender;
	private String phone;
	private String address;
	private Date dob;
	private String avatar;
	private String [] skill;
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
