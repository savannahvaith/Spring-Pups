package com.qa.springpups.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.springpups.domain.Puppy;
import com.qa.springpups.dto.PuppyDTO;
import com.qa.springpups.repo.PuppyRepo;

@Service
public class PuppyService{
	
	private PuppyRepo repo; 
	private ModelMapper mapper; 
	
	public PuppyService(PuppyRepo repo, ModelMapper mapper) {
		this.repo = repo; 
		this.mapper = mapper;
	}
	
	public PuppyDTO mapToDTO(Puppy pup) {
		return this.mapper.map(pup, PuppyDTO.class);
	}
	
	
	public PuppyDTO create(Puppy p) {
		return this.mapToDTO(this.repo.save(p));
	}

	public List<PuppyDTO> read() {
		List<PuppyDTO> dtos = new ArrayList<>(); 
		for(Puppy pup : this.repo.findAll()) {
			dtos.add(this.mapToDTO(pup));
		}
		return dtos; 
//		return  this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public PuppyDTO getById(Long id) {
		Optional<Puppy> optPup = this.repo.findById(id);
		return this.mapToDTO(optPup.orElse(null));
	}

	public PuppyDTO update(Long id, PuppyDTO newPuppy) {
		Puppy existing = this.repo.findById(id).orElseThrow();
		
		existing.setAge(newPuppy.getAge());
		existing.setBreed(newPuppy.getBreed());
		existing.setName(newPuppy.getName());
		existing.setOwner(newPuppy.getOwner());
		
		return this.mapToDTO(this.repo.save(existing));
	}

	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}