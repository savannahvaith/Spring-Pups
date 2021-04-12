package com.qa.springpups.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qa.springpups.domain.Puppy;
import com.qa.springpups.repo.PuppyRepo;

@Service
public class PuppyServiceDB implements PuppyService {
	
	private PuppyRepo repo; 
	
	public PuppyServiceDB(PuppyRepo repo) {
		this.repo = repo; 
	}
	
	@Override
	public Puppy createPuppy(Puppy p) {
		return this.repo.save(p);
	}

	@Override
	public List<Puppy> readPuppies() {
		return this.repo.findAll();
	}

	@Override
	public Puppy getPuppyById(Long id) {
		Optional<Puppy> optPup = this.repo.findById(id);
		return optPup.orElse(null);
	}

	@Override
	public Puppy updatePuppy(Long id, Puppy newPuppy) {
		Puppy existing = this.getPuppyById(id);
		
		existing.setAge(newPuppy.getAge());
		existing.setBreed(newPuppy.getBreed());
		existing.setName(newPuppy.getName());
		existing.setOwner(newPuppy.getOwner());
		
		return this.repo.save(existing);
	}

	@Override
	public boolean deletePuppy(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
