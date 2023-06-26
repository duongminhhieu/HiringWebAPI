package com.setqt.Hiring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.CV;
import com.setqt.Hiring.Model.Candidate;
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {


	@Query("SELECT new com.setqt.Hiring.Model.Candidate(c.fullName, c.email, c.gender, c.phone, c.address,"
			+ " c.dob , c.avatar, c.skill, c.experience)"
			+ " FROM Candidate c JOIN  c.user WHERE user.username = :username")
	
    public Candidate getInfoCandidateById(@Param("username") String username);
	
	
}
