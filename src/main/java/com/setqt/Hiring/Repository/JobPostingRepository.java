
package com.setqt.Hiring.Repository;

import java.util.List;

import com.setqt.Hiring.DTO.APIResponse.SearchResponse;
import com.setqt.Hiring.Model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    //	@Query("""
//	SELECT j
//	FROM  JobPosting j, JobDesciption 
//	where j.title like '%?1%' and j.""")
//	public List<JobPosting> searchByTitle(String title,String add);
//	
//	@Query("SELECT jp FROM JobPosting jp JOIN FETCH jp.jobDescription jd WHERE jp.title  ILIKE %?1% AND jd.address_work ILIKE %?2%")
//    List<JobPosting> findJobPostingWithDescription(String title, String address);
    String sql = "SELECT new com.setqt.Hiring.DTO.APIResponse.SearchResponse(job_posting.id AS id_jobPosting, job_posting.title, " +
            "job_posting.postDate, company.id AS id_company, company.name, " +
            "company.logo, job_description.salary, job_description.address_work, " +
            "job_description.experience) " +
            "FROM JobPosting job_posting " +
            "JOIN Company company ON job_posting.company = company.id " +
            "JOIN JobDescription job_description ON job_description.id = job_posting.jobDescription " +
            "WHERE job_posting.title ILIKE  %?1% " +
            "AND job_description.address_work ILIKE %?2%";
    @Query(sql)
    List<SearchResponse> searchJobForCompany(String title, String address);

//	 AND 
}
