package com.movieapi.payload;

import com.movieapi.validation.movietype.ValidMovieType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "JSON с типом фильма")
public class MovieTypePayload {
	
	@Schema(description = "Тип фильма - допустимые значения: \"Полнометражный\", \"Короткометражный\",\"Сериал\"")
	@ValidMovieType
	@NotBlank(message = "Тип фильма не должен быть пустым")
	String type;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
