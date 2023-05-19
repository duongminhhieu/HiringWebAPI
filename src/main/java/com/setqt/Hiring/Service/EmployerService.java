package com.setqt.Hiring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.Employer;


@Service
public class EmployerService extends GeneralService<Employer>{

//	@Autowired
	public EmployerService(JpaRepository<Employer, Long> repo) {
		super(repo);
		// TODO Auto-generated constructor stub
	}

}
