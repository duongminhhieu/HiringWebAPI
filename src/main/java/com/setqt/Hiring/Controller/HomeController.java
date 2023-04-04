package com.setqt.Hiring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Service.CompanyService;

@RestController
public class HomeController {

	
	@Autowired
	private CompanyService comService;
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseObject> test() {
		List<Company> result= comService.findAll();
		if (result.isEmpty()) return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( 
				new ResponseObject("failed","not found data",null));
				
		return ResponseEntity.status(HttpStatus.OK).body( 
				new ResponseObject("ok","found data",result));
	}
}
