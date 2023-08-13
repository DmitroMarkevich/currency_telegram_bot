package com.example.demo.service.request_handlers;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyRate;
import com.example.demo.model.json_parsers.NbuCurrencyParser;
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
public class NbuSendRequest implements ICurrencyService {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    public List<CurrencyRate> getRate() throws IOException {
        String currencyResponse = Jsoup.connect(URL)
                .ignoreContentType(true)
                .get()
                .body()
                .text();

        Type type = new TypeToken<List<NbuCurrencyParser>>() {
        }.getType();
        List<NbuCurrencyParser> currencyNbuDtoList = new Gson().fromJson(currencyResponse, type);

        return currencyNbuDtoList.stream()
                .filter(curr -> Currency.USD.equals(curr.getCc()) || Currency.EUR.equals(curr.getCc()))
                .map(item -> new CurrencyRate(Bank.NBU, item.getCc(), item.getRate(), item.getRate(), LocalDateTime.now(ZoneId.of("Europe/Kiev"))))
                .toList();
    }
}
