package com.movieapi.payload;

import com.movieapi.validation.date.ValidDateFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "JSON с датой выхода фильма")
public class MovieReleaseDatePayload {
	
	@ValidDateFormat(pattern = "dd-MM-yyyy")
	@Schema(description = "Дата выхода фильма в формате dd-MM-yyyy")
	@NotBlank(message = "Дата выхода фильма не должна быть пустой")
	String releaseDate;
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
}
