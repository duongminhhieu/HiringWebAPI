package com.setqt.Hiring.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import com.setqt.Hiring.DTO.APIResponse.NotificationResponse;
import com.setqt.Hiring.DTO.CandidateDTO;
import com.setqt.Hiring.DTO.EmployerDTO;
import com.setqt.Hiring.DTO.JobPostingDTO;
import com.setqt.Hiring.DTO.APIResponse.AnalysisData;
import com.setqt.Hiring.Model.*;
import com.setqt.Hiring.NotificationSSE.NotificationService;
import com.setqt.Hiring.Service.Firebase.FirebaseImageService;
import com.setqt.Hiring.Service.Notification.NotificationDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.UserService;
import com.setqt.Hiring.Service.CV.CVService;
import com.setqt.Hiring.Service.Company.CompanyService;
import com.setqt.Hiring.Service.Employer.EmployerService;
import com.setqt.Hiring.Service.JobDescription.JobDescriptionService;
import com.setqt.Hiring.Service.JobPosting.JobPostingService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/employer")
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class EmployerController {

	@Autowired
	EmployerService employerService;
	@Autowired
	CompanyService companyService;
	@Autowired
	UserService uService;
	@Autowired
	JobPostingService jobService;
	@Autowired
	CVService cvService;
	@Autowired
	private FirebaseImageService firebaseImageService;
	@Autowired
	JobDescriptionService JDesciptService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	JwtTokenHelper jwtHelper;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private NotificationDBService notificationDBService;

	@PostMapping("/addJobPosting")
	public ResponseEntity<ResponseObject> addPosting(@RequestBody JobPostingDTO jobPostingDTO,
													 @RequestHeader(value = "Authorization") String jwt) {

		jwt = jwt.substring(7, jwt.length());

		String username = jwtHelper.getUsernameFromToken(jwt);
		User user = (User) uService.findOneByUsername(username);

		try {

			JobDescription jobDescription = new JobDescription(jobPostingDTO.getDescription(),
					jobPostingDTO.getBenefits(), jobPostingDTO.getRequirement(), jobPostingDTO.getGender(),
					jobPostingDTO.getExperience(), jobPostingDTO.getSalary(), jobPostingDTO.getNumber_candidates(),
					jobPostingDTO.getWorking_form(), jobPostingDTO.getAddress_work());
			Employer em = user.getEmployer();

			JobPosting jobPosting = new JobPosting();

			jobPosting.setTitle(jobPostingDTO.getTitle());
			jobPosting.setDueDate(jobPostingDTO.getDueDate());
			jobPosting.setPostDate(jobPostingDTO.getPostDate());
			jobPosting.setView(0);
			jobPosting.setStatus("approved");

			Company com = em.getCompany();
			System.out.println(com.getName());
			jobPosting.setCompany(com);
			jobPosting.setJobDescription(jobDescription);

			jobService.save(jobPosting);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Thêm việc thành công ", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Thêm việc không thành công !!!!", null));

		}

	}

	@PostMapping("/updateJobPosting/{id}")
	public ResponseEntity<ResponseObject> updateJobPosting(@PathVariable("id") Long id,
														   @RequestBody JobPostingDTO jobPostingDTO,
														   @RequestHeader(value = "Authorization") String jwt) {

		jwt = jwt.substring(7, jwt.length());

		try {

			Optional<JobPosting> jobPosting = jobService.findById(id);
			if (jobPosting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Không tìm thấy công việc này.", null));
			}

			JobDescription jobDescription = new JobDescription(jobPostingDTO.getDescription(),
					jobPostingDTO.getBenefits(), jobPostingDTO.getRequirement(), jobPostingDTO.getGender(),
					jobPostingDTO.getExperience(), jobPostingDTO.getSalary(), jobPostingDTO.getNumber_candidates(),
					jobPostingDTO.getWorking_form(), jobPostingDTO.getAddress_work());

			jobPosting.get().setTitle(jobPostingDTO.getTitle());
			jobPosting.get().setDueDate(jobPostingDTO.getDueDate());
			jobPosting.get().setPostDate(jobPostingDTO.getPostDate());
			jobPosting.get().setView(0);
			jobPosting.get().setStatus("approved");


			jobPosting.get().setJobDescription(jobDescription);

			jobService.save(jobPosting.get());

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Cập nhật việc thành công ", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Lỗi Server !!", null));

		}

	}

	@PostMapping("/setJob/{id}")
	public ResponseEntity<ResponseObject> changeJob(@RequestHeader(value = "Authorization") String jwt,
													@PathVariable("id") Long id, @RequestParam(name = "action", defaultValue = "hide") String action) {

		jwt = jwt.substring(7, jwt.length());

		try {

			Optional<JobPosting> jobForAction = jobService.findById(id);
			if (jobForAction.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Không tìm thấy công việc này.", null));
			}
			JobPosting job = jobForAction.get();
			if (action.equals("hide")) {
				job.setStatus("hide");
				jobService.save(job);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Ẩn thành công ", job));
			} else if (action.equals("approved")) {
				job.setStatus("approved");
				jobService.save(job);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Mở lại thành công ", job));
			} else if (action.equals("delete")) {
				jobService.delete(id);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Xóa thành công ", null));

			}
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Không đúng param", null));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Lỗi server!!!!", null));

		}

	}

	@PostMapping("/setStatusCV")
	public ResponseEntity<ResponseObject> setStatusCV(@RequestParam String status, @RequestParam Long id) {

		Optional<CV> cv;
		try {
			cv = cvService.findById(id);

			if (cv.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("ok", "Không tìm thấy cv, sai id !!!", null));

			} else {
				if (status == null)
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseObject("failed", "Sai status!!!", null));
				  LocalDateTime localDateTime = LocalDateTime.now();
			        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
				if (status.equals("pass")) {
					CV res = cv.get();
					res.setStatus("pass");
					CV result = cvService.save(res);

					// luu zo DB
					Notification notification = new Notification();
					notification.setImage(cv.get().getJobPosting().getCompany().getLogo());
					notification.setStatus("new");
					notification.setRole("EtoC");
					notification.setTitle("Bạn vừa được tuyển dụng một công việc " + cv.get().getJobPosting().getTitle());
					notification.setContent("Công ty " + cv.get().getJobPosting().getCompany().getName() + " vừa chấp nhận CV của bạn ");
					notification.setTime(currentDate);
					notification.setCandidate(cv.get().getCandidate());
					notification.setCompany(cv.get().getJobPosting().getCompany());
					notificationDBService.save(notification);


					// Gui thong bao den Candidate
					NotificationResponse notificationResponse = new NotificationResponse( notification.getImage(), notification.getStatus(), notification.getTitle(), notification.getContent(), notification.getTime(), notification.getCandidate().getId(), notification.getCompany().getId(), notification.getRole());
					notificationService.sendNotification(cv.get().getCandidate().getEmail(), notificationResponse);

					return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Thành công !!!", result));

				} else if(status.equals("reject")){
					cvService.delete(id);
					// luu zo DB
					Notification notification = new Notification();
					notification.setImage(cv.get().getJobPosting().getCompany().getLogo());
					notification.setStatus("new");
					notification.setRole("EtoC");
					notification.setTitle("Rất tiếc! Một CV của bạn vừa bị từ chối");
					notification.setContent("Công ty" + cv.get().getJobPosting().getCompany().getName() + " vừa từ chối CV của bạn ");
					notification.setTime(currentDate);
					notification.setCandidate(cv.get().getCandidate());
					notification.setCompany(cv.get().getJobPosting().getCompany());
					notificationDBService.save(notification);

					// Gui thong bao den Candidate
					NotificationResponse notificationResponse = new NotificationResponse( notification.getImage(), notification.getStatus(), notification.getTitle(), notification.getContent(), notification.getTime(), notification.getCandidate().getId(), notification.getCompany().getId(), notification.getRole());
					notificationService.sendNotification(cv.get().getCandidate().getEmail(), notificationResponse);

					return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Thành công !!!", null));
				} else {
					CV res = cv.get();
					res.setStatus("consider");
					cvService.save(res);
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Thành công !!!", null));

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Không thành công !", null));

		}
	}

	@GetMapping("/getCV")
	public ResponseEntity<ResponseObject> getCV(@RequestParam String id) {
		try {

			Optional<JobPosting> jobPosting = jobService.findById(Long.parseLong(id));
			if (jobPosting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Không tìm thấy việc !!!!", null));
			}

			JobPosting getJob = jobPosting.get();
			List<CV> cv = getJob.getListCV();
			if (cv.isEmpty())
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Chưa có cv nào !!!!", cv));

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", "Tìm tất cả cv thành công !", cv));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Lỗi server !!!!", null));
		}

	}

	@GetMapping("/getMyJob")
	public ResponseEntity<ResponseObject> getMyJob(@RequestHeader(value = "Authorization") String jwt) {
		try {

			jwt = jwt.substring(7, jwt.length());

			String username = jwtHelper.getUsernameFromToken(jwt);
//					User user = (User) uService.findOneByUsername(username);

			List<JobPosting> jobPosting = employerService.getAllJob(username);
			if (jobPosting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Không tìm thấy việc !!!!", null));
			}

//			List<CV> cv = getJob.getListCV();
//			if (cv.isEmpty())
//				return ResponseEntity.status(HttpStatus.OK)
//						.body(new ResponseObject("failed", "Chưa có cv nào !!!!", cv));

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", "Tìm tất cả công việc thành công !", jobPosting));
		} catch (Exception ex) {
			System.out.println(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Lỗi server !!!!", null));
		}

	}

	@GetMapping("/analys")
	public ResponseEntity<ResponseObject> analysis(@RequestHeader(value = "Authorization") String jwt) {
		try {

			jwt = jwt.substring(7, jwt.length());

			String username = jwtHelper.getUsernameFromToken(jwt);
			Optional<AnalysisData> data = cvService.analysData(username);
			if (data.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Không kết quả !!!!", null));
			} else
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Dữ liệu phân tích !", data.get()));

		} catch (Exception ex) {
			System.out.println(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Lỗi server !!!!", null));
		}

	}

	@GetMapping("/myInfo")
	public ResponseEntity<ResponseObject> getMyInfo(@RequestHeader(value = "Authorization") String jwt) {
		try {
			jwt = jwt.substring(7, jwt.length());

			String username = jwtHelper.getUsernameFromToken(jwt);
			System.out.println(username);
			User user = (User) uService.findOneByUsername(username);
			Employer employer = user.getEmployer();
			if (employer == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("failed", "not found data", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", employer));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("failed", "not found data", null));
		}
	}

	@GetMapping("/myInfoNew")
	public ResponseEntity<ResponseObject> getMyInfoNew(@RequestHeader(value = "Authorization") String jwt) {
		try {
			jwt = jwt.substring(7, jwt.length());

			String username = jwtHelper.getUsernameFromToken(jwt);
			System.out.println(username);
//			User user = (User) uService.findOneByUsername(username);
			Employer employer = employerService.getInfo(username);
			Company com = companyService.findCompanyByEmployerIdWithoutJobPosting(employer.getId());
			employer.setCompany(com);
			if (employer == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("failed", "not found data", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found data", employer));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("failed", "not found data", null));
		}
	}

	@PostMapping(value = "/updateInfoEmployer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> updateInfoEmployer(@RequestParam(value = "file", required = false) MultipartFile file,
															 @RequestParam("name") String name,
															 @RequestParam("phone") String phone,
															 @RequestParam("taxCode") String taxCode,
															 @RequestParam("address") String address,
															 @RequestParam("domain") String domain,
															 @RequestParam("companySize") String companySize,
															 @RequestParam("workTime") String workTime,
															 @RequestParam("description") String description,
															 @RequestHeader(value = "Authorization") String jwt) {
		try {
			//System.out.println(employerDTO.toString());
			jwt = jwt.substring(7, jwt.length());

			String username = jwtHelper.getUsernameFromToken(jwt);
			System.out.println(username);
			User user = (User) uService.findOneByUsername(username);
			Employer employer = user.getEmployer();
			Company company = employer.getCompany();

			if( file != null){
				// xu li file
				firebaseImageService = new FirebaseImageService();
				// save file to Firebase
				String fileName = firebaseImageService.save(file,
						"avatars_employers/" + employer.getId() + "_" + employer.getEmail());
				String imageUrl = firebaseImageService.getFileUrl(fileName);

				System.out.println((imageUrl));
				employer.setLogo(imageUrl);
			}

			if(phone != null && !phone.equals("null")) employer.setPhone(phone);
			if(address != null && !address.equals("null")) company.setAddress(address);
			if(name != null && !name.equals("null")) company.setName(name);
			if(domain != null && !domain.equals("null")) company.setDomain(domain);
			if(taxCode != null && !taxCode.equals("null")) company.setTaxCode(taxCode);
			company.setLogo(employer.getLogo());
			if(companySize != null && !companySize.equals("null")) company.setCompanySize(companySize);
			if(description != null && !description.equals("null")) company.setDescription(description);
			if(workTime != null && !workTime.equals("null")) company.setWorkTime(workTime);
			company.setEmployer(employer);
			employer.setCompany(company);

			Employer result = employerService.save(employer);

			if (result == null)
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "update info employer failed", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Cập nhật thành công", result));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Server bị lỗi !!", null));
		}
	}

	@PostMapping("/changePassword")
	public ResponseEntity<ResponseObject> changePassword(@RequestHeader(value = "Authorization") String jwt,
														 @RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
		try {

			jwt = jwt.substring(7, jwt.length());

			String username = jwtHelper.getUsernameFromToken(jwt);
			System.out.println(username);
			User user = (User) uService.findOneByUsername(username);

			boolean check = passwordEncoder.matches(password, user.getPassword());

			if (!check) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Mật khẩu cũ đã nhập không đúng !", null));
			}

			user.setPassword(passwordEncoder.encode(newPassword));
			User result = uService.save(user);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", "Đổi mật khẩu thành công !", result));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Lỗi server!...", null));
		}
	}

	@GetMapping("/getAllNotification")
	public ResponseEntity<ResponseObject> getAllNotification(
			@RequestHeader(value = "Authorization") String jwt) {
		try {

			jwt = jwt.substring(7, jwt.length());
			String username = jwtHelper.getUsernameFromToken(jwt);
			System.out.println(username);
			User user = (User) uService.findOneByUsername(username);
			Employer employer = user.getEmployer();
			Company company = employer.getCompany();


			List<NotificationResponse> list = notificationDBService.listNotificationCompany(company.getId());

			if (list.isEmpty())
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("ok", "Không có dữ liệu", null));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Tất cả thông báo", list));

		} catch (Exception e) {
			e.printStackTrace(); return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("failed", "Lỗi server !....", null));

		}
	}

	@GetMapping("/setSentNotification")
	public ResponseEntity<ResponseObject> setSent(
			@RequestHeader(value = "Authorization") String jwt) {
		try {

			jwt = jwt.substring(7, jwt.length());
			String username = jwtHelper.getUsernameFromToken(jwt);
			System.out.println(username);
			User user = (User) uService.findOneByUsername(username);
			Employer employer = user.getEmployer();
			Company company = employer.getCompany();


			notificationDBService.setSentCompany(company.getId());

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đã xem các thông báo", null));

		} catch (Exception e) {
			e.printStackTrace(); return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("failed", "Lỗi server !....", null));

		}
	}

}
