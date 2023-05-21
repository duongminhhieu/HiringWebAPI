package com.setqt.Hiring.Service.Candidate;


import com.setqt.Hiring.Model.Candidate;

import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class CandidateService extends GenericService<Candidate> implements ICandidate {
    public CandidateService(JpaRepository<Candidate, Long> genericRepository) {
        super(genericRepository);
    }
}
