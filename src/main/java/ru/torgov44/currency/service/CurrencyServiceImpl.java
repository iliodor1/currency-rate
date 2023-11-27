package ru.torgov44.currency.service;

import static java.util.stream.Collectors.toMap;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;
import ru.torgov44.currency.client.HttpCurrencyDateRateClient;
import ru.torgov44.currency.schema.ValCurs;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final Cache<LocalDate, Map<String, BigDecimal>> cache;

    private final HttpCurrencyDateRateClient client;


    public CurrencyServiceImpl(HttpCurrencyDateRateClient client) {
        this.client = client;
        this.cache = CacheBuilder.newBuilder().build();
    }

    @Override
    public BigDecimal requestByCurrencyCode(String code) {
        try {
            return cache.get(LocalDate.now(), this::callAllByCurrentDate).get(code);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, BigDecimal> callAllByCurrentDate() {
        var xml = client.requestByDate(LocalDate.now());
        ValCurs response = unmarshall(xml);
        return response.getValute()
                       .stream()
                       .collect(toMap(ValCurs.Valute::getCharCode, item -> parseWithLocale(item.getValue())));
    }

    private BigDecimal parseWithLocale(String currency) {
        try {
            double v = NumberFormat.getNumberInstance(Locale.getDefault()).parse(currency).doubleValue();
            return BigDecimal.valueOf(v);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private ValCurs unmarshall(String xml) {
        try (StringReader reader = new StringReader(xml)) {
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            return (ValCurs) context.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}


