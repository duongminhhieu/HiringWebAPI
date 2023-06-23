package com.setqt.Hiring.WebSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Thêm session vào danh sách các kết nối WebSocket đã thiết lập
        System.out.println("comed");
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Xử lý tin nhắn từ frontend (nếu cần)
         // session.sendMessage(new TextMessage("Server response: " + message.getPayload()));
    }

    public void sendNotification(String notification) {
        TextMessage message = new TextMessage(notification);

        // Gửi thông báo tới tất cả các kết nối WebSocket
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
