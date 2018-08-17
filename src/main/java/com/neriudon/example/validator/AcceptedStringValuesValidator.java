package com.neriudon.example.validator;

import java.util.Arrays;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AcceptedStringValuesValidator implements ConstraintValidator<AcceptedStringValues, String> {

	// accepted values array
	private String[] validValues;

	@Override
	public void initialize(AcceptedStringValues constraintAnnotation) {
		validValues = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		// check to exist value or not in accepted values array
		return Arrays.stream(validValues).anyMatch(s -> Objects.equals(value, s));
	}
}
