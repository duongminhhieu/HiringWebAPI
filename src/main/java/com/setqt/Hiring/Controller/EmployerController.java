package com.setqt.Hiring.Controller;

import com.setqt.Hiring.DTO.JobPostingDTO;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    	System.out.println(jobPostingDTO.toString());
    	   jwt = jwt.substring(7, jwt.length());
    	   
           String username = jwtHelper.getUsernameFromToken(jwt);
           System.out.println(username);
           User user = (User) uService.findOneByUsername(username);
           
        try {
        	
            JobDescription jobDescription = new JobDescription(jobPostingDTO.getDescription(), jobPostingDTO.getBenefits(), jobPostingDTO.getRequirement(), jobPostingDTO.getGender(), jobPostingDTO.getExperience(), jobPostingDTO.getSalary(), jobPostingDTO.getNumber_candidates(), jobPostingDTO.getWorking_form(), jobPostingDTO.getAddress_work());
            Employer em = user.getEmployer();
          
            JobPosting jobPosting = new JobPosting();
//            jobPosting.setJobDescription(jobDescription);
            jobPosting.setTitle(jobPostingDTO.getTitle());
            jobPosting.setDueDate(jobPostingDTO.getDueDate());
            jobPosting.setPostDate(jobPostingDTO.getPostDate());
            jobPosting.setView(0);
            
//            jobService.save(jobPosting);
            Company com =em.getCompany();
            System.out.println(com.getName());
            jobPosting.setCompany(com );
            JDesciptService.save(jobDescription);
            jobDescription.setJobPosting(jobPosting);
//            JDesciptService.save(jobDescription);
            jobService.save(jobPosting);
            
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "not found data", null));
//            if (result.isEmpty())
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ResponseObject("failed", "not found data", null));
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
