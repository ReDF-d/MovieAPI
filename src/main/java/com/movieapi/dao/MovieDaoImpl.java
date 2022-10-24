package com.movieapi.dao;

import com.movieapi.exception.NotFoundException;
import com.movieapi.model.Movie;
import com.movieapi.model.enums.MovieType;
import com.movieapi.repo.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class MovieDaoImpl implements MovieDao {
	
	final MovieRepository movieRepository;
	
	public MovieDaoImpl(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	public List<Movie> getAllMovies(Pageable page) {
		return movieRepository.findAll(page).getContent();
	}
	
	public Movie findMovieById(Long id) throws NotFoundException {
		Optional<Movie> optionalMovie = movieRepository.findById(id);
		if (optionalMovie.isEmpty())
			throw new NotFoundException("Фильм не найден");
		return optionalMovie.get();
	}
	
	public void save(Movie movie) {
		movieRepository.save(movie);
	}
	
	public List<Movie> getMoviesByName(String name, Pageable page) {
		return movieRepository.getAllByName(name, page).getContent();
	}
	
	public List<Movie> getMoviesByMovieType(MovieType type, Pageable page) {
		return movieRepository.getAllByMovieType(type, page).getContent();
	}
	
	public List<Movie> getMoviesByReleaseDate(Date releaseDate, Pageable page) {
		return movieRepository.getAllByReleaseDate(releaseDate, page).getContent();
	}
	
	public void delete(Long id) throws NotFoundException {
		if (movieRepository.findById(id).isPresent())
			movieRepository.deleteById(id);
		else
			throw new NotFoundException("Фильм не найден");
	}
}
