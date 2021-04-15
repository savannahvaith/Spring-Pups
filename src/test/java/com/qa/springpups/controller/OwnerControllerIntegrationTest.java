package com.qa.springpups.controller;

import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springpups.domain.Owner;
import com.qa.springpups.dto.OwnerDTO;
import com.qa.springpups.repo.OwnerRepo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OwnerControllerIntegrationTest {

	@Autowired
	private MockMvc mock; 
	
	@Autowired
	private OwnerRepo repo; 
	
	@Autowired
	private ObjectMapper mapper; 
	
	@Autowired
	private ModelMapper modelMapper; 
	
	private Long id; 
	private Owner testOwner; 
	private Owner testOwnerWithId; 
	private OwnerDTO ownerDTO;
	
	private OwnerDTO mapToDTO(Owner owner) {
		return this.modelMapper.map(owner,OwnerDTO.class);
	}
	
	private final String URI = "/owner";
	
	@BeforeEach
	void init() {
		this.repo.deleteAll(); 
		this.testOwner = new Owner("Savannah", 22);
		this.testOwnerWithId = this.repo.saveAndFlush(testOwner);
		this.id = testOwnerWithId.getId();
		this.ownerDTO = this.mapToDTO(testOwnerWithId);
	}
	
	@Test
	void createTest() throws JsonProcessingException, Exception {
		this.mock.perform(post(URI +"/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.mapper.writeValueAsString(this.testOwner)))
			.andExpect(status().isCreated())
			.andExpect(content().json(this.mapper.writeValueAsString(this.ownerDTO)));
	}
	
	@Test
	void readAllTest() throws JsonProcessingException, Exception {
		final List<Owner> OWNERS = new ArrayList<>();
		OWNERS.add(this.testOwnerWithId);
		
		this.mock.perform(get(URI+"/readAll")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(this.mapper.writeValueAsString(OWNERS)));
	}
	
	@Test
	void readOneTest() throws JsonProcessingException, Exception {
		this.mock.perform(get(URI + "/readOne/" + this.id)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(this.mapper.writeValueAsString(this.testOwnerWithId)));
	}
	
	@Test
	void updateTest() throws JsonProcessingException, Exception {
		final Owner UPDATED = new Owner(this.id,"Savannah", 23);
		this.ownerDTO = this.mapToDTO(UPDATED); 
		this.mock.perform(put(URI+"/update/" + this.id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.mapper.writeValueAsString(this.ownerDTO)))
				.andExpect(status().isAccepted())
				.andExpect(content().json(this.mapper.writeValueAsString(this.ownerDTO)));
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mock.perform(delete(URI+"/delete/"+this.id)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
}
