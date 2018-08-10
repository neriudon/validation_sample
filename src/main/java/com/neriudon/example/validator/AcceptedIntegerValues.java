package com.neriudon.example.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { AcceptedIntegerValuesValidator.class })
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface AcceptedIntegerValues {
	String message() default "{com.neriudon.example.validator.AcceptedIntegerValues.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int[] value();

	@Target({ METHOD, FIELD })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		AcceptedIntegerValues[] value();
	}
}
