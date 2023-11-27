package ru.torgov44.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.torgov44.currency.config.CurrencyClientConfig;

@SpringBootApplication
@EnableConfigurationProperties(CurrencyClientConfig.class)
public class CurrencyRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyRateApplication.class, args);
    }

}
