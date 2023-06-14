
package com.setqt.Hiring.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.CV;
import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.Employer;
import com.setqt.Hiring.Model.JobDescription;
import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Model.Report;
import com.setqt.Hiring.Model.SavedJobPosting;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
	
	@Query("SELECT NEW com.setqt.Hiring.Model.JobPosting(jp.status,jp.id,jp.jobDescription,jp.title, jp.postDate,jp.dueDate, jp.view)"
			+ " FROM JobPosting jp WHERE jp.company.employer.user.username = :username")
    List<JobPosting> findJobPostingsByUsername(@Param("username") String username);
	
	
	@Query("SELECT NEW com.setqt.Hiring.Model.JobPosting(jp.status,jp.id,jp.jobDescription,jp.title, jp.postDate,jp.dueDate, jp.view)"
			+ " FROM JobPosting jp JOIN Employer em ON jp.company.id = em.company.id WHERE em.id = :id")
	List<JobPosting> findJobPostingsById(@Param("id") Long id);

	
	@Query("SELECT new com.setqt.Hiring.Model.Employer(e.id,e.user,e.phone, e.email, e.logo) FROM Employer e JOIN e.user u WHERE u.username = :username")
	Employer findEmployerByUsername(@Param("username") String username);
}
