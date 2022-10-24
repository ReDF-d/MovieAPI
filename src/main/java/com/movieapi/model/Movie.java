package com.movieapi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.movieapi.model.enums.MovieType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movie")
@Schema(description = "Фильм")
public class Movie {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Идентификатор")
	private Long id;
	@Column(name = "name")
	@Schema(description = "Название фильма")
	private String name;
	@Column(name = "description")
	@Schema(description = "Описание фильма")
	private String description;
	@Column(name = "movie_type")
	@Schema(description = "Тип фильма - допустимые значения: \"Полнометражный\", \"Короткометражный\",\"Сериал\"")
	private MovieType movieType;
	@Column(name = "genre")
	@Schema(description = "Жанр фильма")
	private String genre;
	@Column(name = "release_date")
	@Schema(description = "Дата выхода фильма в формате dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date releaseDate;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public String getMovieType() {
		return movieType.getType();
	}
	
	public void setMovieType(MovieType movieType) {
		this.movieType = movieType;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
