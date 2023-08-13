package com.example.demo.telegram;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyRate;
import com.example.demo.model.User;
import com.example.demo.service.CurrencyRateService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageBuilder {
    private final CurrencyRateService currencyRateService;
    private final UserService userService;

    public String getCurrencyRateByUserSettings(Long userId) throws IOException {
        List<CurrencyRate> cachedCurrencyRates = currencyRateService.getAllCurrencies();

        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isEmpty()) {
            return "You are not registered. Please start by typing /start.";
        }

        User user = userOptional.get();
        Bank bank = user.getCurrentBank();
        List<Currency> userCurrencies = user.getCurrency();

        if (Objects.isNull(cachedCurrencyRates) || cachedCurrencyRates.isEmpty()) {
            cachedCurrencyRates = currencyRateService.getAllCurrencies();
        }

        if (Objects.nonNull(cachedCurrencyRates) && !cachedCurrencyRates.isEmpty() && currencyRateService.isCurrencyRateOutdated(cachedCurrencyRates)) {
            cachedCurrencyRates = currencyRateService.updateCurrencyRates();
        } else if (currencyRateService.isCurrencyRateOutdated(cachedCurrencyRates)) {
            cachedCurrencyRates = currencyRateService.updateCurrencyRates();
        }

        return buildCurrencyRateString(user, currencyRateService.getCurrencyRatesByBankAndCurrencies(bank, cachedCurrencyRates, userCurrencies));
    }

    private String buildCurrencyRateString(User user, List<CurrencyRate> currencyRates) {
        Integer decimalPlaces = user.getDecimalPlaces();

        StringBuilder result = new StringBuilder();
        result.append("Currency from ").append(currencyRates.get(0).getBank()).append("\n");
        result.append(getSeparatorLine());

        String format = "%." + decimalPlaces + "f";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (CurrencyRate currencyRate : currencyRates) {
            result.append(currencyRate.getCurrency()).append("/UAH:\n");
            result.append("Buy: ").append(String.format(format, currencyRate.getBuy())).append("\n");
            result.append("Sell: ").append(String.format(format, currencyRate.getSell())).append("\n");
            result.append(getSeparatorLine());
        }

        result.append("Last upd in: ").append(currencyRates.get(0).getRateDate().format(formatter));
        return result.toString();
    }

    private String getSeparatorLine() {
        return "-----------------------------------------\n";
    }
}