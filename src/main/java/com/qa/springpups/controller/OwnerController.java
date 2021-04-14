package com.qa.springpups.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springpups.domain.Owner;
import com.qa.springpups.dto.OwnerDTO;
import com.qa.springpups.service.OwnerService;

@RestController
@RequestMapping("/owner")
public class OwnerController {

	private OwnerService service;
	
	public OwnerController(OwnerService service) {
		this.service = service; 
	}
	
	@PostMapping("/create")
	public ResponseEntity<OwnerDTO> create(@RequestBody Owner owner){
		OwnerDTO created = this.service.create(owner);
		return new ResponseEntity<OwnerDTO>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("/readAll")
	public ResponseEntity<List<OwnerDTO>> read(){
		return ResponseEntity.ok(this.service.read());
	}
	
	@GetMapping("/readOne/{id}")
	public ResponseEntity<OwnerDTO> readOne(@PathVariable Long id){
		return ResponseEntity.ok(this.service.readOne(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<OwnerDTO> update(@PathVariable Long id, @RequestBody OwnerDTO owner){
		return new ResponseEntity<OwnerDTO>(this.service.update(id, owner),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
