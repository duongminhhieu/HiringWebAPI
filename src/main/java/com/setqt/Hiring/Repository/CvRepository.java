package com.setqt.Hiring.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.DTO.APIResponse.AnalysisData;
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

	
//	@Query("SELECT new com.setqt.Hiring.DTO.APIResponse.AnalysisData("
//			+ "p.view, jd.number_candidates, COUNT(cv),SUM(CASE  WHEN cv.status = 'pass' THEN 1 ELSE 0 END)) "
//			+ "FROM CV cv JOIN FETCH cv.jobPosting p JOIN FETCH p.jobDescription jd"
//			+ " WHERE cv.candidate.user.username = :username")
	@Query("SELECT new com.setqt.Hiring.DTO.APIResponse.AnalysisData("
	        + "SUM(p.view), SUM(jd.number_candidates), COUNT(cv), SUM(CASE WHEN cv.status = 'pass' THEN 1 ELSE 0 END)) "
	        + "FROM CV cv RIGHT JOIN  cv.jobPosting p JOIN  p.jobDescription jd "
	        + "WHERE p.company.employer.user.username = :username")
	AnalysisData getAnalysData(@Param("username") String username);
}
