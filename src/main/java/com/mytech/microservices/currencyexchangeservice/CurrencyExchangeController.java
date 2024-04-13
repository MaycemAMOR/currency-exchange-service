package com.mytech.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;
    @Autowired
    private ExchangeValueRepository exchangeValueRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveexchangeValue(@PathVariable String from, @PathVariable String to) {
        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
        if (exchangeValue == null)
            throw new ExchageValueNotFoundException(" from : " + from + " to : " + to);
        exchangeValue.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port"))));

        return exchangeValue;
    }
}
