package com.neriudon.example.config;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@PropertySource("sample.properties")
public class TestConfig {

	@Bean
	public Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}
}
