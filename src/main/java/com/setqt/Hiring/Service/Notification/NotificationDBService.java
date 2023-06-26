package com.setqt.Hiring.Service.Notification;

import com.setqt.Hiring.DTO.APIResponse.NotificationResponse;
import com.setqt.Hiring.Model.Notification;
import com.setqt.Hiring.Repository.NotificationRepository;
import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationDBService extends GenericService<Notification> implements INotification {

    public NotificationDBService(JpaRepository<Notification, Long> genericRepository) {
        super(genericRepository);
    }

    @Override
    public List<NotificationResponse> listNotificationCompany(Long idCompany) {
        return ((NotificationRepository) genericRepository).getNotificationCompany(idCompany);
    }

    @Override
    public List<NotificationResponse> listNotificationCandidate(Long idCandidate) {
        return ((NotificationRepository) genericRepository).getNotificationCandidate(idCandidate);
    }

    @Override
    public void setSentCandidate(Long idCandidate) {
        ((NotificationRepository) genericRepository).setSentCandidate(idCandidate);
    }

    @Override
    public void setSentCompany(Long idCompany) {
        ((NotificationRepository) genericRepository).setSentCompany(idCompany);
    }
}
