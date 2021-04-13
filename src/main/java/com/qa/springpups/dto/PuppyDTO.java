package com.qa.springpups.dto;

public class PuppyDTO {
	
	private Long id; 
	private String name; 
	private int age; 
	private String breed; 
	private String owner; 
	private boolean skill; 
	
	// default constructor
	public PuppyDTO() { 
		
	}

	// constructor with params
	public PuppyDTO(String name, int age, String breed, String owner, boolean skill) {
		this.name = name; 
		this.age = age; 
		this.breed = breed; 
		this.owner = owner; 
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isSkill() {
		return skill;
	}

	public void setSkill(boolean skill) {
		this.skill = skill;
	}
	
	
	
}
