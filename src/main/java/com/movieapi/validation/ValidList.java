package com.movieapi.validation;

import com.movieapi.payload.MoviePayload;
import io.swagger.v3.oas.annotations.Hidden;

import javax.validation.Valid;
import java.util.List;

@Hidden
public class ValidList {
	
	@Valid
	List<MoviePayload> movies;
	
	public List<MoviePayload> getMovies() {
		return movies;
	}
	
	public void setMovies(List<MoviePayload> movies) {
		this.movies = movies;
	}
}
