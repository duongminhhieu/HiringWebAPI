package com.setqt.Hiring.Service.JobPosting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Repository.JobPostingRepository;
import com.setqt.Hiring.Service.Generic.GenericService;

@Service
public class JobPostingService extends GenericService<JobPosting> implements IJobPosting {

	public JobPostingService(JpaRepository<JobPosting, Long> genericRepository) {
		super(genericRepository);
	}

}
