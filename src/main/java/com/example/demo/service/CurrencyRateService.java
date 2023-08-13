package com.example.demo.service;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyRate;
import com.example.demo.repository.CurrencyRateRepository;
import com.example.demo.service.request_handlers.MonoSendRequest;
import com.example.demo.service.request_handlers.NbuSendRequest;
import com.example.demo.service.request_handlers.PrivatSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;
    private final MonoSendRequest monoSendRequest;
    private final PrivatSendRequest privatSendRequest;
    private final NbuSendRequest nbuSendRequest;

    @Transactional
    public List<CurrencyRate> updateCurrencyRates() throws IOException {
        List<CurrencyRate> currencyRates = new ArrayList<>();
        currencyRates.addAll(monoSendRequest.getRate());
        currencyRates.addAll(privatSendRequest.getRate());
        currencyRates.addAll(nbuSendRequest.getRate());

        currencyRateRepository.deleteAll();
        currencyRateRepository.saveAll(currencyRates);
        return currencyRates;
    }

    public List<CurrencyRate> getAllCurrencies() {
        return currencyRateRepository.findAll();
    }

    public List<CurrencyRate> getCurrencyRatesByBankAndCurrencies(Bank bank, List<CurrencyRate> currencyRates, List<Currency> currencies) {
        return currencyRates.stream()
                .filter(currencyRate -> isMatchingCurrency(currencyRate, bank, currencies))
                .toList();
    }

    private boolean isMatchingCurrency(CurrencyRate currencyRate, Bank bank, List<Currency> currencies) {
        return currencyRate.getBank().equals(bank) && currencies.contains(currencyRate.getCurrency());
    }

    public boolean isCurrencyRateOutdated(List<CurrencyRate> currencyRates) {
        LocalDateTime rateDateTime = currencyRates.get(0).getRateDate();
        Duration duration = Duration.between(rateDateTime, LocalDateTime.now(ZoneId.of("Europe/Kiev")));
        return duration.toMinutes() >= 5.5;
    }
}
