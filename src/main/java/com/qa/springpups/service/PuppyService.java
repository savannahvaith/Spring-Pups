package com.qa.springpups.service;

import java.util.List;

import com.qa.springpups.domain.Puppy;

public interface PuppyService {
	
	Puppy createPuppy(Puppy p);
	
	List<Puppy> readPuppies();
	
	Puppy getPuppyById(Long id);
	
	Puppy updatePuppy(Long id, Puppy newPuppy);
	
	boolean deletePuppy(Long id);
}
