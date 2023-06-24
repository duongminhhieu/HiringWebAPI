package com.setqt.Hiring.Service.Notification;

import com.setqt.Hiring.Model.Notification;
import com.setqt.Hiring.Repository.NotificationRepository;
import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class NotificationDBService extends GenericService<Notification> implements INotification {
    public NotificationDBService(NotificationRepository notificationRepository) {
        super(notificationRepository);
    }
}
