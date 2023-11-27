package ru.torgov44.currency.controller;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.torgov44.currency.service.CurrencyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency/rate")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("/{code}")
    public BigDecimal requestByCurrencyCode(@PathVariable String code){
         return currencyService.requestByCurrencyCode(code);
    }

}
