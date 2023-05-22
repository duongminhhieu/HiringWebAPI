
package com.setqt.Hiring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{
	
	@Query("""
	SELECT j
	FROM  JobPosting j
	where j.title like '%?1%' """)
	public List<JobPosting> searchByTitle(String title);
}
