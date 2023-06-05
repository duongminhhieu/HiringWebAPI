package com.setqt.Hiring.Controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;

import com.setqt.Hiring.DTO.EmployeeAuthedDTO;
import com.setqt.Hiring.Model.*;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Service.EmailService.EmailService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;


import com.setqt.Hiring.DTO.CandidateAuthedDTO;
import com.setqt.Hiring.Security.Model.Role;
import com.setqt.Hiring.Security.Model.RoleRepository;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.UserService;
import com.setqt.Hiring.Service.Candidate.CandidateService;
import com.setqt.Hiring.Service.Company.CompanyService;
import com.setqt.Hiring.Service.Employer.EmployerService;

import ch.qos.logback.classic.Logger;
import client.AuthenRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserService UService;
    @Autowired
    private CompanyService comService;
    @Autowired
    private EmployerService emService;
    @Autowired
    private PasswordEncoder passEncoder;
    Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    JwtTokenHelper jWTTokenHelper;

    @Autowired
    private Environment environment;
    @Autowired
    private EmailService emailService;


    @PostMapping("/login")
    public ResponseEntity<?> login2(@RequestBody AuthenRequest authentRequest)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authentRequest.getUsername(), authentRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jWTTokenHelper.generateToken(authentRequest.getUsername());


            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Đăng nhập thành công",
                            jwt));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Đăng nhập không thành công",
                            ""));
        }


    }

  
    @PostMapping(value = "/signup/candidate", consumes = {"application/json"})
    public ResponseEntity<ResponseObject> createAccountCDD(@RequestBody CandidateAuthedDTO user) {

//		logger.info(user.getUsername());
//		logger.info("-------");
    	System.out.println("ok"+user.getEmail());
        if (user.getEmail().equals("") || user.getPassword().equals(""))
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Đăng kí không thành công", ""));

        Role initRole = roleRepo.findRoleByName("CANDIDATE");
        User newUser = new User(user.getEmail(), user.getPassword(), true, initRole);
        Candidate candidate = new Candidate();
        candidate.setEmail(user.getEmail());
        candidate.setUser(newUser);
        candidate.setFullName(user.getFullname());
        candidate.setAvatar("https://firebasestorage.googleapis.com/v0/b/jobhiringweb.appspot.com/o/avatars%2FavatarDefault.png?alt=media&token=caa9f8a4-ff38-4a35-a09b-23712bf2a504");
        UService.create(newUser);
        try {
            candidateService.save(candidate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng kí thành công", ""));

    }

//    @GetMapping(value = "/signup/employer")
    @PostMapping(value = "/signup/employer", consumes = {"application/json"})
    public ResponseEntity<ResponseObject> createAccountHier(@RequestBody EmployeeAuthedDTO user) {
//    	public ResponseEntity<ResponseObject> createAccountHier() {

//		logger.info(user.getUsername());
//		logger.info("-------");
    	System.out.println("------++");
        Role initRole = roleRepo.findRoleByName("EMPLOYER");
        User newUser = new User(user.getEmail(), user.getPassword(), true, initRole);

        Employer em = new Employer();
        Company com = new Company();


        em.setEmail(user.getEmail());
        em.setUser(newUser);
        em.setPhone(user.getPhone());
        em.setLogo("https://firebasestorage.googleapis.com/v0/b/jobhiringweb.appspot.com/o/avatars%2FavatarDefault.png?alt=media&token=caa9f8a4-ff38-4a35-a09b-23712bf2a504");

        System.out.println(user.getAddress());
        com.setAddress(user.getAddress());
        com.setRate((double) 0);
        com.setName(user.getName());
        com.setDomain(user.getDomain());
        com.setTaxCode(null);
        com.setEmployer(em);
        em.setCompany(com);

        try {
            UService.create(newUser);
            comService.save(com);
            emService.save(em);

            //send verify email
            String html = FileUtils.readFileToString(new File("src/main/java/com/setqt/Hiring/Utils/verifyEmailTemplate.html"), StandardCharsets.UTF_8);

            html = html.replace("${user.name}", com.getName());
            html = html.replace("${app_url}", Objects.requireNonNull(environment.getProperty("app_url")));
            html = html.replace("${user.email}", em.getEmail());
            html = html.replace("${hashEmail}", passEncoder.encode(em.getEmail()));

            emailService.sendHtmlEmail("jobhiringweb@gmail.com", em.getEmail(), "Xác nhận đăng ký tài khoản doanh nghiệp Jore", html);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng kí thành công", ""));

    }


    @GetMapping("/verify")
    public ResponseEntity<ResponseObject> verifyEmail(@RequestParam(name = "email", defaultValue = "%") String email
            , @RequestParam(name = "token", defaultValue = "%") String token) {
        try {

			boolean check = passEncoder.matches(email, token);

            if (!check)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "verify failed", null));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Xác thực thành công", null));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "not verify email", null));
        }

    }


}
