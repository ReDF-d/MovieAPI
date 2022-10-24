package com.movieapi.repo;

import com.movieapi.model.Movie;
import com.movieapi.model.enums.MovieType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	Page<Movie> getAllByName(@Param("name") String name, Pageable pageable);
	
	Page<Movie> getAllByMovieType(MovieType movieType, Pageable pageable);
	
	Page<Movie> getAllByReleaseDate(@Param("date") Date date, Pageable pageable);
	
	void deleteById(@Param("id") Long id);
}
