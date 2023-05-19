package com.setqt.Hiring.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="JobPosting")
public class JobPosting implements Serializable {
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	
	@ManyToMany(mappedBy="job")
	private Set<CV> listCV = new HashSet<>();
	
	private String title;
	private Date postDate;
	private Date dueDate;
	private int view;
	
	
}
