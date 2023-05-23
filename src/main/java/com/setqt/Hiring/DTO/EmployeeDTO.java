package com.setqt.Hiring.DTO;

public class EmployeeDTO {
	
	private String fullname;
	 private String email;
	 private String password;
	 private String address;
	 private String phone;
	 public EmployeeDTO(String fullname, String email, String password, String address, String phone, String web) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.web = web;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	private String web;
	 
	 public String getFullname() {
			return fullname;
		}
		public void setFullname(String fullname) {
			this.fullname = fullname;
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
