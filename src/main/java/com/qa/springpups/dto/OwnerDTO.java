package com.qa.springpups.dto;

import java.util.ArrayList;
import java.util.List;

public class OwnerDTO {

	private Long id;
	private String name;
	private int age;
	private List<PuppyDTO> puppies = new ArrayList<>();

	public OwnerDTO() {}


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

	public List<PuppyDTO> getPuppies() {
		return puppies;
	}

	public void setPuppies(List<PuppyDTO> puppies) {
		this.puppies = puppies;
	}
}


