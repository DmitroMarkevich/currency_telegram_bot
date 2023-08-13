package com.example.demo.model.json_parsers;

import com.example.demo.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonobankCurrencyParser {
    private Currency currencyCodeA;
    private Currency currencyCodeB;
    private Long date;
    private BigDecimal rateBuy;
    private BigDecimal rateCross;
    private BigDecimal rateSell;
}
