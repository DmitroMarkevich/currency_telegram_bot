package com.example.demo.service.request_handlers;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyRate;
import com.example.demo.model.json_parsers.PrivatbankCurrencyParser;
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
public class PrivatSendRequest implements ICurrencyService {
    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";

    @Override
    public List<CurrencyRate> getRate() throws IOException {
        String currencyResponse = Jsoup.connect(URL)
                .ignoreContentType(true)
                .get()
                .body()
                .text();

        Type type = new TypeToken<List<PrivatbankCurrencyParser>>() {
        }.getType();
        List<PrivatbankCurrencyParser> currencyPrivatDtoList = new Gson().fromJson(currencyResponse, type);

        return currencyPrivatDtoList.stream()
                .filter(curr -> Currency.UAH.equals(curr.getBase_ccy()) &&
                        (Currency.USD.equals(curr.getCcy()) || Currency.EUR.equals(curr.getCcy())))
                .map(item -> new CurrencyRate(Bank.PRIVATBANK, item.getCcy(), item.getBuy(), item.getSale(), LocalDateTime.now(ZoneId.of("Europe/Kiev"))))
                .toList();
    }
}
