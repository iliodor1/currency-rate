package ru.torgov44.currency.service;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal requestByCurrencyCode(String code);
}
