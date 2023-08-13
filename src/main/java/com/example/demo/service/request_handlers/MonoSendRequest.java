package com.example.demo.service.request_handlers;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyRate;
import com.example.demo.model.json_parsers.MonobankCurrencyParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class MonoSendRequest implements ICurrencyService {
    private static final String URL = "https://api.monobank.ua/bank/currency";

    @Override
    public List<CurrencyRate> getRate() throws IOException {
        String currencyResponse = Jsoup.connect(URL)
                .ignoreContentType(true)
                .get()
                .body()
                .text()
                .replaceAll(":840", ":USD")
                .replaceAll(":978", ":EUR")
                .replaceAll(":980", ":UAH");

        Type type = new TypeToken<List<MonobankCurrencyParser>>() {
        }.getType();
        List<MonobankCurrencyParser> monobankCurrencyDtoList = new Gson().fromJson(currencyResponse, type);

        return monobankCurrencyDtoList.stream()
                .filter(curr -> (Currency.UAH.equals(curr.getCurrencyCodeB()) &&
                        (Currency.EUR.equals(curr.getCurrencyCodeA()) || Currency.USD.equals(curr.getCurrencyCodeA()))))
                .map(item -> new CurrencyRate(Bank.MONOBANK, item.getCurrencyCodeA(),
                        item.getRateBuy(), item.getRateSell(), LocalDateTime.now(ZoneId.of("Europe/Kiev"))))
                .toList();
    }
}
