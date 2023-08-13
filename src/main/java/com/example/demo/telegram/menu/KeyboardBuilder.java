package com.example.demo.telegram.menu;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class KeyboardBuilder {
    private static final String BACK_ARROW = "◀️Back";
    private static final String HOME_ICON = "\uD83C\uDFE0Home";
    private static final String GET_HOME_ACTION = "GET_HOME";
    private final UserService userService;

    @Autowired
    public KeyboardBuilder(UserService userService) {
        this.userService = userService;
    }

    public InlineKeyboardMarkup getMainKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton("Get actual currency", "GET_CURRENCY"));
        rowsInline.add(createButton("User settings", "GET_SETTINGS"));
        rowsInline.add(createButton("ℹ️Information", "GET_INFO"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getSettingsKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton("Currency", "GET_CURRENCY_SETTINGS"));
        rowsInline.add(createButton("Bank", "GET_BANK_SETTINGS"));
        rowsInline.add(createButton("Precision", "GET_DECIMAL_SETTINGS"));
        rowsInline.add(createButton("Notification time", "GET_NOTIFICATION_SETTINGS"));
        rowsInline.add(createButton(BACK_ARROW, "GET_SETTINGS_BACK"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getCurrencyKeyboard(Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton(checkMarkForCurrency(chatId, Currency.USD), "SET_USD")));
        rowsInline.add((createButton(checkMarkForCurrency(chatId, Currency.EUR), "SET_EUR")));
        rowsInline.add((createButton(BACK_ARROW, "GET_CURRENCY_BACK")));
        rowsInline.add(createButton(HOME_ICON, GET_HOME_ACTION));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getBankKeyboard(Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton(checkMarkForBank(chatId, Bank.MONOBANK), "SET_MONOBANK"));
        rowsInline.add(createButton(checkMarkForBank(chatId, Bank.NBU), "SET_NBU"));
        rowsInline.add(createButton(checkMarkForBank(chatId, Bank.PRIVATBANK), "SET_PRIVATBANK"));
        rowsInline.add(createButton(BACK_ARROW, "GET_BANK_BACK"));
        rowsInline.add(createButton(HOME_ICON, GET_HOME_ACTION));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getDecimalPlacesKeyboard(Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton(checkMarkForDecimalPlaces(chatId, 2), "SET_PRECISION_2"));
        rowsInline.add(createButton(checkMarkForDecimalPlaces(chatId, 3), "SET_PRECISION_3"));
        rowsInline.add(createButton(checkMarkForDecimalPlaces(chatId, 4), "SET_PRECISION_4"));
        rowsInline.add(createButton(BACK_ARROW, "GET_SETTINGS"));
        rowsInline.add(createButton(HOME_ICON, GET_HOME_ACTION));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getNotificationTimeKeyboard(Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline4 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline5 = new ArrayList<>();
        rowInline1.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "09:00")).callbackData("SET_NOTIFICATION_TIME_09").build());
        rowInline1.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "10:00")).callbackData("SET_NOTIFICATION_TIME_10").build());
        rowInline1.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "11:00")).callbackData("SET_NOTIFICATION_TIME_11").build());
        rowInline2.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "12:00")).callbackData("SET_NOTIFICATION_TIME_12").build());
        rowInline2.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "13:00")).callbackData("SET_NOTIFICATION_TIME_13").build());
        rowInline2.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "14:00")).callbackData("SET_NOTIFICATION_TIME_14").build());
        rowInline3.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "15:00")).callbackData("SET_NOTIFICATION_TIME_15").build());
        rowInline3.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "16:00")).callbackData("SET_NOTIFICATION_TIME_16").build());
        rowInline3.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "17:00")).callbackData("SET_NOTIFICATION_TIME_17").build());
        rowInline4.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "18:00")).callbackData("SET_NOTIFICATION_TIME_18").build());
        rowInline4.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(chatId, "OFF")).callbackData("OFF_NOTIFICATION").build());
        rowInline4.add(InlineKeyboardButton.builder().text(BACK_ARROW).callbackData("GET_NOTIFICATION_BACK").build());
        rowInline5.add(InlineKeyboardButton.builder().text(HOME_ICON).callbackData(GET_HOME_ACTION).build());
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);
        rowsInline.add(rowInline5);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private List<InlineKeyboardButton> createButton(String command, String callBack) {
        return List.of(InlineKeyboardButton.builder()
                .text(command)
                .callbackData(callBack)
                .build());
    }

    private String checkMarkForNotificationTime(Long chatId, String notificationTime) {
        Integer userNotificationTime = userService.getUserNotificationTime(chatId);

        if (userNotificationTime == null) {
            return notificationTime.equals("OFF") ? notificationTime + "✅" : notificationTime;
        }

        if (notificationTime.equals("10:00") && userNotificationTime == 10) {
            return notificationTime + "✅";
        }

        if (notificationTime.equals("OFF")) {
            return notificationTime;
        }

        Integer parsedNotificationTime = Integer.parseInt(notificationTime.split(":")[0]);
        if (userNotificationTime.equals(parsedNotificationTime)) {
            return notificationTime + "✅";
        }

        return notificationTime;
    }

    private String checkMarkForCurrency(Long userId, Currency currencyName) {
        List<Currency> userCurrencySetting = userService.getUserCurrency(userId);
        return userCurrencySetting.contains(currencyName) ? currencyName + "✅" : currencyName.name();
    }

    private String checkMarkForDecimalPlaces(Long chatId, Integer decimalPlaces) {
        Integer userDecimalPlaces = userService.getUserDecimalPlaces(chatId);
        return Objects.equals(userDecimalPlaces, decimalPlaces) ? decimalPlaces + "✅" : String.valueOf(decimalPlaces);
    }

    private String checkMarkForBank(Long chatId, Bank bankName) {
        Bank userBank = userService.getUserBank(chatId);
        return userBank == bankName ? bankName + "✅" : bankName.name();
    }
}