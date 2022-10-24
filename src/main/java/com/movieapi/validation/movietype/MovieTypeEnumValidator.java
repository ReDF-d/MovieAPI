package com.movieapi.validation.movietype;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MovieTypeEnumValidator implements ConstraintValidator<ValidMovieType, CharSequence> {
	private List<String> acceptedValues;
	
	@Override
	public void initialize(ValidMovieType annotation) {
		acceptedValues = ValidMovieType.constants;
	}
	
	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (value == null) {
			return true;
		}
		boolean contains = acceptedValues.contains(value.toString());
		if (!contains) {
			customMessageForValidation(context);
			return false;
		}
		return true;
	}
	
	private void customMessageForValidation(ConstraintValidatorContext constraintContext) {
		constraintContext.buildConstraintViolationWithTemplate("Неверный тип фильма").addConstraintViolation();
	}
}