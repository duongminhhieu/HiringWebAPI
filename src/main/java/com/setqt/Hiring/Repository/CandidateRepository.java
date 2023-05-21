package com.setqt.Hiring.Repository;

import org.springframework.stereotype.Repository;
import com.setqt.Hiring.Model.Candidate;
import com.setqt.Hiring.Model.CandidatePK;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	
}
