package com.qa.springpups.controller;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springpups.domain.Puppy;
import com.qa.springpups.dto.PuppyDTO;
import com.qa.springpups.repo.PuppyRepo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PuppyControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PuppyRepo repo; 
	
	private final String URI = "/puppy";
	
	private PuppyDTO mapToDTO(Puppy p ) {
		return this.modelMapper.map(p,PuppyDTO.class);
	}
	
	private Long id; 
	private Puppy testPuppy; 
	private Puppy testPuppyWithId; 
	private PuppyDTO puppyDTO; 
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testPuppy = new Puppy("Milo", 2, "Lahsa Apso", false);
		this.testPuppyWithId = this.repo.saveAndFlush(this.testPuppy);
		this.id = this.testPuppyWithId.getId();
		this.puppyDTO = mapToDTO(testPuppyWithId);
	}
	
	@Test
	void createTest() throws Exception {
		

//		// convert from object to string 
//		String puppyAsString = this.mapper.writeValueAsString(this.testPuppy);
//		
//		// make the request
//		RequestBuilder mockRequest = post(URI + "/create")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(puppyAsString);
//
//		// Response stuff 
//		// need to convert to DTO for relationship tings
//		String savedPuppyAsString = this.mapper.writeValueAsString(this.puppyDTO);
//		
//		ResultMatcher resultStatus = status().isCreated(); 
//		ResultMatcher resultBody = content().json(savedPuppyAsString);
//	
//		this.mockMvc.perform(mockRequest).andExpect(resultStatus).andExpect(resultBody);
		
		this.mockMvc.perform(post(URI+"/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(this.testPuppy)))
					.andExpect(status().isCreated())
					.andExpect(content().json(this.mapper.writeValueAsString(this.puppyDTO)));
	}
	
	@Test
	void readOneTest() throws JsonProcessingException, Exception {
		this.mockMvc.perform(get(URI+"/getOne/"+this.id)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json(this.mapper.writeValueAsString(this.puppyDTO)));
	}
	
	@Test
	void readAllTest() throws JsonProcessingException, Exception {
		final List<PuppyDTO> PUPPIES = new ArrayList<>();
		PUPPIES.add(this.puppyDTO);
		
		this.mockMvc.perform(get(URI+"/getAll")
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json(this.mapper.writeValueAsString(PUPPIES)));
	}
	
	@Test
	void updateTest() throws JsonProcessingException, Exception {
		final Puppy NEW_PUPPY = new Puppy(this.id, "Tyson", 2, "German Shepard", false);
		this.puppyDTO = mapToDTO(NEW_PUPPY); 
		this.mockMvc.perform(put(URI + "/update/" + this.id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(puppyDTO)))
				.andExpect(status().isAccepted())
				.andExpect(content().json(this.mapper.writeValueAsString(puppyDTO)));
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mockMvc.perform(delete(URI + "/delete/" + this.id))
				.andExpect(status().isNoContent());
	}
	
}
