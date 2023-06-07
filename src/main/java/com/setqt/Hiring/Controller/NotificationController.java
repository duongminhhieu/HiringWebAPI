package com.setqt.Hiring.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.print.attribute.standard.Media;

import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*", allowedHeaders = {"Content-Type", "Authorization"})
public class NotificationController {
	
	
	@Autowired
	UserService uService;

	@Autowired
	JwtTokenHelper jwtHelper;
	public Map<String,SseEmitter> listEmitter= new HashMap<String, SseEmitter>();
	
	@GetMapping(value="/subscribe",consumes = org.springframework.http.MediaType.ALL_VALUE)
	public SseEmitter init(@RequestParam String id) {

		String username = jwtHelper.getUsernameFromToken(id);
		System.out.println(username);
//		User user = (User) uService.findOneByUsername(username);
		SseEmitter emit = new SseEmitter();
		initSub(emit);
		listEmitter.put(username, emit);
		emit.onCompletion(()->listEmitter.remove(emit));
		emit.onTimeout(()->listEmitter.remove(emit));
//		emit.onError(()->listEmitter.remove(emit));
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
	public void dispatch(@RequestParam String title,@RequestParam String text,String username) {
		String format= new JSONObject().put("title",title).put("text", text).toString();
		
		SseEmitter emit = listEmitter.get(username);
		
		if (emit!=null) {
			try {
				emit.send(SseEmitter.event().name("lastestNews").data(format));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				listEmitter.remove(username);
			}
		}
	}
}
