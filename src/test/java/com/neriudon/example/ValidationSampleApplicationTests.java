package com.neriudon.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.neriudon.example.code.IntCode;
import com.neriudon.example.code.StringCode;
import com.neriudon.example.validator.AcceptedIntegerValues;
import com.neriudon.example.validator.AcceptedStringValues;

import java.util.Arrays;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationSampleApplicationTests {

	private static final String STRING_EXIST_CODE = "1";
	private static final String STRING_NOT_EXIST_CODE = "5";
	private static final int INT_EXIST_CODE = 4;
	private static final int INT_NOT_EXIST_CODE = 1;

	private ValidatorFactory validatorFactory;
	private Validator validator;

	@Before
	public void setup() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void existStringCode() throws Exception {
		StringCodeSample code = new StringCodeSample(STRING_EXIST_CODE);
		Set<ConstraintViolation<StringCodeSample>> result = validator.validate(code);
		assertThat(result.isEmpty(), is(true));
	}

	@Test
	public void notExistStringCode() throws Exception {
		StringCodeSample code = new StringCodeSample(STRING_NOT_EXIST_CODE);
		Set<ConstraintViolation<StringCodeSample>> result = validator.validate(code);
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next().getInvalidValue(), is(STRING_NOT_EXIST_CODE));
	}

	@Test
	public void existIntCode() throws Exception {
		IntCodeSample code = new IntCodeSample(INT_EXIST_CODE);
		Set<ConstraintViolation<IntCodeSample>> result = validator.validate(code);
		assertThat(result.isEmpty(), is(true));
	}

	@Test
	public void notExistIntCode() throws Exception {
		IntCodeSample code = new IntCodeSample(INT_NOT_EXIST_CODE);
		Set<ConstraintViolation<IntCodeSample>> result = validator.validate(code);
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next().getInvalidValue(), is(INT_NOT_EXIST_CODE));
	}

	@Test
	public void acceptedStringValuesNormal() {
		AcceptedStringValuesSample sample = new AcceptedStringValuesSample("1");
		Set<ConstraintViolation<AcceptedStringValuesSample>> result = validator.validate(sample);
		assertThat(result.isEmpty(), is(true));
	}

	@Test
	public void acceptedStringValuesNg() {
		AcceptedStringValuesSample sample = new AcceptedStringValuesSample("0");
		Set<ConstraintViolation<AcceptedStringValuesSample>> result = validator.validate(sample);
		assertThat(result.size(), is(1));
		result.stream().forEach(r -> {
			assertThat(r.getInvalidValue(), is("0"));
			System.out.println(r.getMessage());
		});
	}

	@Test
	public void acceptedIntegerValuesNormal() {
		AcceptedIntegerValuesSample sample = new AcceptedIntegerValuesSample(1);
		Set<ConstraintViolation<AcceptedIntegerValuesSample>> result = validator.validate(sample);
		assertThat(result.isEmpty(), is(true));
	}

	@Test
	public void acceptedIntegerValuesNg() {
		AcceptedIntegerValuesSample sample = new AcceptedIntegerValuesSample(0);
		Set<ConstraintViolation<AcceptedIntegerValuesSample>> result = validator.validate(sample);
		assertThat(result.size(), is(1));
		result.stream().forEach(r -> {
			assertThat(r.getInvalidValue(), is(0));
			System.out.println(r.getMessage());
			
		});
	}

	private static class StringCodeSample {
		@CodeExists(StringCode.class)
		private String code;

		public StringCodeSample(String code) {
			this.code = code;
		}
	}

	private static class IntCodeSample {
		@CodeExists(IntCode.class)
		private int code;

		public IntCodeSample(int code) {
			this.code = code;
		}
	}

	private static class AcceptedIntegerValuesSample {

		@AcceptedIntegerValues({ 1, 2, 3, 4, 5 })
		private int code;

		public AcceptedIntegerValuesSample(int code) {
			this.code = code;
		}
	}

	private static class AcceptedStringValuesSample {

		@AcceptedStringValues({ "1", "2", "3", "4", "5" })
		private String code;

		public AcceptedStringValuesSample(String code) {
			this.code = code;
		}
	}

}
