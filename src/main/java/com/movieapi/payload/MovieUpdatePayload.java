package com.movieapi.payload;

import com.movieapi.validation.date.ValidDateFormat;
import com.movieapi.validation.movietype.ValidMovieType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MovieUpdatePayload {
	
	@Schema(description = "Идентификатор")
	@NotNull(message = "Идентификатор фильма не должен быть пустым")
	private Long id;
	
	@Schema(description = "Название фильма")
	private String name;
	
	@Schema(description = "Описание фильма")
	private String description;
	
	@ValidMovieType
	@Schema(description = "Тип фильма - допустимые значения: \"Полнометражный\", \"Короткометражный\",\"Сериал\"")
	private String type;
	
	@Pattern(regexp = "\\p{L}+", message = "Название жанра может включать в себя только буквы")
	@Schema(description = "Жанр фильма")
	private String genre;
	
	@ValidDateFormat(pattern = "dd-MM-yyyy")
	@Schema(description = "Дата выхода фильма в формате dd-MM-yyyy")
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
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
