package com.setqt.Hiring.Controller;

import java.util.List;
import java.util.Optional;

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
@CrossOrigin(origins = "*")
public class CompanyController {

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

            List<Company> result = comService.findAll();

            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "not found data", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "server failed", null));
        }

    }

    @GetMapping("/getTop")
    public ResponseEntity<ResponseObject> getTopSix() {
        try {

            List<Company> result = comService.findTop6ByRating();
            System.out.println(result.size());
            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "not found data", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "server failed", null));
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCompany(@PathVariable Long id) {
        try {
//              System.out.println(1);
            Optional<Company> result = comService.findById(id);

            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "not found data", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result.get()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "server failed", null));
        }

    }


}