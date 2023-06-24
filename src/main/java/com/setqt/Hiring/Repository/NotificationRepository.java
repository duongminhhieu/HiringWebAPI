package com.setqt.Hiring.Repository;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
