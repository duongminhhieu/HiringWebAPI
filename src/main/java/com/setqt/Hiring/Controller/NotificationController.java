package com.setqt.Hiring.Controller;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import com.setqt.Hiring.DTO.APIResponse.NotificationResponse;
import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Model.Notification;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.NotificationSSE.NotificationService;
import com.setqt.Hiring.Service.Notification.NotificationDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

		NotificationResponse notificationResponse = new NotificationResponse("hello", "ok", "theeh", message, new Date());
		notificationService.sendNotification(clientId, notificationResponse);
		return ResponseEntity.ok("Notification sent successfully");
	}


	@GetMapping("/getAll")
	public ResponseEntity<ResponseObject> getAll() {

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
