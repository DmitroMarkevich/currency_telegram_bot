package com.example.demo.model.json_parsers;

import com.example.demo.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class NbuCurrencyParser {
    private Long r030;
    private String txt;
    private BigDecimal rate;
    private Currency cc;
    private String exchangedate;
}
