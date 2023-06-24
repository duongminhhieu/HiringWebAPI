package com.setqt.Hiring;

import com.setqt.Hiring.Controller.NotificationController;
import com.setqt.Hiring.NotificationSSE.NotificationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HiringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiringApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
				registry.addMapping("/**").allowedHeaders("*");
			}
		};
	}

}
