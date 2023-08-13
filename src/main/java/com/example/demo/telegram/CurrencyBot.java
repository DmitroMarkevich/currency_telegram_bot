package com.example.demo.telegram;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.telegram.menu.MenuHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;

@Log4j2
@Component
public class CurrencyBot extends TelegramLongPollingBot {
    private final MessageBuilder messageBuilder;
    private final MenuHandler menuHandler;
    private final UserService userService;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Autowired
    public CurrencyBot(MessageBuilder messageBuilder, UserService userService, MenuHandler menuHandler) {
        this.messageBuilder = messageBuilder;
        this.menuHandler = menuHandler;
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                handleTextMessage(update);
            } else if (update.hasCallbackQuery()) {
                handleCallbackQuery(update);
            }
        } catch (TelegramApiException e) {
            log.error("An error occurred while processing update:", e);
        } catch (IOException e) {
            log.error("An error occurred while building the notification message for user. Error: ", e);
        }
    }

    private void handleCallbackQuery(Update update) throws TelegramApiException, IOException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callData = callbackQuery.getData();
        SendMessage outMessage = new SendMessage();

        Long chatId = callbackQuery.getMessage().getChatId();
        Long messageId = Long.valueOf(update.getCallbackQuery().getMessage().getMessageId());
        outMessage.setChatId(chatId);

        switch (callData) {
            case "GET_CURRENCY" -> {
                outMessage.setText(messageBuilder.getCurrencyRateByUserSettings(chatId));
                execute(outMessage);
                execute(menuHandler.getStartMenu(chatId));
            }
            case "SET_USD" -> {
                userService.updateUserCurrency(chatId, Currency.USD);
                execute(menuHandler.getCurrencyMenu(chatId, messageId));
            }
            case "SET_EUR" -> {
                userService.updateUserCurrency(chatId, Currency.EUR);
                execute(menuHandler.getCurrencyMenu(chatId, messageId));
            }
            case "GET_INFO" -> {
                outMessage.setText("""
                        This bot tracks exchange rates from multiple banks and provides information on request or automatically according to a specified schedule.
                        To view rates, select "Get actual currency" from the main menu.
                        Use the "User settings" section to configure the bots operating mode."""
                );
                execute(outMessage);
                execute(menuHandler.getStartMenu(chatId));
            }
            case "GET_SETTINGS", "GET_BANK_BACK", "GET_CURRENCY_BACK", "GET_NOTIFICATION_BACK" ->
                    execute(menuHandler.getSettingsMenu(chatId));
            case "GET_HOME", "GET_SETTINGS_BACK" -> execute(menuHandler.getStartMenu(chatId));
            case "GET_CURRENCY_SETTINGS" -> execute(menuHandler.getCurrencyMenu(chatId, messageId));
            case "GET_NOTIFICATION_SETTINGS" -> execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            case "SET_NOTIFICATION_TIME_09" -> {
                userService.updateUserNotificationTime(chatId, 9);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_10" -> {
                userService.updateUserNotificationTime(chatId, 10);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_11" -> {
                userService.updateUserNotificationTime(chatId, 11);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_12" -> {
                userService.updateUserNotificationTime(chatId, 12);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_13" -> {
                userService.updateUserNotificationTime(chatId, 13);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_14" -> {
                userService.updateUserNotificationTime(chatId, 14);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_15" -> {
                userService.updateUserNotificationTime(chatId, 15);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_16" -> {
                userService.updateUserNotificationTime(chatId, 16);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_17" -> {
                userService.updateUserNotificationTime(chatId, 17);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "SET_NOTIFICATION_TIME_18" -> {
                userService.updateUserNotificationTime(chatId, 18);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "OFF_NOTIFICATION" -> {
                userService.updateUserNotificationTime(chatId, null);
                execute(menuHandler.getNotificationTimeMenu(chatId, messageId));
            }
            case "GET_BANK_SETTINGS" -> execute(menuHandler.getBankMenu(chatId, messageId));
            case "SET_MONOBANK" -> {
                userService.updateUserCurrentBank(chatId, Bank.MONOBANK);
                execute(menuHandler.getBankMenu(chatId, messageId));
            }
            case "SET_NBU" -> {
                userService.updateUserCurrentBank(chatId, Bank.NBU);
                execute(menuHandler.getBankMenu(chatId, messageId));
            }
            case "SET_PRIVATBANK" -> {
                userService.updateUserCurrentBank(chatId, Bank.PRIVATBANK);
                execute(menuHandler.getBankMenu(chatId, messageId));
            }
            case "GET_DECIMAL_SETTINGS" -> execute(menuHandler.getDecimalPlacesMenu(chatId, messageId));
            case "SET_PRECISION_2" -> {
                userService.updateUserDecimalPlaces(chatId, 2);
                execute(menuHandler.getDecimalPlacesMenu(chatId, messageId));
            }
            case "SET_PRECISION_3" -> {
                userService.updateUserDecimalPlaces(chatId, 3);
                execute(menuHandler.getDecimalPlacesMenu(chatId, messageId));
            }
            case "SET_PRECISION_4" -> {
                userService.updateUserDecimalPlaces(chatId, 4);
                execute(menuHandler.getDecimalPlacesMenu(chatId, messageId));
            }
        }
    }

    private void handleTextMessage(Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();

        if (!update.getMessage().getText().equals("/start")) {
            execute(menuHandler.getStartMenu(chatId));
            return;
        }

        if (userService.userExistsById(chatId)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Welcome back! You are already registered.");
            execute(message);
        } else {
            String username = update.getMessage().getFrom().getUserName();
            String firstName = update.getMessage().getFrom().getFirstName();
            String lastName = update.getMessage().getFrom().getLastName();

            userService.saveUser(User.builder()
                    .id(chatId)
                    .registrationDate(LocalDate.now(ZoneId.of("Europe/Kiev")))
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .currentBank(Bank.PRIVATBANK)
                    .currency(Collections.singletonList(Currency.USD))
                    .decimalPlaces(2)
                    .build()
            );
        }

        SendMessage startMenuMessage = menuHandler.getStartMenu(chatId);
        execute(startMenuMessage);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
