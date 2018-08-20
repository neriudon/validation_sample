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
@Constraint(validatedBy = { MaxFromPropertyValidator.class })
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface MaxFromProperty {

	String message() default "{com.neriudon.example.validator.MaxFromProperty.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String value();

	@Target({ METHOD, FIELD })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		MaxFromProperty[] value();
	}
}
