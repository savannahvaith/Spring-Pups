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

import com.qa.springpups.domain.Puppy;
import com.qa.springpups.dto.PuppyDTO;
import com.qa.springpups.service.PuppyService;


@RestController
@RequestMapping("/puppy")
public class PuppyController{

	private PuppyService service; 
	
	public PuppyController(PuppyService service) {
		this.service = service; 
	}
	
	@PostMapping("/create")
	public ResponseEntity<PuppyDTO> create(@RequestBody Puppy pup){
		return new ResponseEntity<PuppyDTO>(this.service.create(pup), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<PuppyDTO>> getAll(){
		return ResponseEntity.ok(this.service.read());
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<PuppyDTO> getOne(@PathVariable long id){
		return ResponseEntity.ok(this.service.getById(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<PuppyDTO> updatePuppyDTO(@PathVariable long id, @RequestBody Puppy newpup){
		return new ResponseEntity<>(this.service.update(id, newpup), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deletePuppyDTO(@PathVariable long id){
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}