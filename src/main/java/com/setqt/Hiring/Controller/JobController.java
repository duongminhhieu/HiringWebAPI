package com.setqt.Hiring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Repository.JobPostingRepository;
import com.setqt.Hiring.Service.JobPosting.JobPostingService;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	JobPostingService jobService;

	@PostMapping("/search")
	public ResponseEntity<ResponseObject> searchJob(@RequestParam(name = "text", defaultValue = "ALL") String job) {
		try {

			System.out.println(job);
			List<JobPosting> result;
			result = jobService.findAll();

			if (result.isEmpty())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("failed", "not found data", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@PostMapping("/add")
	public ResponseEntity<ResponseObject> addJob(@RequestBody JobPosting job) {
		try {
			System.out.println("ssss---");
			System.out.println(job);
			JobPosting result;
			result = jobService.save(job);

			if (result==null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("failed", "cannot add", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "posted job", result));
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@GetMapping("/test")
	public String test() {
		return "okk";
	}
}
