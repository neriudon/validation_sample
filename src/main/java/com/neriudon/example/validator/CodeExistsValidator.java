package com.neriudon.example.validator;

import com.neriudon.example.CodeExists;
import com.neriudon.example.code.Code;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class CodeExistsValidator<T> implements ConstraintValidator<CodeExists, T> {

  Code<T> target;

  @Override
  public void initialize(CodeExists constraintAnnotation) {
    try {
      // get instance of targeted class.
      target = convert(constraintAnnotation.value().getDeclaredField("INSTANCE").get(this));
    } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
        | SecurityException e) {
      // this code is sample. so, you must handle error properly.
      e.printStackTrace();
    }
  }

  @Override
  public boolean isValid(T value, ConstraintValidatorContext context) {
    // return exists method's result.
    return target.exists(value);
  }

  @SuppressWarnings("unchecked")
  private Code<T> convert(Object o) {
    return (Code<T>) o;
  }

}
