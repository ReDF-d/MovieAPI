package com.movieapi.payload;

import com.movieapi.validation.date.ValidDateFormat;
import com.movieapi.validation.movietype.ValidMovieType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "JSON с данными фильма")
public class MoviePayload {
	
	@Schema(description = "Название фильма")
	@NotBlank(message = "Название фильма не должно быть пустым")
	private String name;
	
	@Schema(description = "Описание фильма")
	@NotBlank(message = "Описание фильма не должно быть пустым")
	private String description;
	
	@ValidMovieType
	@Schema(description = "Тип фильма - допустимые значения: \"Полнометражный\", \"Короткометражный\",\"Сериал\"")
	@NotBlank(message = "Тип фильма не должен быть пустым")
	private String type;
	
	@Pattern(regexp = "\\p{L}+", message = "Название жанра может включать в себя только буквы")
	@Schema(description = "Жанр фильма")
	@NotBlank(message = "Жанр фильма не должен быть пустым")
	private String genre;
	
	@ValidDateFormat(pattern = "dd-MM-yyyy")
	@Schema(description = "Дата выхода фильма в формате dd-MM-yyyy")
	@NotBlank(message = "Дата выхода фильма не должна быть пустой")
	private String releaseDate;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}
