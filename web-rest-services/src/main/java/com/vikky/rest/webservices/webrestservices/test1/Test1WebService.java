package com.vikky.rest.webservices.webrestservices.test1;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class Test1WebService {
	
	public static void main(String[] args) {
		
		SpringApplication.run(Test1WebService.class, args);
		
	}
	
	
	@Bean 
	public LocaleResolver localeResolver() {
		SessionLocaleResolver lr = new SessionLocaleResolver();
		
		lr.setDefaultLocale(Locale.US);
		return lr;
	}
	
	@Bean
	public ResourceBundleMessageSource messageResource() {
	
		ResourceBundleMessageSource rm = new ResourceBundleMessageSource();
		rm.setBasename("messages");
		
		return rm;
	}

}
