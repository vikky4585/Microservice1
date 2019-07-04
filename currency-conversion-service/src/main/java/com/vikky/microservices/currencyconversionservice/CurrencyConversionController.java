package com.vikky.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@GetMapping("/currency-convertor/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getEx(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		System.out.println("Quantity is " + quantity);
		Map<String, String> uriVars = new HashMap<String, String>();
		uriVars.put("to", to);
		uriVars.put("from", from);
		

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class, uriVars);
		
		CurrencyConversionBean response = responseEntity.getBody();
		
		logger.info("{}", response);

		return new CurrencyConversionBean(response.getId(), from, to, response.getMultiple(), quantity, quantity.multiply(response.getMultiple()), 0);
	}
	
	
	@GetMapping("/currency-convertor-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getExFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		CurrencyConversionBean response = this.proxy.getEx(from, to);
		logger.info("{}", response);

		return new CurrencyConversionBean(response.getId(), from, to, response.getMultiple(), quantity, quantity.multiply(response.getMultiple()), 0);
	}
}
