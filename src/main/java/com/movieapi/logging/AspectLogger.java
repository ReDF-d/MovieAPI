package com.movieapi.logging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapi.payload.ResponsePayload;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AspectLogger {
	private static final Logger logger = LoggerFactory.getLogger(AspectLogger.class);
	
	private final ObjectMapper mapper;
	
	public AspectLogger(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@Pointcut("execution(@(@org.springframework.web.bind.annotation.RequestMapping *) * *(..)) && execution(* com.movieapi.controller..*(..))")
	private void restControllerPointcut() {
	}
	
	@Pointcut("execution(* com.movieapi.exception.GlobalExceptionHandler.handleException(..))")
	private void exceptionHandlerPointcut() {
	}
	
	@Pointcut("execution(* org.springframework.data.repository.CrudRepository+.*(..))))")
	private void databaseAccessPointcut() {
	}
	
	@AfterReturning(value = "databaseAccessPointcut()", returning = "result")
	private void logDatabaseAccess(JoinPoint joinPoint, Object result) {
		Map<String, Object> parameters = getParameters(joinPoint);
		try {
			logger.info("Database access {}, parameters {} ,result {}", joinPoint, mapper.writeValueAsString(parameters), result);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}
	
	@AfterReturning(pointcut = "exceptionHandlerPointcut()", returning = "entity")
	private void logExceptionHandler(ResponseEntity<?> entity) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		try {
			logger.info("Request path: {}, method: {}",
					request.getContextPath() + request.getServletPath(), request.getMethod());
			logger.info("Response path: {}, method: {}, response: {}",
					request.getContextPath() + request.getServletPath(), request.getMethod(), mapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Before("restControllerPointcut()")
	private void logMethod(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, Object> parameters = getParameters(joinPoint);
		try {
			logger.info("Request path: {}, method: {}, arguments: {} ",
					request.getContextPath() + request.getServletPath(), request.getMethod(), mapper.writeValueAsString(parameters));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}
	
	@AfterReturning(pointcut = "restControllerPointcut()", returning = "entity")
	private void logMethodAfter(ResponsePayload<?> entity) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		try {
			logger.info("Response path: {}, method: {}, response: {}",
					request.getContextPath() + request.getServletPath(), request.getMethod(), mapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}
	
	private Map<String, Object> getParameters(JoinPoint joinPoint) {
		CodeSignature signature = (CodeSignature) joinPoint.getSignature();
		HashMap<String, Object> map = new HashMap<>();
		String[] parameterNames = signature.getParameterNames();
		
		for (int i = 0; i < parameterNames.length; i++) {
			map.put(parameterNames[i], joinPoint.getArgs()[i]);
		}
		return map;
	}
}
