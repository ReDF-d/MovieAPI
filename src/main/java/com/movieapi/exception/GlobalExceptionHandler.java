package com.movieapi.exception;

import com.movieapi.payload.ResponsePayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponsePayload<String>> handleException(NotFoundException e) {
		ResponsePayload<String> response = new ResponsePayload<>();
		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());
		response.setErrors(errors);
		response.setStatusCode(HttpStatus.NOT_FOUND.getReasonPhrase());
		response.setStatusCodeValue(HttpStatus.NOT_FOUND.value());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponsePayload<String>> handleException(Exception e) {
		ResponsePayload<String> response = new ResponsePayload<>();
		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());
		response.setErrors(errors);
		response.setStatusCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
		response.setStatusCodeValue(HttpStatus.BAD_REQUEST.value());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponsePayload<List<String>>> handleException(MethodArgumentNotValidException e) {
		List<String> errors = new ArrayList<>();
		BindingResult bindingResult = e.getBindingResult();
		for (ObjectError error : bindingResult.getAllErrors()) {
			String message = error.getDefaultMessage();
			errors.add(message);
		}
		ResponsePayload<List<String>> response = new ResponsePayload<>();
		response.setErrors(errors);
		response.setStatusCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
		response.setStatusCodeValue(HttpStatus.BAD_REQUEST.value());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
