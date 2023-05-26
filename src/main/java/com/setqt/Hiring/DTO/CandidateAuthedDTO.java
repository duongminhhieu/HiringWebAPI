package com.setqt.Hiring.DTO;

import java.io.Serializable;
import java.util.Date;

public class CandidateAuthedDTO implements Serializable {

	private String fullName;
	private String email;
	private String password;

	public CandidateAuthedDTO(String fullname, String email, String password) {
		super();
		this.fullName = fullname;
		this.email = email;
		this.password = password;
	}


	 public String getFullname() {
		return fullName;
	}
	public void setFullname(String fullname) {
		this.fullName = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
