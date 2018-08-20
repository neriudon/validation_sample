package com.neriudon.example.config;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@PropertySource("sample.properties")
public class TestConfig {

	@Bean
	public Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}
}
