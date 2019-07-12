package com.vikky.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigController {
	
	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public LimitConfig limitController() {
		return new LimitConfig(configuration.getMaximum(),configuration.getMinimum());
	}
	
	@GetMapping("/faultTolerance")
	@HystrixCommand(fallbackMethod = "fallBackMethod")
	public LimitConfig faultTolerance() {
		throw new RuntimeException();
	}
	
	public LimitConfig fallBackMethod() {
		return new LimitConfig(100,10);
	}

}
