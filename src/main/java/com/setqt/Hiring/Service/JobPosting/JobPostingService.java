package com.setqt.Hiring.Service.JobPosting;

import java.util.List;

import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Repository.JobPostingRepository;

@Service
public class JobPostingService extends GenericService<JobPosting> implements IJobPosting {

	@Autowired
	public JobPostingService(JpaRepository<JobPosting, Long> genericRepository) {
		super(genericRepository);
	
	}

	public List<JobPosting> findJobPostingWithDescription(String title,String add) {
		// TODO Auto-generated method stub
		return ((JobPostingRepository) genericRepository).findJobPostingWithDescription(title,add);
	}

}
