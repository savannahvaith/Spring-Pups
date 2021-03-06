package com.qa.springpups.dto;

import com.qa.springpups.domain.Owner;

public class PuppyDTO {
	
	private Long id; 
	private String name; 
	private int age; 
	private String breed; 
	private boolean skill; 
	
	// default constructor
	public PuppyDTO() { 
		
	}
	
	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public boolean isSkill() {
		return skill;
	}

	public void setSkill(boolean skill) {
		this.skill = skill;
	}
	
}
