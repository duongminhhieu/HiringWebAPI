package com.setqt.Hiring.Controller;

import com.setqt.Hiring.DTO.JobPostingDTO;
import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.JobDescription;
import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Service.Company.CompanyService;
import com.setqt.Hiring.Service.Employer.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    @Autowired
    EmployerService employerService;
    @Autowired
    CompanyService companyService;

    @PostMapping("/addJobPosting")
    public ResponseEntity<ResponseObject> addPosting(@RequestBody JobPostingDTO jobPostingDTO) {
        try {

            System.out.println(jobPostingDTO.toString());
            JobDescription jobDescription = new JobDescription(jobPostingDTO.getDescription(), jobPostingDTO.getBenefits(), jobPostingDTO.getRequirement(), jobPostingDTO.getGender(), jobPostingDTO.getExperience(), jobPostingDTO.getSalary(), jobPostingDTO.getNumber_candidates(), jobPostingDTO.getWorking_form(), jobPostingDTO.getAddress_work());
            Company company = new Company();
            JobPosting jobPosting = new JobPosting();

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
