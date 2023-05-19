package com.setqt.Hiring.Controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.setqt.Hiring.Security.Model.Role;
import com.setqt.Hiring.Security.Model.RoleRepository;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.UserService;

import ch.qos.logback.classic.Logger;
import client.AuthenRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserService UService;

	@Autowired
	private PasswordEncoder passEncoder;
	Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	
	
	@Autowired
	com.setqt.Hiring.Security.JwtTokenHelper jWTTokenHelper;
	@PostMapping("/login")
	public ResponseEntity<?> login2(@RequestBody AuthenRequest authentRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException {

		try {
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authentRequest.getUsername(), authentRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jWTTokenHelper.generateToken(authentRequest.getUsername());
		
//		System.out.println("sdsd===="+jwt);
		return  ResponseEntity.status(HttpStatus.OK).body( 
					new com.setqt.Hiring.Model.ResponseObject("ok","successfully authentication",jwt));
		
		}
		catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


	}

	@PostMapping("/signin")
		public ResponseEntity<String> authenticateUser(@RequestBody AuthenRequest loginDto){
//	    	String passEn = passEncoder.encode(loginDto.getPassword());
	   
	    	Authentication authentication  = authenticationManager.authenticate(
	    			new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
	    }

	@PostMapping(value = "/signup", consumes = { "application/json" })
	public ResponseEntity<?> createAccount(@RequestBody User user) {

		logger.info(user.getUsername());
		logger.info("-------");
		Role initRole = roleRepo.findRoleByName("ADMIN");
		User newUser = new User(user.getUsername(), user.getPassword(), true, initRole);
		UService.create(newUser);

		return ResponseEntity.ok(HttpStatus.OK);
	}

//	@PostMapping(value = "/login", consumes = { "application/json" })
//	public ResponseEntity<?> login(@RequestBody User user) {
//
//		Role initRole = roleRepo.findRoleByName("ADMIN");
//		User newUser = new User(user.getUsername(), user.getPassword(), true, initRole);
//		UService.create(newUser);
//
//		return ResponseEntity.ok(HttpStatus.OK);
//	}

//	@PostMapping(value="/auth2", consumes={"application/json"})
//	public ResponseEntity<?> createAccount() {
//		
//		User u = new User("odxxxk","fgh");
//		UService.create(u);
//		return ResponseEntity.ok(HttpStatus.OK);
//	}

	@GetMapping("/s")
	public String test() {
//		System.out.println(user.getUsername());

		logger.info("-------");

		return "Asd";
//		return ResponseEntity.ok(HttpStatus.OK);
	}

}
