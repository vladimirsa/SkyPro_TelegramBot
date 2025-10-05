package pro.sky.telegrambot.notification;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "notification_date_time")
    private LocalDateTime notificationLocalDateTime;
    @Column(name = "message_text")
    private String notificationMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getNotificationLocalDateTime() {
        return notificationLocalDateTime;
    }

    public void setNotificationLocalDateTime(LocalDateTime notificationLocalDateTime) {
        this.notificationLocalDateTime = notificationLocalDateTime;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) &&
                Objects.equals(notificationLocalDateTime, that.notificationLocalDateTime) &&
                Objects.equals(notificationMessage, that.notificationMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, notificationLocalDateTime, notificationMessage);
    }

    @Override
    public String toString() {
        return "NotificationTaskClass{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationLocalDateTime=" + notificationLocalDateTime +
                ", notificationMessage='" + notificationMessage + '\'' +
                '}';
    }
}
