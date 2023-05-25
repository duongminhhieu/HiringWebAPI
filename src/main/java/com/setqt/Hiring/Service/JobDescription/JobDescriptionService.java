package com.setqt.Hiring.Service.JobDescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.JobDescription;
import com.setqt.Hiring.Service.Generic.GenericService;
import com.setqt.Hiring.Service.Generic.IGenericService;

@Service
public class JobDescriptionService extends GenericService<JobDescription> implements IJobDescription {

	
	@Autowired
	public JobDescriptionService(JpaRepository<JobDescription, Long> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

}
