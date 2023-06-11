package com.setqt.Hiring.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import com.setqt.Hiring.DTO.EmployeeAuthedDTO;
import com.setqt.Hiring.Model.*;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Service.EmailService.EmailService;
import com.setqt.Hiring.Threads.ConfirmEmailThreads;
import com.setqt.Hiring.Threads.ResetPassEmailThread;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import org.springframework.util.FileCopyUtils;
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
@CrossOrigin(origins = "*", allowedHeaders = {"Content-Type", "Authorization"})
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


    @PostMapping("/loginCandidate")
    public ResponseEntity<?> loginCandidate(@RequestBody AuthenRequest authentRequest)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authentRequest.getUsername(), authentRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jWTTokenHelper.generateToken(authentRequest.getUsername());

            User user = (User) UService.findOneByUsername(authentRequest.getUsername());
            Set<Role> roles = new HashSet<>();
            roles = (Set<Role>) user.getRoles();
            boolean check = false;
            Iterator<Role> iterator = roles.iterator();
            while (iterator.hasNext()) {
                Role role = iterator.next();
                if (Objects.equals(role.getNameRole(), "CANDIDATE")) {
                    check = true;
                    break;
                }
            }

            if (!check) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Đăng nhập không thành công",
                                ""));
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Đăng nhập thành công",
                            jwt));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Đăng nhập không thành công",
                            ""));
        }

    }

    @PostMapping("/loginEmployer")
    public ResponseEntity<?> loginEmployer(@RequestBody AuthenRequest authentRequest)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authentRequest.getUsername(), authentRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jWTTokenHelper.generateToken(authentRequest.getUsername());

            User user = (User) UService.findOneByUsername(authentRequest.getUsername());
            Set<Role> roles = new HashSet<>();
            roles = (Set<Role>) user.getRoles();
            boolean check = false;
            Iterator<Role> iterator = roles.iterator();
            while (iterator.hasNext()) {
                Role role = iterator.next();
                if (Objects.equals(role.getNameRole(), "EMPLOYER")) {
                    check = true;
                    break;
                }
            }

            if (!check) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Đăng nhập không thành công",
                                ""));
            }

