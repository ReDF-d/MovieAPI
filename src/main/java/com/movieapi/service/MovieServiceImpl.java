package com.movieapi.service;

import com.movieapi.dao.MovieDao;
import com.movieapi.exception.NotFoundException;
import com.movieapi.model.Movie;
import com.movieapi.model.enums.MovieType;
import com.movieapi.payload.MoviePayload;
import com.movieapi.payload.MovieUpdatePayload;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
	
	final MovieDao movieDao;
	
	public MovieServiceImpl(MovieDao movieDao) {
		this.movieDao = movieDao;
	}
	
	public List<Movie> getAllMovies(int pageNo, int pageSize) {
		return movieDao.getAllMovies(PageRequest.of(pageNo, pageSize));
	}
	
	public Movie getMovieById(Long id) throws NotFoundException {
		return movieDao.findMovieById(id);
	}
	
	public void addMovie(MoviePayload moviePayload) {
		Movie movie = convert(moviePayload);
		movieDao.save(movie);
	}
	
	public List<Movie> getMoviesByName(String name, int pageNo, int pageSize) {
		return movieDao.getMoviesByName(name, PageRequest.of(pageNo, pageSize));
	}
	
	public List<Movie> getMoviesByMovieType(String type, int pageNo, int pageSize) {
		return movieDao.getMoviesByMovieType(MovieType.fromString(type), PageRequest.of(pageNo, pageSize));
	}
	
	public List<Movie> getMoviesByReleaseDate(String releaseDate, int pageNo, int pageSize) {
		return movieDao.getMoviesByReleaseDate(convertDate(releaseDate), PageRequest.of(pageNo, pageSize));
	}
	
	public void delete(Long id) throws NotFoundException {
		movieDao.delete(id);
	}
	
	public void update(MovieUpdatePayload moviePayload) throws NotFoundException {
		Movie existing = movieDao.findMovieById(moviePayload.getId());
		Movie movie = convertUpdateDto(moviePayload, existing);
		movieDao.save(movie);
	}
	
	
	public Movie convert(MoviePayload moviePayload) {
		Movie movie = new Movie();
		MovieType movieType = MovieType.fromString(moviePayload.getType());
		movie.setName(moviePayload.getName());
		movie.setDescription(moviePayload.getDescription());
		movie.setMovieType(movieType);
		movie.setGenre(moviePayload.getGenre());
		movie.setReleaseDate(convertDate(moviePayload.getReleaseDate()));
		
		return movie;
	}
	
	public Movie convertUpdateDto(MovieUpdatePayload moviePayload, Movie movie) {
		if ((moviePayload.getType()) != null && !moviePayload.getType().equals("")) {
			MovieType movieType = MovieType.fromString(moviePayload.getType());
			if (movieType != null)
				movie.setMovieType(movieType);
		}
		if ((moviePayload.getName() != null) && !(moviePayload.getName().equals("")))
			movie.setName(moviePayload.getName());
		if ((moviePayload.getDescription() != null) && !(moviePayload.getDescription().equals("")))
			movie.setDescription(moviePayload.getDescription());
		if ((moviePayload.getGenre() != null) && !(moviePayload.getGenre().equals("")))
			movie.setGenre(moviePayload.getGenre());
		if ((moviePayload.getReleaseDate() != null) && !(moviePayload.getReleaseDate().equals("")))
			movie.setReleaseDate(convertDate(moviePayload.getReleaseDate()));
		
		return movie;
	}
	
	public Date convertDate(String releaseDate) {
		try {
			return new SimpleDateFormat("dd-MM-yyyy").parse(releaseDate);
		} catch (ParseException e) {
			return null;
		}
	}
	
}
