package com.setqt.Hiring.Controller;

import com.setqt.Hiring.DTO.CandidateAuthedDTO;
import com.setqt.Hiring.DTO.CandidateDTO;
import com.setqt.Hiring.Model.Candidate;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.Candidate.CandidateService;
import com.setqt.Hiring.Service.Firebase.FirebaseImageService;
import com.setqt.Hiring.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    JwtTokenHelper jwtHelper;

    @Autowired
    UserService uService;
    @Autowired
    private FirebaseImageService firebaseImageService;
    @Autowired
    CandidateService candidateService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAllCandidate() {
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

}
