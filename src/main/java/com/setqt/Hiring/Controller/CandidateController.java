package com.setqt.Hiring.Controller;

import com.setqt.Hiring.DTO.CandidateAuthedDTO;
import com.setqt.Hiring.DTO.CandidateDTO;
import com.setqt.Hiring.DTO.RatingDTO;
import com.setqt.Hiring.DTO.ReportDTO;
import com.setqt.Hiring.Model.*;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.Candidate.CandidateService;
import com.setqt.Hiring.Service.Company.CompanyService;
import com.setqt.Hiring.Service.Firebase.FirebaseImageService;
import com.setqt.Hiring.Service.JobPosting.JobPostingService;
import com.setqt.Hiring.Service.RatingCompany.RatingCompanyService;
import com.setqt.Hiring.Service.Report.ReportService;
import com.setqt.Hiring.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    JwtTokenHelper jwtHelper;

    @Autowired
    JobPostingService jobPostingService;
    @Autowired
    ReportService reportService;
    @Autowired
    UserService uService;
    @Autowired
    private FirebaseImageService firebaseImageService;
    @Autowired
    CandidateService candidateService;
    @Autowired
    CompanyService companyService;

    @Autowired
    RatingCompanyService ratingCompanyService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAllCandidate(@RequestHeader(value = "Authorization") String jwt) {
        try {
            List<Candidate> result = candidateService.findAll();
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

    @PutMapping(value = "/updateInfoCandidate", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseObject> addCandidate(@RequestPart("candidate") CandidateDTO candidateDTO,
                                                       @RequestPart("file") MultipartFile file,
                                                       @RequestHeader(value = "Authorization") String jwt) {
        try {
            System.out.println(candidateDTO.toString());
            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();


            // xu li file
            firebaseImageService = new FirebaseImageService();
            // save file to Firebase
            String fileName = firebaseImageService.save(file, "avatars_candidate/" + candidate.getId() + "_" + candidate.getEmail());
            String imageUrl = firebaseImageService.getFileUrl(fileName);

            System.out.println((imageUrl));

            candidate.setFullName(candidateDTO.getFullname());
            candidate.setGender(candidateDTO.getGender());
            candidate.setPhone(candidateDTO.getPhone());
            candidate.setAddress(candidateDTO.getAddress());
            candidate.setAvatar(imageUrl);
            candidate.setExperience(candidateDTO.getExperience());
            candidate.setSkill(candidateDTO.getSkill());
            candidate.setDob(candidateDTO.getDob());

            Candidate result = candidateService.save(candidate);

            if (result == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "update info candidate failed", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/addReport/{idPosting}")
    public ResponseEntity<ResponseObject> addReport(@PathVariable String idPosting,
                                                    @RequestBody ReportDTO reportDTO,
                                                    @RequestHeader(value = "Authorization") String jwt) {
        try {

            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();

            Optional<JobPosting> jobPosting = jobPostingService.findById(Long.parseLong(idPosting));
            Report report = new Report(reportDTO.getContent(), jobPosting.get(), candidate);

            Report result = reportService.save(report);

            if (result == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "add Report failed", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "add Report successfully", result));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/rating/{idCompany}")
    public ResponseEntity<ResponseObject> addReport(@PathVariable String idCompany,
                                                    @RequestBody RatingDTO ratingDTO,
                                                    @RequestHeader(value = "Authorization") String jwt) {
        try {

            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();

            Optional<Company> company = companyService.findById(Long.parseLong(idCompany));

            List<RatingCompany> ratingCompanyList =  ratingCompanyService.findAll();

            // check exists rating
            for(RatingCompany a : ratingCompanyList){
                if(Objects.equals(a.getCandidate().getId(), candidate.getId()) && Objects.equals(a.getCompany().getId(), company.get().getId())){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseObject("failed", "candidate has been rating", null));
                }
            }


            RatingCompany ratingCompany = new RatingCompany(ratingDTO.getRate(), ratingDTO.getContent(), company.get(), candidate);
            RatingCompany result = ratingCompanyService.save(ratingCompany);
            // update rating
            company.get().updateRating();
            companyService.save(company.get());

            if (result == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "add Rating failed", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "add Rating successfully", result));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
