package com.setqt.Hiring.Repository;

import com.setqt.Hiring.DTO.APIResponse.NotificationResponse;
import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT NEW com.setqt.Hiring.DTO.APIResponse.NotificationResponse(n.image, n.status, n.title, n.content, n.time, n.candidate.id, n.company.id, n.role)"
            + " FROM Notification n  WHERE n.company.id = :idCompany and n.role = 'CtoE' order by n.time desc ")
    List<NotificationResponse> getNotificationCompany(Long idCompany);

    @Query("SELECT NEW com.setqt.Hiring.DTO.APIResponse.NotificationResponse(n.image, n.status, n.title, n.content, n.time, n.candidate.id, n.company.id, n.role)"
            + " FROM Notification n  WHERE n.candidate.id = :idCandidate and n.role = 'EToC' order by n.time desc ")
    List<NotificationResponse> getNotificationCandidate(Long idCandidate);

    @Query("UPDATE Notification n SET n.status = 'sent'"
            + "WHERE n.candidate.id = :idCompany and n.role = 'CtoE'")
    List<NotificationResponse> setSentCompany(Long idCompany);

    @Query("UPDATE Notification n SET n.status = 'sent'"
            + "WHERE n.candidate.id = :idCandidate and n.role = 'EToC'")
    List<NotificationResponse> setSentCandidate(Long idCandidate);



}
