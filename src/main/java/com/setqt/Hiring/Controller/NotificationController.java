package com.setqt.Hiring.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.setqt.Hiring.DTO.APIResponse.NotificationResponse;
import com.setqt.Hiring.Model.Notification;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.NotificationSSE.NotificationService;
import com.setqt.Hiring.Service.Notification.NotificationDBService;

@RestController
@RequestMapping(path = "/notification")
@CrossOrigin(origins = "*")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private NotificationDBService notificationDBService;

	@GetMapping("/subscribe/{clientId}")
	public SseEmitter subscribe(@PathVariable String clientId) {
		SseEmitter emitter = new SseEmitter();
		notificationService.addEmitter(clientId, emitter);
		return emitter;
	}

	@GetMapping("/send/{clientId}")
	public ResponseEntity<String> sendNotification(@PathVariable String clientId, @RequestParam String message) {

		NotificationResponse notificationResponse = new NotificationResponse( "notification.getImage()", "notification.getStatus()", "notification.getTitle()",
				"notification.getContent()", new Date(), 10000L,
				10000L, "notification.getRole()");
		notificationService.sendNotification(clientId, notificationResponse);
		return ResponseEntity.ok("Notification sent successfully");
	}


	@GetMapping("/getAll")
	public ResponseEntity<ResponseObject> getAll() {

		  LocalDateTime localDateTime =  LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
	        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	        System.out.println("Lúc " + currentDate);
		try {
			List<Notification> result = notificationDBService.findAll();
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

}
