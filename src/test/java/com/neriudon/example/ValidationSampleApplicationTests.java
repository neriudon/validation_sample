package com.neriudon.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.neriudon.example.code.IntCode;
import com.neriudon.example.code.StringCode;
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
    assertThat(true, is(result.isEmpty()));
  }

  @Test
  public void notExistStringCode() throws Exception {
    StringCodeSample code = new StringCodeSample(STRING_NOT_EXIST_CODE);
    Set<ConstraintViolation<StringCodeSample>> result = validator.validate(code);
    assertThat(1, is(result.size()));
    assertThat(STRING_NOT_EXIST_CODE, is(result.iterator().next().getInvalidValue()));
  }

  @Test
  public void existIntCode() throws Exception {
    IntCodeSample code = new IntCodeSample(INT_EXIST_CODE);
    Set<ConstraintViolation<IntCodeSample>> result = validator.validate(code);
    assertThat(true, is(result.isEmpty()));
  }

  @Test
  public void notExistIntCode() throws Exception {
    IntCodeSample code = new IntCodeSample(INT_NOT_EXIST_CODE);
    Set<ConstraintViolation<IntCodeSample>> result = validator.validate(code);
    assertThat(1, is(result.size()));
    assertThat(INT_NOT_EXIST_CODE, is(result.iterator().next().getInvalidValue()));
  }

  /**
   * the class has String type code.
   */
  private static class StringCodeSample {
    @CodeExists(StringCode.class)
    private String code;

    public StringCodeSample(String code) {
      this.code = code;
    }
  }

  /**
   * the class has int type code.
   */
  private static class IntCodeSample {
    @CodeExists(IntCode.class)
    private int code;

    public IntCodeSample(int code) {
      this.code = code;
    }
  }
}
