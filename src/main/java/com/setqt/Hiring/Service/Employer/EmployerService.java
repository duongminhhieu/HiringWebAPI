package com.setqt.Hiring.Service.Employer;

import com.setqt.Hiring.Service.Generic.GenericService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.Employer;


@Service
public class EmployerService extends GenericService<Employer> implements IEmployer {

	
	public EmployerService(JpaRepository<Employer, Long> genericRepository) {
		super(genericRepository);
	}
}
