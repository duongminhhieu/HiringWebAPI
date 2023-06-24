package com.setqt.Hiring.Controller;

import com.setqt.Hiring.DTO.CandidateDTO;
import com.setqt.Hiring.DTO.RatingDTO;
import com.setqt.Hiring.DTO.ReportDTO;
import com.setqt.Hiring.DTO.SubmitCVDTO;
import com.setqt.Hiring.Model.*;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.CV.CVService;
import com.setqt.Hiring.Service.Candidate.CandidateService;
import com.setqt.Hiring.Service.Company.CompanyService;
import com.setqt.Hiring.Service.Firebase.FirebaseDocumentFileService;
import com.setqt.Hiring.Service.Firebase.FirebaseImageService;
import com.setqt.Hiring.Service.JobPosting.JobPostingService;
import com.setqt.Hiring.Service.RatingCompany.RatingCompanyService;
import com.setqt.Hiring.Service.Report.ReportService;
import com.setqt.Hiring.Service.SavedJobPosting.SavedJobPostingService;
import com.setqt.Hiring.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/candidate")
@CrossOrigin(origins = "*", allowedHeaders = {"Content-Type", "Authorization"})
public class CandidateController {
    @Autowired
    JwtTokenHelper jwtHelper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JobPostingService jobPostingService;
    @Autowired
    ReportService reportService;
    @Autowired
    UserService uService;
    @Autowired
    private FirebaseImageService firebaseImageService;
    @Autowired
    private FirebaseDocumentFileService firebaseDocumentFileService;
    @Autowired
    CandidateService candidateService;
    @Autowired
    CompanyService companyService;
    @Autowired
    RatingCompanyService ratingCompanyService;
    @Autowired
    SavedJobPostingService savedJobPostingService;
    @Autowired
    CVService cvService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAllCandidate(@RequestHeader(value = "Authorization") String jwt) {
        try {
            List<Candidate> result = candidateService.findAll();
            System.out.println(result.size());
            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "not found data", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/myInfo")
    public ResponseEntity<ResponseObject> getMyInfo(@RequestHeader(value = "Authorization") String jwt) {
        try {
            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();
            if (candidate == null)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "not found data", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", candidate));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("failed", "not found data", null));
        }
    }
    @GetMapping("/myInfoNew")
    public ResponseEntity<ResponseObject> getMyInfoNew(@RequestHeader(value = "Authorization") String jwt) {
    	try {
    		jwt = jwt.substring(7, jwt.length());
    		
    		String username = jwtHelper.getUsernameFromToken(jwt);
    		
//    		User user = (User) uService.findOneByUsername(username);
    		Candidate candidate = candidateService.getInfo(username);
    		if (candidate == null)
    			return ResponseEntity.status(HttpStatus.OK)
    					.body(new ResponseObject("failed", "not found data", null));
    		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", candidate));
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.OK)
    				.body(new ResponseObject("failed", "not found data", null));
    	}
    }
    @GetMapping("/JobSummited")
    public ResponseEntity<ResponseObject> getJobSummited(@RequestHeader(value = "Authorization") String jwt) {
    	try {
    		jwt = jwt.substring(7, jwt.length());
    		
    		String username = jwtHelper.getUsernameFromToken(jwt);
    		

    		List<CV> listcv= cvService.getCVByUsername(username);
    		if (listcv.size() == 0)
    			return ResponseEntity.status(HttpStatus.OK)
    					.body(new ResponseObject("failed", "not found data", null));
    		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", listcv));
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.OK)
    				.body(new ResponseObject("failed", "not found data", null));
    	}
    }

