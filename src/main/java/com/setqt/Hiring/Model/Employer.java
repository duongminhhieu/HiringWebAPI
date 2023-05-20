package com.setqt.Hiring.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.setqt.Hiring.Security.Model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name="employer")
@ToString
public class Employer implements Serializable{
	 public Employer() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "user_id",referencedColumnName = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name="company_id", referencedColumnName = "id")
	@JsonBackReference
	private Company company;


	private String phone;
	private String email;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Employer(User user, Company company, String phone, String email) {
		super();
		this.user = user;
		this.company = company;
		this.phone = phone;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