//            if(!user.isEnable()){
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("failed", "Bạn chưa xác thực tài khoản!",
//                                ""));
//            }

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


        List<User> userExist = UService.findByUsername(user.getEmail());
        if (userExist.size() != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Đăng kí không thành công, đã tồn tại tài email này", ""));
        }
        System.out.println("ok" + user.getEmail());
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
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Lỗi server !....", ""));

        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng kí thành công", ""));

    }


    @PostMapping(value = "/signup/employer", consumes = {"application/json"})
    public ResponseEntity<ResponseObject> createAccountHier(@RequestBody EmployeeAuthedDTO user) {

        List<User> userExist = UService.findByUsername(user.getEmail());
        if (userExist.size() != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Đăng kí không thành công, đã tồn tại tài email này", ""));
        }
        Role initRole = roleRepo.findRoleByName("EMPLOYER");
        User newUser = new User(user.getEmail(), user.getPassword(), false, initRole);

        Employer em = new Employer();
        Company com = new Company();


        em.setEmail(user.getEmail());
        em.setUser(newUser);
        em.setPhone(user.getPhone());
        em.setLogo("https://firebasestorage.googleapis.com/v0/b/jobhiringweb.appspot.com/o/avatars%2FavatarDefault.png?alt=media&token=caa9f8a4-ff38-4a35-a09b-23712bf2a504");
        System.out.println(user.getAddress());
        com.setAddress(user.getAddress());
        com.setRate((double) 5);
        com.setName(user.getName());
        com.setDomain(user.getDomain());
        com.setTaxCode(null);
        com.setLogo("https://firebasestorage.googleapis.com/v0/b/jobhiringweb.appspot.com/o/avatars%2FavatarDefault.png?alt=media&token=caa9f8a4-ff38-4a35-a09b-23712bf2a504");
        com.setEmployer(em);
        em.setCompany(com);

        try {
            UService.create(newUser);
            comService.save(com);
            emService.save(em);

            // gui mail
            ConfirmEmailThreads confirmEmailThreads = new ConfirmEmailThreads(com, em, environment, passEncoder, emailService);
            Thread thread = new Thread(confirmEmailThreads);
            thread.start();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Đăng không thành công", ""));

        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng kí thành công", ""));

    }


    @GetMapping("/verify")
    public ResponseEntity<byte[]> verifyEmail(@RequestParam(name = "id", defaultValue = "%") String id,
                                              @RequestParam(name = "token", defaultValue = "%") String token) {
        try {
            boolean check = passEncoder.matches(id, token);

            if (!check) {
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body("verify failed".getBytes());
            } else {
                Optional<User> u = UService.findById(Long.parseLong(id));
                u.get().setEnable(true);
                UService.save(u.get());
                File htmlFile = new File("src/main/java/com/setqt/Hiring/Utils/notificationEmailTemplate.html");
                byte[] htmlContent = FileCopyUtils.copyToByteArray(htmlFile);
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(htmlContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body("Lỗi server !...".getBytes());
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestParam("email") String email, @RequestParam("role") String role) {
        try {

            List<User> userExist = UService.findByUsername(email);
            User user = null;
            if (userExist.size() == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Tài khoản này không tồn tại", ""));
            } else {
                // kiem tra xem trong listUser và lấy ra user có role cần lấy
                for (User u : userExist) {
                    Set<Role> roles = new HashSet<>();
                    roles = (Set<Role>) u.getRoles();
                    Iterator<Role> iterator = roles.iterator();
                    while (iterator.hasNext()) {
                        Role role1 = iterator.next();
                        if (Objects.equals(role1.getNameRole(), role)) {
                            user = u;
                            break;
                        }
                    }
                }
            }

            if (user == null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Tài khoản này không tồn tại", ""));
            }

            // gui mail
            ResetPassEmailThread resetPassEmailThread = new ResetPassEmailThread(email, role, environment, passEncoder, emailService);
            Thread thread = new Thread(resetPassEmailThread);
            thread.start();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Đã gửi mail reset password thành công !", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseObject("failed", "Lỗi server!...", null));
        }
    }

    @GetMapping("/reset_password")
    public ResponseEntity<byte[]> formResetPassword(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "token") String token,
                                                    @RequestParam(name = "role") String role) {
        try {
            boolean check = passEncoder.matches(email, token);

            if (!check) {
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body("failed".getBytes());
            } else {

                String html = null;
                try {
                    html = FileUtils.readFileToString(new File("src/main/java/com/setqt/Hiring/Utils/formResetPassword.html"), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                html = html.replace(("${APP_URL}"), Objects.requireNonNull(environment.getProperty("app_url")));
                html = html.replace("{{email}}", email);
                html = html.replace("{{role}}", role);
                html = html.replace("{{token}}", token);


                byte[] htmlContent = html.getBytes(StandardCharsets.UTF_8);
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(htmlContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body("Lỗi server !...".getBytes(StandardCharsets.UTF_8));
        }
    }

    @PostMapping("/post_reset_password")
    public ResponseEntity<byte[]> ResetPassword(@RequestParam(name = "email") String email,
                                                @RequestParam(name = "token") String token,
                                                @RequestParam(name = "role") String role,
                                                @RequestParam(name = "password") String newPassword) {
        try {
            boolean check = passEncoder.matches(email, token);

            if (!check) {
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body("failed".getBytes());
            } else {
                List<User> userExist = UService.findByUsername(email);
                User user = null;
                if (userExist.size() == 0) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.TEXT_HTML)
                            .body("Không tìm thấy tài khoản !...".getBytes(StandardCharsets.UTF_8));
                } else {
                    // kiem tra xem trong listUser và lấy ra user có role cần set lại pass
                    for (User u : userExist) {
                        Set<Role> roles = new HashSet<>();
                        roles = (Set<Role>) u.getRoles();
                        Iterator<Role> iterator = roles.iterator();
                        while (iterator.hasNext()) {
                            Role role1 = iterator.next();
                            if (Objects.equals(role1.getNameRole(), role)) {
                                user = u;
                                break;
                            }
                        }
                    }
                }

                if (user == null) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.TEXT_HTML)
                            .body("Không tìm thấy tài khoản !...".getBytes(StandardCharsets.UTF_8));
                }

                // set lại mật khẩu
                user.setPassword(passEncoder.encode(newPassword));
                User result = UService.save(user);

                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body("Tạo lại mật khẩu mới thành công 👍👍👍".getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body("Lỗi server !...".getBytes(StandardCharsets.UTF_8));
        }
    }


}
