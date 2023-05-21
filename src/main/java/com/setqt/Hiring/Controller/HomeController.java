package com.setqt.Hiring.Controller;

import java.util.List;

import com.setqt.Hiring.Model.Candidate;
import com.setqt.Hiring.Service.Candidate.CandidateService;
import com.setqt.Hiring.Service.Company.CompanyService;
import com.setqt.Hiring.Service.Employer.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.Employer;
import com.setqt.Hiring.Model.ResponseObject;


@RestController
@RequestMapping("/company")
@CrossOrigin(origins="*")
public class HomeController {

    @Autowired
    private CompanyService comService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private CandidateService candidateService;
//    @Autowired
//    private JwtTokenHelper jwtHelper;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> test() {
        try {
            System.out.println(1);
            List<Company> result = comService.findAll();
            System.out.println(result.size());
            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "not found data", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/getCompany")
    public ResponseEntity<List<Company>> getCompany() {
        try {
            List<Company> companies = this.comService.findAll();
            System.out.println(companies.size());
            System.out.println("job postirng: " + companies.get(0).getJobPostingList().size());
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getEmployer")
    public ResponseEntity<List<Employer>> getEmployer() {
        try {
            List<Employer> employers = this.employerService.findAll();

            return new ResponseEntity<>(employers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getCandidate")
    public List<Candidate> getCandidate() throws Exception {
        return this.candidateService.findAll();
    }

    @GetMapping("/test")
    public ResponseEntity<ResponseObject> tests() throws Exception {
        List<Employer> result = employerService.findAll();
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "not found data", null));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));
    }

//    @GetMapping("/add")
//    public ResponseEntity<ResponseObject> addCompany(@RequestHeader(value = "Authorization") String jwt) {
//        jwt = jwt.substring(7, jwt.length());
//
//        String user = jwtHelper.getUsernameFromToken(jwt);
//        System.out.println(user);
//
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", null));
//    }
}
