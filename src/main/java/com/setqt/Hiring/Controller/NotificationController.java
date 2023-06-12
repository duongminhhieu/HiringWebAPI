package com.setqt.Hiring.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.print.attribute.standard.Media;

import com.setqt.Hiring.WebSocket.NotificationWebSocketHandler;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse.SseBuilder;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.google.common.net.MediaType;
import com.setqt.Hiring.Security.JwtTokenHelper;
import com.setqt.Hiring.Security.Model.User;
import com.setqt.Hiring.Service.UserService;
import com.setqt.Hiring.Service.JobDescription.JobDescriptionService;
import com.setqt.Hiring.Service.JobPosting.JobPostingService;

@RestController
@RequestMapping(path = "/notification")
//@CrossOrigin(origins = "*", allowedHeaders = {"Content-Type", "Authorization"})
@CrossOrigin(origins = "*")
public class NotificationController {

	@Autowired
	UserService uService;

	@Autowired
	JwtTokenHelper jwtHelper;
	public Map<String, SseEmitter> listEmitter = new HashMap<String, SseEmitter>();

	@Autowired
	private NotificationWebSocketHandler notificationWebSocketHandler;

	@GetMapping(value = "/subscribe", consumes = org.springframework.http.MediaType.ALL_VALUE)
	public SseEmitter init(@RequestParam String id) {

		String username = id;
		if (id == "")
			return null;
		System.out.println("client den");
		SseEmitter emit = new SseEmitter();
		initSub(emit);
		listEmitter.put(username, emit);
		emit.onCompletion(() -> listEmitter.remove(emit));
		emit.onTimeout(() -> listEmitter.remove(emit));
		return emit;
	}

	private void initSub(SseEmitter emit) {
		try {
			emit.send(SseEmitter.event().name("INIT"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@PostMapping("/dispatchEvent")
	public void dispatch(@RequestParam String title, @RequestParam String text, String username) {
		String format = new JSONObject().put("title", title).put("text", text).toString();

		SseEmitter emitter;
		for (Map.Entry<String, SseEmitter> entry : listEmitter.entrySet()) {
			if (entry.getKey().contains(username)) {
				emitter = listEmitter.get(entry.getKey());

				if (emitter != null) {
					try {
						emitter.send(SseEmitter.event().name("lastestNews").data(format));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						listEmitter.remove(username);
					}
				}
			}
		}
	}

	@GetMapping("/sendNotification")
	public void sendNotification() {
		String notification = "Thông báo từ backend Spring Boot";
		try{
			// Gửi thông báo tới tất cả các kết nối WebSocket
			notificationWebSocketHandler.sendNotification(notification);
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
