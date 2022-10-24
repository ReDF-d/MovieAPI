package com.movieapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.movieapi.model.Movie;
import com.movieapi.model.enums.MovieType;
import com.movieapi.payload.*;
import com.movieapi.repo.MovieRepository;
import com.movieapi.validation.ValidList;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MovieControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MovieRepository movieRepository;
	
	static String asJsonString(Object obj) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	@BeforeEach
	void setupTests() throws ParseException {
		Movie testMovie = new Movie();
		testMovie.setName("testName");
		testMovie.setDescription("testDescription");
		testMovie.setMovieType(MovieType.FULL);
		testMovie.setGenre("testGenre");
		testMovie.setReleaseDate(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"));
		movieRepository.save(testMovie);
	}
	
	@Test
	void testAllEndpoint_andExpectOk() throws Exception {
		MvcResult result = mockMvc.perform(get("/all")).andExpect(status().isOk())
				.andReturn();
		String name = JsonPath.read(result.getResponse().getContentAsString(), "$.response[0].name");
		Assertions.assertEquals("testName", name);
	}
	
	@Test
	void testAddEndpoint_andExpectCreated() throws Exception {
		MoviePayload anotherMovie = new MoviePayload();
		anotherMovie.setName("anotherName");
		anotherMovie.setDescription("anotherDescription");
		anotherMovie.setType("Полнометражный");
		anotherMovie.setGenre("anotherGenre");
		anotherMovie.setReleaseDate("01-01-2000");
		
		MvcResult result = mockMvc.perform(post("/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(anotherMovie))).andExpect(status().isCreated())
				.andReturn();
		String response = JsonPath.read(result.getResponse().getContentAsString(), "$.response");
		Assertions.assertEquals("Фильм сохранён", response);
	}
	
	@Test
	void testAddEndpoint_andExpectError() throws Exception {
		MoviePayload invalidMovie = new MoviePayload();
		invalidMovie.setName("");
		invalidMovie.setDescription("");
		invalidMovie.setType("Invalid");
		invalidMovie.setGenre("anotherGenre123");
		invalidMovie.setReleaseDate("qwerty");
		
		MvcResult result = mockMvc.perform(post("/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(invalidMovie))).andExpect(status().isBadRequest())
				.andReturn();
		JSONArray errors = JsonPath.read(result.getResponse().getContentAsString(), "$.errors");
		Assertions.assertEquals(5, errors.size());
	}
	
	@Test
	void testAddAllEndpoint_andExpectCreated() throws Exception {
		MoviePayload firstMovie = new MoviePayload();
		firstMovie.setName("anotherName");
		firstMovie.setDescription("anotherDescription");
		firstMovie.setType("Полнометражный");
		firstMovie.setGenre("anotherGenre");
		firstMovie.setReleaseDate("01-01-2000");
		
		MoviePayload secondMovie = new MoviePayload();
		secondMovie.setName("anotherName");
		secondMovie.setDescription("anotherDescription");
		secondMovie.setType("Полнометражный");
		secondMovie.setGenre("anotherGenre");
		secondMovie.setReleaseDate("01-01-2000");
		
		ValidList validList = new ValidList();
		List<MoviePayload> moviePayloads = new ArrayList<>();
		
		moviePayloads.add(firstMovie);
		moviePayloads.add(secondMovie);
		
		validList.setMovies(moviePayloads);
		
		MvcResult result = mockMvc.perform(post("/addAll")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(validList))).andExpect(status().isCreated())
				.andReturn();
		String response = JsonPath.read(result.getResponse().getContentAsString(), "$.response");
		Assertions.assertEquals("Фильмы сохранены", response);
	}
	
	@Test
	void testAddAllEndpoint_andExpectError() throws Exception {
		MoviePayload firstMovie = new MoviePayload();
		firstMovie.setName("anotherName");
		firstMovie.setDescription("anotherDescription");
		firstMovie.setType("Invalid");
		firstMovie.setGenre("anotherGenre");
		firstMovie.setReleaseDate("01-01-2000");
		
		MoviePayload secondMovie = new MoviePayload();
		secondMovie.setName("anotherName");
		secondMovie.setDescription("anotherDescription");
		secondMovie.setType("Полнометражный");
		secondMovie.setGenre("anotherGenre");
		secondMovie.setReleaseDate("01-01-2000");
		
		ValidList validList = new ValidList();
		List<MoviePayload> moviePayloads = new ArrayList<>();
		
		moviePayloads.add(firstMovie);
		moviePayloads.add(secondMovie);
		
		validList.setMovies(moviePayloads);
		
		MvcResult result = mockMvc.perform(post("/addAll")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(validList))).andExpect(status().isBadRequest())
				.andReturn();
		JSONArray errors = JsonPath.read(result.getResponse().getContentAsString(), "$.errors");
		Assertions.assertEquals(1, errors.size());
	}
	
	@Test
	void testGetMovieByIdEndpoint_andExpectOk() throws Exception {
		Long id = movieRepository.findAll().get(0).getId();
		MvcResult result = mockMvc.perform(get("/movie/" + id)).andExpect(status().isOk())
				.andReturn();
		Long response = Long.parseLong(JsonPath.read(result.getResponse().getContentAsString(), "$.response.id").toString());
		Assertions.assertEquals(id, response);
	}
	
	@Test
	void testGetMovieByIdEndpoint_andExpectError() throws Exception {
		MvcResult result = mockMvc.perform(get("/movie/qwe")).andExpect(status().isBadRequest())
				.andReturn();
		JSONArray id = JsonPath.read(result.getResponse().getContentAsString(), "$.errors");
		Assertions.assertEquals(1, id.size());
	}
	
	@Test
	void testGetMovieByIdEndpoint_andExpectNotFound() throws Exception {
		mockMvc.perform(get("/movie/123")).andExpect(status().isNotFound());
	}
	
	@Test
	void testFindByNameEndpoint_andExpectOk() throws Exception {
		MovieNamePayload movieNamePayload = new MovieNamePayload();
		movieNamePayload.setName("testName");
		
		MvcResult result = mockMvc.perform(post("/findByName")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieNamePayload))).andExpect(status().isOk())
				.andReturn();
		String response = JsonPath.read(result.getResponse().getContentAsString(), "$.response[0].name");
		Assertions.assertEquals("testName", response);
	}
	
	@Test
	void testFindByNameEndpoint_andExpectEmptyBody() throws Exception {
		MovieNamePayload movieNamePayload = new MovieNamePayload();
		movieNamePayload.setName("notExists");
		
		MvcResult result = mockMvc.perform(post("/findByName")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieNamePayload))).andExpect(status().isOk())
				.andReturn();
		JSONArray response = JsonPath.read(result.getResponse().getContentAsString(), "$.response");
		Assertions.assertEquals("[]", response.toJSONString());
	}
	
	@Test
	void testFindByNameEndpoint_andExpectError() throws Exception {
		MovieNamePayload movieNamePayload = new MovieNamePayload();
		
		MvcResult result = mockMvc.perform(post("/findByName")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieNamePayload))).andExpect(status().isBadRequest())
				.andReturn();
		JSONArray response = JsonPath.read(result.getResponse().getContentAsString(), "$.errors");
		Assertions.assertEquals(1, response.size());
	}
	
	@Test
	void testFindByTypeEndpoint_andExpectOk() throws Exception {
		MovieTypePayload movieTypePayload = new MovieTypePayload();
		movieTypePayload.setType("Полнометражный");
		
		MvcResult result = mockMvc.perform(post("/findByType")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieTypePayload))).andExpect(status().isOk())
				.andReturn();
		String response = JsonPath.read(result.getResponse().getContentAsString(), "$.response[0].name");
		Assertions.assertEquals("testName", response);
	}
	
	@Test
	void testFindByTypeEndpoint_andExpectEmptyBody() throws Exception {
		MovieTypePayload movieNamePayload = new MovieTypePayload();
		movieNamePayload.setType("Сериал");
		
		MvcResult result = mockMvc.perform(post("/findByType")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieNamePayload))).andExpect(status().isOk())
				.andReturn();
		JSONArray response = JsonPath.read(result.getResponse().getContentAsString(), "$.response");
		Assertions.assertEquals("[]", response.toJSONString());
	}
	
	@Test
	void testFindByTypeEndpoint_andExpectError() throws Exception {
		MovieTypePayload movieNamePayload = new MovieTypePayload();
		
		MvcResult result = mockMvc.perform(post("/findByType")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieNamePayload))).andExpect(status().isBadRequest())
				.andReturn();
		JSONArray response = JsonPath.read(result.getResponse().getContentAsString(), "$.errors");
		Assertions.assertEquals(1, response.size());
	}
	
	@Test
	void testFindByDateEndpoint_andExpectOk() throws Exception {
		MovieReleaseDatePayload movieReleaseDatePayload = new MovieReleaseDatePayload();
		movieReleaseDatePayload.setReleaseDate("01-01-2000");
		
		MvcResult result = mockMvc.perform(post("/findByDate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieReleaseDatePayload))).andExpect(status().isOk())
				.andReturn();
		String response = JsonPath.read(result.getResponse().getContentAsString(), "$.response[0].name");
		Assertions.assertEquals("testName", response);
	}
	
	@Test
	void testFindByDateEndpoint_andExpectEmptyBody() throws Exception {
		MovieReleaseDatePayload movieReleaseDatePayload = new MovieReleaseDatePayload();
		movieReleaseDatePayload.setReleaseDate("02-02-2002");
		
		MvcResult result = mockMvc.perform(post("/findByDate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieReleaseDatePayload))).andExpect(status().isOk())
				.andReturn();
		JSONArray response = JsonPath.read(result.getResponse().getContentAsString(), "$.response");
		Assertions.assertEquals("[]", response.toJSONString());
	}
	
	@Test
	void testFindByDateEndpoint_andExpectError() throws Exception {
		MovieReleaseDatePayload movieReleaseDatePayload = new MovieReleaseDatePayload();
		movieReleaseDatePayload.setReleaseDate("qwe");
		
		MvcResult result = mockMvc.perform(post("/findByDate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(movieReleaseDatePayload))).andExpect(status().isBadRequest())
				.andReturn();
		JSONArray response = JsonPath.read(result.getResponse().getContentAsString(), "$.errors");
		Assertions.assertEquals(1, response.size());
	}
	
	@Test
	void testDeleteEndpoint_andExpectOk() throws Exception {
		Long id = movieRepository.findAll().get(0).getId();
		mockMvc.perform(delete("/movie/" + id)).andExpect(status().isOk());
	}
	
	@Test
	void testDeleteEndpoint_andExpectNotFound() throws Exception {
		mockMvc.perform(delete("/movie/2")).andExpect(status().isNotFound());
	}
	
	@Test
	void testDeleteEndpoint_andExpectError() throws Exception {
		mockMvc.perform(delete("/movie/qwe")).andExpect(status().isBadRequest());
	}
	
	@Test
	void testUpdateEndpoint_andExpectOk() throws Exception {
		Long id = movieRepository.findAll().get(0).getId();
		MovieUpdatePayload updatePayload = new MovieUpdatePayload();
		updatePayload.setId(id);
		updatePayload.setName("updatedName");
		
		MvcResult result = mockMvc.perform(patch("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(updatePayload))).andExpect(status().isOk())
				.andReturn();
		String response = JsonPath.read(result.getResponse().getContentAsString(), "$.response");
		Assertions.assertEquals("Данные фильма обновлены", response);
	}
	
	@Test
	void testUpdateEndpoint_andExpectError() throws Exception {
		MovieUpdatePayload updatePayload = new MovieUpdatePayload();
		updatePayload.setType("invalidType");
		MvcResult result = mockMvc.perform(patch("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(updatePayload))).andExpect(status().isBadRequest())
				.andReturn();
		JSONArray response = JsonPath.read(result.getResponse().getContentAsString(), "$.errors");
		Assertions.assertEquals(2, response.size());
	}
}
