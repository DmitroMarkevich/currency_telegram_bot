package com.example.demo.telegram.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@Component
public class MenuHandler extends BotCommand {
    private final KeyboardBuilder keyboardBuilder;

    @Autowired
    public MenuHandler(KeyboardBuilder keyboardBuilder) {
        this.keyboardBuilder = keyboardBuilder;
    }

    public SendMessage getStartMenu(Long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Hi! How can I help you?")
                .replyMarkup(keyboardBuilder.getMainKeyboard())
                .build();
    }

    public SendMessage getSettingsMenu(Long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Settings:")
                .replyMarkup(keyboardBuilder.getSettingsKeyboard())
                .build();
    }

    public EditMessageText getCurrencyMenu(Long chatId, Long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(Math.toIntExact(messageId))
                .text("Select a currency:")
                .replyMarkup(keyboardBuilder.getCurrencyKeyboard(chatId))
                .build();
    }

    public EditMessageText getDecimalPlacesMenu(Long chatId, Long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(Math.toIntExact(messageId))
                .text("Decimal places:")
                .replyMarkup(keyboardBuilder.getDecimalPlacesKeyboard(chatId))
                .build();
    }

    public EditMessageText getNotificationTimeMenu(Long chatId, Long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(Math.toIntExact(messageId))
                .text("Select a time for the notification:")
                .replyMarkup(keyboardBuilder.getNotificationTimeKeyboard(chatId))
                .build();
    }

    public EditMessageText getBankMenu(Long chatId, Long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .text("Select a bank:")
                .messageId(Math.toIntExact(messageId))
                .replyMarkup(keyboardBuilder.getBankKeyboard(chatId))
                .build();
    }
}