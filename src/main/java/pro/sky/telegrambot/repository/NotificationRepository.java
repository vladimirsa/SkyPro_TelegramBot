package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.notification.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findAllByNotificationLocalDateTime(LocalDateTime notificationLocalDateTime);
}