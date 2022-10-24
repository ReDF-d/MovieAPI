package com.movieapi.controller;

import com.movieapi.exception.NotFoundException;
import com.movieapi.model.Movie;
import com.movieapi.payload.*;
import com.movieapi.service.MovieService;
import com.movieapi.validation.ValidList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(
		name = "Фильмы",
		description = "Эндпоинты для работы с фильмами"
)
public class MovieController {
	
	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@GetMapping(path = "/all", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Получить список всех фильмов", responses = {@ApiResponse(responseCode = "200")})
	public ResponsePayload<List<Movie>> getAllMovies(@RequestParam(value = "pageNo", defaultValue = "0", required = false) @Parameter(description = "Номер страницы") Integer pageNo,
	                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Parameter(description = "Размер страницы") Integer pageSize) {
		List<Movie> movieList = movieService.getAllMovies(pageNo, pageSize);
		
		ResponsePayload<List<Movie>> response = new ResponsePayload<>();
		response.setResponse(movieList);
		response.setPageNo(pageNo);
		response.setPageSize(pageSize);
		return response;
	}
	
	@GetMapping(path = "/movie/{id}", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Получить информацию о фильме по его id", responses = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400"), @ApiResponse(responseCode = "404")})
	public ResponsePayload<Movie> getMovieById(@PathVariable @Parameter(description = "id фильма")
			                                           Long id) throws NotFoundException {
		Movie movie = movieService.getMovieById(id);
		
		ResponsePayload<Movie> response = new ResponsePayload<>();
		response.setResponse(movie);
		return response;
	}
	
	@DeleteMapping(path = "/movie/{id}", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Удалить фильм по его id", responses = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400"), @ApiResponse(responseCode = "404")})
	public ResponsePayload<String> deleteMovie(@PathVariable @Parameter(description = "id фильма")
			                                           Long id) throws NotFoundException {
		movieService.delete(id);
		
		ResponsePayload<String> response = new ResponsePayload<>();
		response.setResponse("Фильм с id " + id + " удалён");
		return response;
	}
	
	@PostMapping(path = "/findByName", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Получить список фильмов по названию", responses = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400")})
	public ResponsePayload<List<Movie>> getMoviesByName(@RequestBody @Valid @Parameter(description = "JSON в формате \"name\" : название фильма") MovieNamePayload payload,
	                                                    @RequestParam(value = "pageNo", defaultValue = "0", required = false) @Parameter(description = "Номер страницы") Integer pageNo,
	                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Parameter(description = "Размер страницы") Integer pageSize) {
		List<Movie> movieList = movieService.getMoviesByName(payload.getName(), pageNo, pageSize);
		
		ResponsePayload<List<Movie>> response = new ResponsePayload<>();
		response.setResponse(movieList);
		response.setPageNo(pageNo);
		response.setPageSize(pageSize);
		return response;
	}
	
	@PostMapping(path = "/findByType", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Получить список фильмов по типу фильма", responses = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400")})
	public ResponsePayload<List<Movie>> getMoviesByMovieType(@RequestBody @Valid @Parameter(description = "JSON в формате \"type\" : тип фильма") MovieTypePayload payload,
	                                                         @RequestParam(value = "pageNo", defaultValue = "0", required = false) @Parameter(description = "Номер страницы") Integer pageNo,
	                                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Parameter(description = "Размер страницы") Integer pageSize
	) {
		List<Movie> movieList = movieService.getMoviesByMovieType(payload.getType(), pageNo, pageSize);
		
		ResponsePayload<List<Movie>> response = new ResponsePayload<>();
		response.setResponse(movieList);
		response.setPageNo(pageNo);
		response.setPageSize(pageSize);
		return response;
	}
	
	@PostMapping(path = "/findByDate", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Получить список фильмов по дате выхода", responses = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400")})
	public ResponsePayload<List<Movie>> getMoviesByReleaseDate(@RequestBody @Valid @Parameter(description = "JSON в формате \"releaseDate\" : дата выхода фильма(dd-MM-yyyy)") MovieReleaseDatePayload payload,
	                                                           @RequestParam(value = "pageNo", defaultValue = "0", required = false) @Parameter(description = "Номер страницы") Integer pageNo,
	                                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Parameter(description = "Размер страницы") Integer pageSize) {
		List<Movie> movieList = movieService.getMoviesByReleaseDate(payload.getReleaseDate(), pageNo, pageSize);
		
		ResponsePayload<List<Movie>> response = new ResponsePayload<>();
		response.setResponse(movieList);
		response.setPageNo(pageNo);
		response.setPageSize(pageSize);
		return response;
	}
	
	@PostMapping(path = "/add", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Добавить фильм", responses = {@ApiResponse(responseCode = "201"), @ApiResponse(responseCode = "400")})
	public ResponseEntity<ResponsePayload<String>> addMovie(@RequestBody @Valid @Parameter(description = "JSON с данными фильма")
			                                                        MoviePayload moviePayload) {
		movieService.addMovie(moviePayload);
		
		ResponsePayload<String> response = new ResponsePayload<>();
		response.setResponse("Фильм сохранён");
		response.setStatusCode(HttpStatus.CREATED.getReasonPhrase());
		response.setStatusCodeValue(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping(path = "/addAll", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Добавить несколько фильмов", responses = {@ApiResponse(responseCode = "201"), @ApiResponse(responseCode = "400")})
	public ResponseEntity<ResponsePayload<String>> addMovies(@RequestBody @Valid @Parameter(description = "JSON с массивом данных фильмов")
			                                                         ValidList moviePayloadList) {
		moviePayloadList.getMovies().forEach(movieService::addMovie);
		
		ResponsePayload<String> response = new ResponsePayload<>();
		response.setResponse("Фильмы сохранены");
		response.setStatusCode(HttpStatus.CREATED.getReasonPhrase());
		response.setStatusCodeValue(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PatchMapping(path = "/update", produces = {"application/json; charset=UTF-8"})
	@Operation(summary = "Обновить данные фильма - все поля, кроме поля \"id\", необязательны, и не будут обновлены, если отсутствуют", responses = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400"), @ApiResponse(responseCode = "404")})
	public ResponsePayload<String> updateMovie(@RequestBody @Valid @Parameter(description = "JSON с обновленными данными фильма") MovieUpdatePayload moviePayload) throws NotFoundException {
		movieService.update(moviePayload);
		
		ResponsePayload<String> response = new ResponsePayload<>();
		response.setResponse("Данные фильма обновлены");
		return response;
	}
}
