package com.qa.springpups.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.springpups.domain.Owner;
import com.qa.springpups.dto.OwnerDTO;
import com.qa.springpups.repo.OwnerRepo;

@Service
public class OwnerService {

	private OwnerRepo repo; 
	
	private ModelMapper mapper;
	
	private OwnerDTO mapToDTO(Owner owner) {
		return this.mapper.map(owner,OwnerDTO.class);
	}
	
	@Autowired
	public OwnerService(OwnerRepo repo, ModelMapper mapper) {
		this.repo = repo; 
		this.mapper = mapper; 
	}
	
	// create
	public OwnerDTO create(Owner o) {
		return this.mapToDTO(this.repo.save(o));
	}
	
	
	// read
	public List<OwnerDTO> read(){
		List<OwnerDTO> dtos = new ArrayList<>(); 
		for(Owner o : this.repo.findAll()) {
			dtos.add(this.mapToDTO(o));
		}
		return dtos; 
	}
	
	
	// read one
	public OwnerDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}
	
	// update
	public OwnerDTO update(Long id, OwnerDTO o) {
		Owner existing = this.repo.findById(id).orElseThrow(); 
		existing.setAge(o.getAge());
		existing.setName(o.getName());
		return this.mapToDTO(this.repo.save(existing));
	}
	
	// delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
