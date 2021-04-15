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
import com.qa.springpups.domain.Owner;
import com.qa.springpups.dto.OwnerDTO;
import com.qa.springpups.repo.OwnerRepo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:pup-schema.sql", "classpath:pup-data.sql"},executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
public class OwnerControllerIntegrationTest {

	@Autowired
	private MockMvc mock; 
	
	@Autowired
	private OwnerRepo repo; 
	
	@Autowired
	private ObjectMapper mapper; 
	
	@Autowired
	private ModelMapper modelMapper; 
	
	private OwnerDTO mapToDTO(Owner owner) {
		return this.modelMapper.map(owner,OwnerDTO.class);
	}
	
	private final String URI = "/owner";
	private final Owner OWNER_FROM_DB = new Owner(1L,"Savannah", 22);
	private final Long id = OWNER_FROM_DB.getId();
	
//	@BeforeEach
//	void init() {
//		this.repo.deleteAll(); 
//		this.testOwner = new Owner("Savannah", 22);
//		this.testOwnerWithId = this.repo.saveAndFlush(testOwner);
//		this.id = testOwnerWithId.getId();
//		this.ownerDTO = this.mapToDTO(testOwnerWithId);
//	}
	
	@Test
	void createTest() throws JsonProcessingException, Exception {
		final Owner NEW_OWNER = new Owner("Bark Whalberg", 34);
		final Owner SAVED_OWNER = new Owner(2L, "Bark Whalberg", 34);
		this.mock.perform(post(URI +"/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.mapper.writeValueAsString(NEW_OWNER)))
			.andExpect(status().isCreated())
			.andExpect(content().json(this.mapper.writeValueAsString(this.mapToDTO(SAVED_OWNER))));
	}
	
	@Test
	void readAllTest() throws JsonProcessingException, Exception {
		final List<OwnerDTO> OWNERS = new ArrayList<>();
		OWNERS.add(this.mapToDTO(OWNER_FROM_DB));
		
		this.mock.perform(get(URI+"/readAll")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	void readOneTest() throws JsonProcessingException, Exception {
		this.mock.perform(get(URI+"/readOne/"+ this.id)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(this.mapper.writeValueAsString(this.mapToDTO(OWNER_FROM_DB))));
	}
	
	@Test
	void updateTest() throws JsonProcessingException, Exception {
		final Owner UPDATED = new Owner(this.id,"Savannah", 23);
		this.mock.perform(put(URI+"/update/"+ this.id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.mapper.writeValueAsString(UPDATED)))
				.andExpect(status().isAccepted())
				.andExpect(content().json(this.mapper.writeValueAsString(this.mapToDTO(UPDATED))));
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mock.perform(delete(URI+"/delete/"+this.id)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
}
