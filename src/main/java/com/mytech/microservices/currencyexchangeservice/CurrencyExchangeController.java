package com.mytech.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private Environment environment;
    @Autowired
    private ExchangeValueRepository exchangeValueRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        logger.info("retrieveExchangeValue called with {} to {}", from, to);
        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
        if (exchangeValue == null)
            throw new ExchageValueNotFoundException("Unable to Find data for" + "from : " + from + " to : " + to);

        //CHANGE-KUBERNETES
        String port = environment.getProperty("local.server.port");
        String host = environment.getProperty("HOSTNAME");
        String version = "v11";

        exchangeValue.setEnvironment(port + " " + version + " " + host);

        return exchangeValue;
    }
}
