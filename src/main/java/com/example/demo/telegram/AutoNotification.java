package com.example.demo.telegram;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class AutoNotification implements Runnable {
    private final MessageBuilder messageBuilder;
    private final UserService userService;
    private final CurrencyBot currencyBot;

    @Autowired
    public AutoNotification(MessageBuilder messageBuilder, UserService userService, CurrencyBot currencyBot) {
        this.messageBuilder = messageBuilder;
        this.userService = userService;
        this.currencyBot = currencyBot;
    }

    @Override
    public void run() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable notificationTask = () -> {
            LocalTime localTime = LocalTime.now(ZoneId.of("Europe/Kiev"));

            if (localTime.getMinute() == 0 && localTime.getHour() >= 9 && localTime.getHour() <= 18) {
                sendNotification(localTime.getHour());
            }
        };

        LocalTime now = LocalTime.now(ZoneId.of("Europe/Kiev"));
        LocalTime nextHour = now.plusHours(1).truncatedTo(ChronoUnit.HOURS);
        Duration timeToNextHour = Duration.between(now, nextHour);

        long initialDelayMillis = timeToNextHour.toMillis();
        long periodMillis = TimeUnit.HOURS.toMillis(1);

        executor.scheduleAtFixedRate(notificationTask, initialDelayMillis, periodMillis, TimeUnit.MILLISECONDS);

        executor.shutdown();
    }

    private void sendNotification(Integer currentHour) {
        for (User user : userService.getUsersByNotificationTime(currentHour)) {
            try {
                Long userId = user.getId();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText(messageBuilder.getCurrencyRateByUserSettings(userId));
                currencyBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("An error occurred while processing update:", e);
            } catch (IOException e) {
                log.error("An error occurred while building the notification message for user " + user.getId(), e);
            }
        }
    }
}
