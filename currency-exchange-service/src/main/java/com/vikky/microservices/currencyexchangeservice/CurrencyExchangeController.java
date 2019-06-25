package com.vikky.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue getEx(@PathVariable String from, @PathVariable String to) {
		
		return repository.findByFromAndTo(from, to);

//		ExchangeValue ev = new ExchangeValue(Long.valueOf(1000), "USD", "INR", new BigDecimal(70));
//		ev.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
//		return ev;
	}
}
