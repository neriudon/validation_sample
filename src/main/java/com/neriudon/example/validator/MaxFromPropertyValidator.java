package com.neriudon.example.validator;

import java.math.BigDecimal;
import java.math.BigInteger;
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
public abstract class MaxFromPropertyValidator<T> extends ApplicationObjectSupport
		implements ConstraintValidator<MaxFromProperty, T> {

	protected long max;

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

	/**
	 * MaxFromPropertyValidator for Number
	 */
	public static class NumberMaxFromPropertyValidator extends MaxFromPropertyValidator<Number> {

		public NumberMaxFromPropertyValidator(List<PropertySourcesPlaceholderConfigurer> configurers,
				Environment environment) {
			super(configurers, environment);
		}

		@Override
		public boolean isValid(Number value, ConstraintValidatorContext context) {
			// null values are valid
			if (value == null) {
				return true;
			}

			// handling of NaN, positive infinity and negative infinity
			else if (value instanceof Double) {
				if ((Double) value == Double.NEGATIVE_INFINITY) {
					return true;
				} else if (Double.isNaN((Double) value) || (Double) value == Double.POSITIVE_INFINITY) {
					return false;
				}
			} else if (value instanceof Float) {
				if ((Float) value == Float.NEGATIVE_INFINITY) {
					return true;
				} else if (Float.isNaN((Float) value) || (Float) value == Float.POSITIVE_INFINITY) {
					return false;
				}
			}
			if (value instanceof BigDecimal) {
				return ((BigDecimal) value).compareTo(BigDecimal.valueOf(max)) != 1;
			} else if (value instanceof BigInteger) {
				return ((BigInteger) value).compareTo(BigInteger.valueOf(max)) != 1;
			} else {
				long longValue = value.longValue();
				return longValue <= max;
			}
		}
	}

	/**
	 * MaxFromPropertyValidator for CharSequernce
	 */
	public static class CharSequenceMaxFromPropertyValidator extends MaxFromPropertyValidator<CharSequence> {

		public CharSequenceMaxFromPropertyValidator(List<PropertySourcesPlaceholderConfigurer> configurers,
				Environment environment) {
			super(configurers, environment);
		}

		@Override
		public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
			// null values are valid
			if (value == null) {
				return true;
			}
			try {
				return new BigDecimal(value.toString()).compareTo(BigDecimal.valueOf(max)) != 1;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
	}
}
