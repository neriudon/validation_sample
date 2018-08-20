package com.neriudon.example.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;

/**
 * check input value.
 */
public class MaxFromPropertyValidator extends ApplicationObjectSupport
		implements ConstraintValidator<MaxFromProperty, Long> {

	private long max;

	private final PropertyResolver resolver;

	public MaxFromPropertyValidator(List<PropertySourcesPlaceholderConfigurer> configurers, Environment environment) {

		if (configurers != null) {
			MutablePropertySources propertySources = new MutablePropertySources();
			configurers.forEach(c -> c.getAppliedPropertySources().forEach(p -> {
				// named unique name.
				// Because MutablePropertySources override propertySources if defined same name.
				CompositePropertySource temp = new CompositePropertySource(
						c.toString().concat("$").concat(p.getName()));
				temp.addPropertySource(p);
				propertySources.addLast(temp);
			}));
			this.resolver = new PropertySourcesPropertyResolver(propertySources);
		} else {
			this.resolver = environment;
		}
	}

	@Override
	public void initialize(MaxFromProperty constraintAnnotation) {
		max = getMaxValue(constraintAnnotation.value());
	}

	/**
	 * return true value is less than or equal to max, or null.
	 */
	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value <= max;
	}

	/**
	 * return max value.<br>
	 * if no value mapped key, convert key to long.
	 */
	private long getMaxValue(String key) {
		String value = resolver.getProperty(key, key);
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"failed to get int value from Property(key:" + key + ", value:" + value + ")");
		}
	}
}
