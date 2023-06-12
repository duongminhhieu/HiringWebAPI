package com.setqt.Hiring.Service.Candidate;


import com.setqt.Hiring.Model.Candidate;
import com.setqt.Hiring.Repository.CandidateRepository;
import com.setqt.Hiring.Service.Generic.GenericService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class CandidateService extends GenericService<Candidate> implements ICandidate {
	
	@Autowired
    public CandidateService(JpaRepository<Candidate, Long> genericRepository) {
        super(genericRepository);
    }
    public Candidate getInfo(String name) {
//    	Candidate result =  ((CandidateRepository) genericRepository).getInfoCandidateById(id);
    	Candidate result =   ((CandidateRepository) genericRepository).getInfoCandidateById(name);
    	return result;
    }
}
