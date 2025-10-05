package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.notification.NotificationTask;
import pro.sky.telegrambot.repository.NotificationRepository;
import pro.sky.telegrambot.service.MessageService;


import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final Pattern patternMessage = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
    private final NotificationRepository notificationRepository;
    private final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final String RESPONSE_TEXT = "Привет, я твой бот-помощник";
    private final String WRONG_ON_TEXT = "Некорректное сообщение";
    private final String sendMessage = "Уведомление добавлено";
    private MessageService service;

    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationRepository notificationRepository,
                                      MessageService service, TelegramBot telegramBot) {
        this.notificationRepository = notificationRepository;
        this.service = service;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String textMessage = update.message().text();
            Long chatId = update.message().chat().id();

            if (textMessage.equals("/start")) {
                service.info(chatId, RESPONSE_TEXT);
            } else {
                Matcher matcher = patternMessage.matcher(textMessage);
                if (matcher.matches()) {
                    NotificationTask notificationTaskClass = new NotificationTask();
                    notificationTaskClass.setChatId(chatId);
                    notificationTaskClass.setNotificationMessage(matcher.group(3));
                    notificationTaskClass.setNotificationLocalDateTime(
                            LocalDateTime.parse(matcher.group(1), DATE_TIME));
                    notificationTaskClass = notificationRepository.save(notificationTaskClass);
                    service.info(chatId, sendMessage);
                    logger.info("New notification with id - {}", notificationTaskClass.getId());
                } else {
                    service.info(chatId, WRONG_ON_TEXT);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}