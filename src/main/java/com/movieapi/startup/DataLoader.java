package com.movieapi.startup;

import com.movieapi.model.Movie;
import com.movieapi.model.enums.MovieType;
import com.movieapi.repo.MovieRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
@Profile("!test")
public class DataLoader implements ApplicationRunner {
	
	private final MovieRepository movieRepository;
	
	public DataLoader(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	public void run(ApplicationArguments args) throws ParseException {
		String pattern = "dd-MM-yyyy";
		
		Movie movie1 = new Movie();
		movie1.setName("Зелёная миля");
		movie1.setDescription("Зеленая миля - описание");
		movie1.setMovieType(MovieType.FULL);
		movie1.setGenre("Драма, криминал");
		movie1.setReleaseDate(new SimpleDateFormat(pattern).parse("06-12-1999"));
		
		Movie movie2 = new Movie();
		movie2.setName("Список Шиндлера");
		movie2.setDescription("Список Шиндлера - описание");
		movie2.setMovieType(MovieType.FULL);
		movie2.setGenre("Драма");
		movie2.setReleaseDate(new SimpleDateFormat(pattern).parse("30-11-1993"));
		
		Movie movie3 = new Movie();
		movie3.setName("Форрест Гамп");
		movie3.setDescription("Форрест Гамп - описание");
		movie3.setMovieType(MovieType.FULL);
		movie3.setGenre("Мелодрама");
		movie3.setReleaseDate(new SimpleDateFormat(pattern).parse("23-06-1994"));
		
		Movie movie4 = new Movie();
		movie4.setName("Во все тяжкие");
		movie4.setDescription("Во все тяжкие - описание");
		movie4.setMovieType(MovieType.SERIES);
		movie4.setGenre("Криминал");
		movie4.setReleaseDate(new SimpleDateFormat(pattern).parse("20-12-2008"));
		
		Movie movie5 = new Movie();
		movie5.setName("Наваждение");
		movie5.setDescription("Наваждение - описание");
		movie5.setMovieType(MovieType.SHORT);
		movie5.setGenre("Комедия");
		movie5.setReleaseDate(new SimpleDateFormat(pattern).parse("23-07-1965"));
		
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		movieRepository.save(movie4);
		movieRepository.save(movie5);
	}
}
