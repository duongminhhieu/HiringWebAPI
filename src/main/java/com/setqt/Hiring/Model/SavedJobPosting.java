package com.setqt.Hiring.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.setqt.Hiring.DTO.ResponseDTO.CompanyResponse;
import com.setqt.Hiring.DTO.ResponseDTO.JobPostingResponse;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "saved_job_posting")
public class SavedJobPosting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "candidate_id", nullable = false, referencedColumnName = "id")
	@JsonBackReference(value = "saved_job_posting")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "job_posting_id", nullable = false, referencedColumnName = "id")
	@JsonBackReference(value = "saved_job_posting")
	private JobPosting jobPosting;

	public SavedJobPosting() {
	}

	public SavedJobPosting(Candidate candidate, JobPosting jobPosting) {
		this.candidate = candidate;
		this.jobPosting = jobPosting;
	}

	public Long getId() {
		return id;
	}

	public CompanyResponse getCompanyInfo() {
		Company company = jobPosting.getCompany();
		return new CompanyResponse(company.getId(), company.getName(), company.getTaxCode(), company.getAddress(),
				company.getDomain(), company.getLogo(), company.getCompanySize(), company.getWorkTime(),
				company.getDescription(), company.getRate());

	}

	public void setId(Long id) {
		this.id = id;
	}

	public JobPostingResponse getInfoJobPosting() {
		return new JobPostingResponse(jobPosting.getId(), jobPosting.getTitle(), jobPosting.getPostDate(),
				jobPosting.getDueDate(), jobPosting.getJobDescription().getDescription(),
				jobPosting.getJobDescription().getBenefits(), jobPosting.getJobDescription().getRequirement(),
				jobPosting.getJobDescription().getGender(), jobPosting.getJobDescription().getExperience(),
				jobPosting.getJobDescription().getSalary(), jobPosting.getJobDescription().getNumber_candidates(),
				jobPosting.getJobDescription().getWorking_form(), jobPosting.getJobDescription().getAddress_work());
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public JobPosting getJobPosting() {
		return jobPosting;
	}

	public void setJobPosting(JobPosting jobPosting) {
		this.jobPosting = jobPosting;
	}
}
