package com.setqt.Hiring.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.CV;
import com.setqt.Hiring.Model.Candidate;
import com.setqt.Hiring.Model.JobPosting;

@Repository
public interface CvRepository extends JpaRepository<CV, Long> {

	@Query("SELECT new com.setqt.Hiring.Model.CV("
			+ "cv.candidate, cv.jobPosting,cv.introLetter,cv.fileCV, cv.dateCreated,p.company) "
			+ "FROM CV cv JOIN cv.jobPosting p  "
			+ "WHERE cv.candidate.user.username = :username")

	public List<CV> getCvByUsername(@Param("username") String username);

}
