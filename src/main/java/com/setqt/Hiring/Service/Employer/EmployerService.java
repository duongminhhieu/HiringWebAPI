package com.setqt.Hiring.Service.Employer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.Employer;
import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Repository.EmployerRepository;
import com.setqt.Hiring.Service.Generic.GenericService;


@Service
public class EmployerService extends GenericService<Employer> implements IEmployer {

	
	public EmployerService(JpaRepository<Employer, Long> genericRepository) {
		super(genericRepository);
	}
	public List<JobPosting> getAllJob(String username){
		
		return ((EmployerRepository) genericRepository).findJobPostingsByUsername(username);
	}
	public List<JobPosting> getAllJobByID(Long id){
		
		return ((EmployerRepository) genericRepository).findJobPostingsById(id);
	}
	public Employer getInfo(String username){
		
		return ((EmployerRepository) genericRepository).findEmployerByUsername(username);
	}

}
