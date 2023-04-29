package com.setqt.Hiring.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.setqt.Hiring.Service.FirebaseImageService;
import com.setqt.Hiring.Utils.Properties;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    private static FirebaseApp firebaseApp;
    @Autowired
    Properties properties;
    @PostConstruct
    public void init() throws IOException {
        if (firebaseApp == null) {
            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");
            //System.out.println(properties.getBucketName());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(properties.getBucketName())
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);

        }

    }

    @Bean
    public FirebaseApp firebaseApp() {
        return firebaseApp;
    }

}
