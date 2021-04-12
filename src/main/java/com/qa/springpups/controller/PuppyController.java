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
import com.qa.springpups.service.PuppyService;

@RestController
@RequestMapping("/puppy")
public class PuppyController {

	private PuppyService service; 
	
	public PuppyController(PuppyService service) {
		this.service = service; 
	}
	
	@PostMapping("/create")
	public ResponseEntity<Puppy> create(@RequestBody Puppy pup){
		return new ResponseEntity<Puppy>(this.service.createPuppy(pup), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Puppy>> getAll(){
		return ResponseEntity.ok(this.service.readPuppies());
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<Puppy> getOne(@PathVariable long id){
		return ResponseEntity.ok(this.service.getPuppyById(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Puppy> updatePuppy(@PathVariable long id, @RequestBody Puppy newpup){
		return ResponseEntity.ok(this.service.updatePuppy(id, newpup));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deletePuppy(@PathVariable long id){
		return ResponseEntity.ok(this.service.deletePuppy(id));
	}
	
}
