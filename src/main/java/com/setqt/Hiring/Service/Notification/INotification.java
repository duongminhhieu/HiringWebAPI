package com.setqt.Hiring.Service.Notification;

import com.setqt.Hiring.DTO.APIResponse.NotificationResponse;
import com.setqt.Hiring.Model.Notification;
import com.setqt.Hiring.Service.Generic.IGenericService;

import java.util.List;

public interface INotification extends IGenericService<Notification> {

    public List<NotificationResponse> listNotificationCompany(Long idCompany);
    public List<NotificationResponse> listNotificationCandidate(Long idCandidate);

}