    @PostMapping(value = "/updateInfoCandidate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> updateCandidate(@RequestParam(value = "file", required = false) MultipartFile file,
                                                          @RequestParam("fullName") String fullName,
                                                          @RequestParam("gender") String gender,
                                                          @RequestParam("phone") String phone,
                                                          @RequestParam("address") String address,
                                                          @RequestParam("dob") String dob,
                                                          @RequestParam("skill") String[] skill,
                                                          @RequestParam("experience") String experience,
                                                          @RequestHeader(value = "Authorization") String jwt) {
        try {
            //System.out.println(candidateDTO.toString());
            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();


            if(file != null){
                // xu li file
                firebaseImageService = new FirebaseImageService();
                // save file to Firebase
                String fileName = firebaseImageService.save(file,
                        "avatars_candidate/" + candidate.getId() + "_" + candidate.getEmail());
                String imageUrl = firebaseImageService.getFileUrl(fileName);
                System.out.println((imageUrl));
                candidate.setAvatar(imageUrl);
            }
            if(fullName != null && !fullName.equals("null")) candidate.setFullName(fullName);
            if(gender != null && !gender.equals("null")) candidate.setGender(gender);
            if(phone != null && !phone.equals("null")) candidate.setPhone(phone);
            if(address != null && !address.equals("null")) candidate.setAddress(address);
            if(experience != null && !experience.equals("null")) candidate.setExperience(experience);
            if(skill != null) candidate.setSkill(skill);
            System.out.println(dob);
            if(dob != null && !dob.equals("null")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(dob);
                candidate.setDob(date);
            }
            Candidate result = candidateService.save(candidate);

            if (result == null)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "update info candidate failed", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Cập nhật thành công", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("failed", "Lỗi Server !....", null));
        }
    }

