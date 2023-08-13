package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "currency_rates")
public class CurrencyRate {
    private Bank bank;
    private Currency currency;
    private BigDecimal buy;
    private BigDecimal sell;
    private LocalDateTime rateDate;
}
