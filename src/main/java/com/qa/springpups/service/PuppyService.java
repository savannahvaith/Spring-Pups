package com.qa.springpups.service;

import java.util.List;

import com.qa.springpups.domain.Puppy;
import com.qa.springpups.dto.PuppyDTO;

public interface PuppyService {
	
	PuppyDTO createPuppy(Puppy p);
	
	List<PuppyDTO> readPuppies();
	
	PuppyDTO getPuppyById(Long id);
	
	PuppyDTO updatePuppy(Long id, PuppyDTO newPuppy);
	
	boolean deletePuppy(Long id);
	
	PuppyDTO mapToDTO(Puppy pup);
}