    @PostMapping("/addReport/{idPosting}")
    public ResponseEntity<ResponseObject> addReport(@PathVariable String idPosting, @RequestBody ReportDTO reportDTO,
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
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "add Report failed", null));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "add Report successfully", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("failed", "Lỗi server ! ....", null));
        }
    }

    @PostMapping("/rating/{idCompany}")
    public ResponseEntity<ResponseObject> addRating(@PathVariable String idCompany, @RequestBody RatingDTO ratingDTO,
                                                    @RequestHeader(value = "Authorization") String jwt) {
        try {

            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();

            Optional<Company> company = companyService.findById(Long.parseLong(idCompany));

            List<RatingCompany> ratingCompanyList = ratingCompanyService.findAll();

            // check exists rating
            for (RatingCompany a : ratingCompanyList) {
                if (Objects.equals(a.getCandidate().getId(), candidate.getId())
                        && Objects.equals(a.getCompany().getId(), company.get().getId())) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseObject("failed", "candidate has been rating", null));
                }
            }

            RatingCompany ratingCompany = new RatingCompany(ratingDTO.getRate(), ratingDTO.getContent(), new Date(), company.get(), candidate);
            RatingCompany result = ratingCompanyService.save(ratingCompany);
            // update rating
            company.get().updateRating();
            companyService.save(company.get());

            if (result == null)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "add Rating failed", null));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "add Rating successfully", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("failed", "Lỗi server !....", null));
        }
    }

    @PostMapping("/saveJobPosting/{idPosting}")
    public ResponseEntity<ResponseObject> saveJobPosting(@PathVariable String idPosting,
                                                         @RequestHeader(value = "Authorization") String jwt) {
        try {

            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();

            List<SavedJobPosting> savedJobPostingList = savedJobPostingService.findAll();

            for (SavedJobPosting savedJobPosting : savedJobPostingList) {
                if (savedJobPosting.getJobPosting().getId() == Long.parseLong(idPosting)
                        && Objects.equals(savedJobPosting.getCandidate().getId(), candidate.getId())) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseObject("ok", "This job posting has been saved", null));
                }
            }

            Optional<JobPosting> jobPosting = jobPostingService.findById(Long.parseLong(idPosting));
            SavedJobPosting savedJobPosting = new SavedJobPosting(candidate, jobPosting.get());
            SavedJobPosting result = savedJobPostingService.save(savedJobPosting);

            if (result == null)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "save Job posting failed", null));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "save job posting successfully", result));

        } catch (Exception e) {
            e.printStackTrace(); return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("failed", "Lỗi server !....", null));

        }
    }
    @GetMapping("/getJobSaved")
    public ResponseEntity<ResponseObject> getJobPosting(
    		@RequestHeader(value = "Authorization") String jwt) {
    	try {
    		
    		jwt = jwt.substring(7, jwt.length());
    		
    		String username = jwtHelper.getUsernameFromToken(jwt);
    		System.out.println(username);
    		User user = (User) uService.findOneByUsername(username);
    		Candidate candidate = user.getCandidate();
    	
    		List<SavedJobPosting> svJob= savedJobPostingService.getByCandidateID(candidate.getId());
    		
    		if (svJob.size()==0)
    		return ResponseEntity.status(HttpStatus.OK)
    				.body(new ResponseObject("ok", "Chưa lưu công việc nào", svJob));
    		else 
    			return ResponseEntity.status(HttpStatus.OK)
        				.body(new ResponseObject("ok", "DS  công việc đã lưu", svJob));
        	
    	} catch (Exception e) {
    		e.printStackTrace(); return ResponseEntity.status(HttpStatus.OK)
    				.body(new ResponseObject("failed", "Lỗi server !....", null));
    		
    	}
    }

    @PostMapping("/deleteJobPosting/{idSaved}")
    public ResponseEntity<ResponseObject> deleteJobPosting(@PathVariable String idSaved,
                                                           @RequestHeader(value = "Authorization") String jwt) {
        try {

            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();

            savedJobPostingService.delete(Long.parseLong(idSaved));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "delete job posting successfully", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "delete Job posting failed", null));
        }
    }


    @PostMapping(value = "/submitCV/{idPosting}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> submitCV(@PathVariable String idPosting,
                                                   @RequestParam("file") MultipartFile file, @RequestParam("introLetter") String introLetter, @RequestParam("name") String name,
                                                   @RequestHeader(value = "Authorization") String jwt) {
        try {
            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);
            Candidate candidate = user.getCandidate();
            Optional<JobPosting> jobPosting = jobPostingService.findById(Long.parseLong(idPosting));

            // xu li file
            firebaseDocumentFileService = new FirebaseDocumentFileService();
            // save file to Firebase
            String fileName = firebaseDocumentFileService.save(file,
                    candidate.getId() + "_" + candidate.getEmail() + "_" + idPosting);
            String url = firebaseDocumentFileService.getFileUrl(fileName);

            System.out.println((url));
            for(CV cv : jobPosting.get().getListCV()){
                if(Objects.equals(cv.getCandidate().getId(), candidate.getId())){
                    cv.setName(name);
                    cv.setIntroLetter(introLetter);
                    cv.setFileCV(url);
                    cv.setDateCreated(new Date());
                    cv.setStatus("consider");
                    CV result = cvService.save(cv);
                    if (result == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseObject("failed", "submit for Job posting failed!", null));

                    }
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseObject("ok", "CV bạn đã được ghi đè !", result));
                }
            }

            CV cv = new CV();
            cv.setCandidate(candidate);
            cv.setName(name);
            cv.setIntroLetter(introLetter);
            cv.setFileCV(url);
            cv.setDateCreated(new Date());
            cv.setJobPosting(jobPosting.get());
              cv.setStatus("consider");
            CV result = cvService.save(cv);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "submit for Job posting failed", null));

            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Nộp CV thành công !", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("failed", "Lỗi server ....", null));
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseObject> changePassword(@RequestHeader(value = "Authorization") String jwt,
                                                         @RequestParam("password") String password,
                                                         @RequestParam("newPassword") String newPassword) {
        try {

            jwt = jwt.substring(7, jwt.length());

            String username = jwtHelper.getUsernameFromToken(jwt);
            System.out.println(username);
            User user = (User) uService.findOneByUsername(username);

            boolean check = passwordEncoder.matches(password, user.getPassword());

            if(!check){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("failed", "Mật khẩu cũ đã nhập không đúng !", null));
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            User result = uService.save(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Đổi mật khẩu thành công !", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("failed", "Lỗi server!...", null));
        }
    }

}
