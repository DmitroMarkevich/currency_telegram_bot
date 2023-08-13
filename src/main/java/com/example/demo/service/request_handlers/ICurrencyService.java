package com.example.demo.service.request_handlers;

import com.example.demo.model.CurrencyRate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ICurrencyService {
    List<CurrencyRate> getRate() throws IOException;
}
