package com.movieapi.validation.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {
	
	private String pattern;
	
	private static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			if (value != null) {
				date = sdf.parse(value);
				if (!value.equals(sdf.format(date))) {
					date = null;
				}
			}
			
		} catch (ParseException ex) {
		}
		return date != null;
	}
	
	@Override
	public void initialize(ValidDateFormat annotation) {
		this.pattern = annotation.pattern();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (value == null) {
			return true;
		}
		
		boolean validDate = isValidFormat(pattern, value);
		if (!validDate)
			customMessageForValidation(context);
		
		return validDate;
	}
	
	private void customMessageForValidation(ConstraintValidatorContext constraintContext) {
		constraintContext.buildConstraintViolationWithTemplate("Неверная дата").addConstraintViolation();
	}
}