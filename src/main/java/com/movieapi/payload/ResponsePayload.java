package com.movieapi.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.List;

@Hidden
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePayload<T> {
	
	private T response;
	private String statusCode;
	private int statusCodeValue;
	private String timestamp;
	private Integer pageNo;
	private Integer pageSize;
	private List<String> errors;
	
	public ResponsePayload() {
		setStatusCode(HttpStatus.OK.getReasonPhrase());
		setStatusCodeValue(HttpStatus.OK.value());
		setTimestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()));
	}
	
	public T getResponse() {
		return response;
	}
	
	public void setResponse(T response) {
		this.response = response;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public int getStatusCodeValue() {
		return statusCodeValue;
	}
	
	public void setStatusCodeValue(int statusCodeValue) {
		this.statusCodeValue = statusCodeValue;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
