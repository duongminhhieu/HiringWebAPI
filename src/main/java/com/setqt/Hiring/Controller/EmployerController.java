package com.setqt.Hiring.Controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.setqt.Hiring.DTO.JobPostingDTO;
import com.setqt.Hiring.Model.CV;
import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.Employer;
import com.setqt.Hiring.Model.JobDescription;
import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.UserService;
import com.setqt.Hiring.Service.Company.CompanyService;
import com.setqt.Hiring.Service.Employer.EmployerService;
import com.setqt.Hiring.Service.JobDescription.JobDescriptionService;
import com.setqt.Hiring.Service.JobPosting.JobPostingService;

@RestController
@RequestMapping("/employer")
public class EmployerController {
	@Autowired
	EmployerService employerService;
	@Autowired
	CompanyService companyService;
	@Autowired
	UserService uService;
	@Autowired
	JobPostingService jobService;

	@Autowired
	JobDescriptionService JDesciptService;

	@Autowired
	JwtTokenHelper jwtHelper;

	@PostMapping("/addJobPosting")
	public ResponseEntity<ResponseObject> addPosting(@RequestBody JobPostingDTO jobPostingDTO,
			@RequestHeader(value = "Authorization") String jwt) {

		jwt = jwt.substring(7, jwt.length());

		String username = jwtHelper.getUsernameFromToken(jwt);
		User user = (User) uService.findOneByUsername(username);

		try {

			JobDescription jobDescription = new JobDescription(jobPostingDTO.getDescription(),
					jobPostingDTO.getBenefits(), jobPostingDTO.getRequirement(), jobPostingDTO.getGender(),
					jobPostingDTO.getExperience(), jobPostingDTO.getSalary(), jobPostingDTO.getNumber_candidates(),
					jobPostingDTO.getWorking_form(), jobPostingDTO.getAddress_work());
			Employer em = user.getEmployer();

			JobPosting jobPosting = new JobPosting();

			jobPosting.setTitle(jobPostingDTO.getTitle());
			jobPosting.setDueDate(jobPostingDTO.getDueDate());
			jobPosting.setPostDate(jobPostingDTO.getPostDate());
			jobPosting.setView(0);

			Company com = em.getCompany();
			System.out.println(com.getName());
			jobPosting.setCompany(com);
			jobPosting.setJobDescription(jobDescription);

			jobService.save(jobPosting);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", "Thêm việc thành công ", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Thêm việc không thành công !!!!", null));

		}

	}

	@GetMapping("/getCV")
	public ResponseEntity<ResponseObject> getCV(@RequestParam String id) {
		try {

			Optional<JobPosting> jobPosting = jobService.findById(Long.parseLong(id));
			if (jobPosting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ResponseObject("failed", "Không tìm thấy việc !!!!", null));
			}

			JobPosting getJob = jobPosting.get();
			Set<CV> cv = getJob.getListCV();
			if (cv.isEmpty())
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ResponseObject("failed", "Chưa có cv nào !!!!", null));
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", "Tìm tất cả cv thành công !", cv));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Lỗi server !!!!", null));
		}

	}

}
