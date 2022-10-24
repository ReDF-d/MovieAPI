package com.movieapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "JSON с названием фильма")
public class MovieNamePayload {
	
	@Schema(description = "Название фильма")
	@NotBlank(message = "Название фильма не должно быть пустым")
	String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
