package com.vikky.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigController {
	
	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public LimitConfig limitController() {
		return new LimitConfig(configuration.getMaximum(),configuration.getMinimum());
	}

}
