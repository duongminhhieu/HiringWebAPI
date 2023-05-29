package com.setqt.Hiring.Controller;

import java.util.List;
import java.util.Optional;

import com.setqt.Hiring.Model.JobDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Service.JobPosting.JobPostingService;

@RestController
@RequestMapping("/job")
@CrossOrigin(origins = "*")
public class JobPostingController {

	@Autowired
	JobPostingService jobService;

	@GetMapping("/search")
	public ResponseEntity<ResponseObject> searchJob(@RequestParam(name = "text", defaultValue = "%") String job
			,@RequestParam(name = "address", defaultValue = "%") String add) {
		try {

			System.out.println(job);
			List<JobPosting> result;
			result = jobService.findJobPostingWithDescription(job, add);

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


	@GetMapping("/getAll")
	public ResponseEntity<ResponseObject> getAllJobPosting() {
		try {
			System.out.println(1);
			List<JobPosting> result = jobService.findAll();
			System.out.println(result.size());
			if (result.isEmpty())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("failed", "not found data", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", result));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("failed", "server fail", null));
		}
	}

	@GetMapping("/{idJobPosting}")
	public ResponseEntity<ResponseObject> getJobPosting(@PathVariable String idJobPosting) {
		try {

			Optional<JobPosting> jobPosting =  jobService.findById(Long.parseLong(idJobPosting));

			if (jobPosting.get() == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("failed", "not found data", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", jobPosting.get()));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("failed", "not found data", null));
		}

	}
}
