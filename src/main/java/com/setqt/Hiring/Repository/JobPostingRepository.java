
package com.setqt.Hiring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{
	
//	@Query("""
//	SELECT j
//	FROM  JobPosting j, JobDesciption 
//	where j.title like '%?1%' and j.""")
//	public List<JobPosting> searchByTitle(String title,String add);
//	
	@Query("SELECT jp FROM JobPosting jp JOIN FETCH jp.jobDescription jd WHERE jp.title  ILIKE %?1% AND jd.address_work ILIKE %?2%")
    List<JobPosting> findJobPostingWithDescription(String title, String address);
//	 AND 
}
