package com.qa.springpups.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.springpups.domain.Puppy;
import com.qa.springpups.dto.PuppyDTO;
import com.qa.springpups.repo.PuppyRepo;

@Service
public class PuppyServiceDB implements PuppyService {
	
	private PuppyRepo repo; 
	private ModelMapper mapper; 
	
	public PuppyServiceDB(PuppyRepo repo, ModelMapper mapper) {
		this.repo = repo; 
		this.mapper = mapper;
	}
	
	@Override
	public PuppyDTO mapToDTO(Puppy pup) {
		return this.mapper.map(pup, PuppyDTO.class);
	}
	
	
	@Override
	public PuppyDTO createPuppy(Puppy p) {
		return this.mapToDTO(this.repo.save(p));
	}

	@Override
	public List<PuppyDTO> readPuppies() {
		return  this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public PuppyDTO getPuppyById(Long id) {
		Optional<Puppy> optPup = this.repo.findById(id);
		return this.mapToDTO(optPup.orElse(null));
	}

	@Override
	public PuppyDTO updatePuppy(Long id, Puppy newPuppy) {
		Puppy existing = this.repo.findById(id).orElseThrow();
		
		existing.setAge(newPuppy.getAge());
		existing.setBreed(newPuppy.getBreed());
		existing.setName(newPuppy.getName());
		existing.setOwner(newPuppy.getOwner());
		
		return this.mapToDTO(this.repo.save(existing));
	}

	@Override
	public boolean deletePuppy(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	

}
