package com.vikky.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CurrencyConversionController {

	
	@GetMapping("/currency-convertor/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getEx(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		System.out.println("Quantity is " + quantity);
		Map<String, String> uriVars = new HashMap<String, String>();
		uriVars.put("to", to);
		uriVars.put("from", from);

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class, uriVars);
		
		CurrencyConversionBean response = responseEntity.getBody();
		return new CurrencyConversionBean(response.getId(), from, to, response.getMultiple(), quantity, quantity.multiply(response.getMultiple()), 0);
	}
}
