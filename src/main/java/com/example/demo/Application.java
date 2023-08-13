package com.example.demo;

import com.example.demo.telegram.AutoNotification;
import com.example.demo.telegram.CurrencyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Application {
    private static AutoNotification autoNotification;
    private static CurrencyBot currencyBot;

    @Autowired
    public Application(CurrencyBot currencyBot, AutoNotification autoNotification) {
        Application.autoNotification = autoNotification;
        Application.currencyBot = currencyBot;
    }

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(Application.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(currencyBot);
        autoNotification.run();
    }
}
