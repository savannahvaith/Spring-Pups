package com.qa.springpups.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springpups.domain.Puppy;
import com.qa.springpups.dto.PuppyDTO;
import com.qa.springpups.repo.PuppyRepo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:pup-schema.sql",
		"classpath:pup-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class PuppyControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ModelMapper modelMapper;

	private PuppyDTO mapToDTO(Puppy puppy) {
		return this.modelMapper.map(puppy, PuppyDTO.class);
	}

	private final String URI = "/puppy";

	final Puppy PUP_FROM_DB = new Puppy(1L, "Milo", 2, "Lahsa Apso", false);
	final Long id = PUP_FROM_DB.getId();

//	@BeforeEach
//	void init() {
//		this.repo.deleteAll();
//		this.testPuppy = new Puppy("Milo", 2, "Lahsa Apso", false);
//		this.testPuppyWithId = this.repo.saveAndFlush(this.testPuppy);
//		this.id = this.testPuppyWithId.getId();
//		this.puppyDTO = mapToDTO(testPuppyWithId);
//	}

	@Test
	void createTest() throws Exception {
		final Puppy NEW_PUPPY = new Puppy("Tyson", 3, "German Shepard", false);
		final Puppy SAVED_PUPPY = new Puppy(2L, "Tyson", 3, "German Shepard", false);

		this.mockMvc
				.perform(post(URI + "/create").contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(NEW_PUPPY)))
				.andExpect(status().isCreated())
				.andExpect(content().json(this.mapper.writeValueAsString(this.mapToDTO(SAVED_PUPPY))));
	}

	@Test
	void readOneTest() throws JsonProcessingException, Exception {
		this.mockMvc.perform(get(URI + "/getOne/" + this.id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(this.mapper.writeValueAsString(this.mapToDTO(PUP_FROM_DB))));
	}

	@Test
	void readAllTest() throws JsonProcessingException, Exception {
		final List<PuppyDTO> PUPPIES = new ArrayList<>();
		PUPPIES.add(this.mapToDTO(PUP_FROM_DB));

		this.mockMvc.perform(get(URI + "/getAll").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(this.mapper.writeValueAsString(PUPPIES)));
	}

	@Test
	void updateTest() throws JsonProcessingException, Exception {
		final Puppy NEW_PUPPY = new Puppy(this.id, "Tyson", 2, "German Shepard", false);

		this.mockMvc
				.perform(put(URI + "/update/" + this.id).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(this.mapToDTO(NEW_PUPPY))))
				.andExpect(status().isAccepted())
				.andExpect(content().json(this.mapper.writeValueAsString(this.mapToDTO(NEW_PUPPY))));
	}

	@Test
	void deleteTest() throws Exception {
		this.mockMvc.perform(delete(URI + "/delete/" + this.id)).andExpect(status().isNoContent());
	}

}
