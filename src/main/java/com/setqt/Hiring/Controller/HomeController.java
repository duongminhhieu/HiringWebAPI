package com.setqt.Hiring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.Employer;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Service.CompanyService;
import com.setqt.Hiring.Service.EmployerService;

@RestController
@RequestMapping("/company")
public class HomeController {

	@Autowired
	private CompanyService comService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JwtTokenHelper jwtHelper;

	@GetMapping("/getAll")
	public ResponseEntity<ResponseObject> test() {
		List<Company> result = comService.findAll();
		if (result.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("failed", "not found data", null));

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));
	}
	@GetMapping("/test")
	public ResponseEntity<ResponseObject> tests() {
		List<Employer> result = employerService.findAll();
		if (result.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("failed", "not found data", null));
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));
	}

	@GetMapping("/add")
	public ResponseEntity<ResponseObject> addCompany(@RequestHeader(value = "Authorization") String jwt) {
		jwt = jwt.substring(7, jwt.length());

		String user = jwtHelper.getUsernameFromToken(jwt);
		System.out.println(user);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", null));
	}
}
