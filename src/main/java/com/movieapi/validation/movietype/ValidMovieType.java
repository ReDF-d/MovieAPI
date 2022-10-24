package com.movieapi.validation.movietype;

import com.movieapi.model.enums.MovieType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MovieTypeEnumValidator.class)
public @interface ValidMovieType {
	
	List<String> constants = MovieType.getConstants();
	
	String message() default "Неверный тип фильма";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
