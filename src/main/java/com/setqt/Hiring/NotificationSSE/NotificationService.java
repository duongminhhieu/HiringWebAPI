package com.setqt.Hiring.NotificationSSE;

import com.setqt.Hiring.DTO.APIResponse.NotificationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(String clientId, SseEmitter emitter) {
        emitters.put(clientId, emitter);
    }

    public void removeEmitter(String clientId) {
        emitters.remove(clientId);
    }

    public void sendNotification(String clientId, NotificationResponse message) {
        SseEmitter emitter = emitters.get(clientId);
        if (emitter != null) {
            try {
                emitter.send(message);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(clientId);
            }
        }
    }
}
