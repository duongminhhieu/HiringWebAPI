//package com.setqt.Hiring.Model;
//
//import java.io.Serializable;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "Candidate")
//public class Candidate implements Serializable{
//	@Id
////	@GeneratedValue(strategy = GenerationType.AUTO)
////	private Long id;
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "User_Role", joinColumns = { @JoinColumn(name = "user_Id") }, inverseJoinColumns = {
//			@JoinColumn(name = "role_Id") })
//	String 
//	
//}
