package com.qa.springpups.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springpups.domain.Puppy;
import com.qa.springpups.dto.PuppyDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PuppyControllerIntegrationTest {

	@Autowired
	private MockMvc movkMvc;
	
	@Autowired
	private ObjectMapper mapper; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final String URI = "/puppy";
	
	private PuppyDTO mapToDTO(Puppy p ) {
		return this.modelMapper.map(p,PuppyDTO.class);
	}
	
	private final static Puppy puppy = new Puppy("Milo", 2, "Lahsa Apso", false);
	private final static Puppy savedPuppy = new Puppy(1L,puppy.getName(), puppy.getAge(), puppy.getBreed(), puppy.isSkill());
	
	@Test
	void createTest() throws Exception {
		
		// create puppy 
		// convert from object to string 
		String puppyAsString = this.mapper.writeValueAsString(puppy);
		
		// make the request
		RequestBuilder mockRequest = post(URI + "/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(puppyAsString);

		// Response stuff 
		// need to convert to DTO for relationship tings
		String savedPuppyAsString = this.mapper.writeValueAsString(this.mapToDTO(savedPuppy));
		
		ResultMatcher resultStatus = status().isCreated(); 
		ResultMatcher resultBody = content().json(savedPuppyAsString);
	
		this.movkMvc.perform(mockRequest).andExpect(resultStatus).andExpect(resultBody);
	
	}
	
}
