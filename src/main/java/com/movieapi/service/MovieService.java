package com.movieapi.service;

import com.movieapi.exception.NotFoundException;
import com.movieapi.model.Movie;
import com.movieapi.payload.MoviePayload;
import com.movieapi.payload.MovieUpdatePayload;

import java.util.Date;
import java.util.List;

public interface MovieService {
	
	List<Movie> getAllMovies(int pageNo, int pageSize);
	
	Movie getMovieById(Long id) throws NotFoundException;
	
	void addMovie(MoviePayload moviePayload);
	
	List<Movie> getMoviesByName(String name, int pageNo, int pageSize);
	
	List<Movie> getMoviesByMovieType(String type, int pageNo, int pageSize);
	
	List<Movie> getMoviesByReleaseDate(String releaseDate, int pageNo, int pageSize);
	
	void delete(Long id) throws NotFoundException;
	
	void update(MovieUpdatePayload moviePayload) throws NotFoundException;
	
	Movie convert(MoviePayload moviePayload);
	
	Movie convertUpdateDto(MovieUpdatePayload moviePayload, Movie movie);
	
	Date convertDate(String releaseDate);
}
