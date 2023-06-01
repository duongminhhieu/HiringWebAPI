package com.setqt.Hiring.Repository;

import com.setqt.Hiring.Model.Candidate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	
}
