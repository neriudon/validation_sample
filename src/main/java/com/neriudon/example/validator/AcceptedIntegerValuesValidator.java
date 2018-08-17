package com.neriudon.example.validator;

import java.util.Arrays;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class AcceptedIntegerValuesValidator implements ConstraintValidator<AcceptedIntegerValues, Integer> {

	// accepted values array
	private Integer[] validValues;

	@Override
	public void initialize(AcceptedIntegerValues constraintAnnotation) {
		validValues = ArrayUtils.toObject(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		context.unwrap(HibernateConstraintValidatorContext.class).addExpressionVariable("acceptedValuesToString", Arrays.toString(validValues));

		// check to exist value or not in accepted values array
		return Arrays.stream(validValues).anyMatch(s -> Objects.equals(value, s));
	}
}
