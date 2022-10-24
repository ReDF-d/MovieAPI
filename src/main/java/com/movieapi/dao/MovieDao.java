package com.movieapi.dao;

import com.movieapi.exception.NotFoundException;
import com.movieapi.model.Movie;
import com.movieapi.model.enums.MovieType;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface MovieDao {
	
	List<Movie> getAllMovies(Pageable page);
	
	Movie findMovieById(Long id) throws NotFoundException;
	
	void save(Movie movie);
	
	List<Movie> getMoviesByName(String name, Pageable page);
	
	List<Movie> getMoviesByMovieType(MovieType type, Pageable page);
	
	List<Movie> getMoviesByReleaseDate(Date releaseDate, Pageable page);
	
	void delete(Long id) throws NotFoundException;
}
