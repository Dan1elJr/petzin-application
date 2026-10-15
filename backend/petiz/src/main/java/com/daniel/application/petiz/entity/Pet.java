package com.daniel.application.petiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="pet")
public class Pet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="pet_name")
	private String petName;
	@Column(name="pet_breed")
	private String petBreed;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_owner_id", nullable = false)
	private PetOwner petOwner;
	
	public Pet() {}
	
	public Pet(Integer id, String petName, String petBreed, PetOwner petOwner) {
		this.id = id;
		this.petName = petName;
		this.petBreed = petBreed;
		this.petOwner = petOwner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetBreed() {
		return petBreed;
	}

	public void setPetBreed(String petBreed) {
		this.petBreed = petBreed;
	}

	public PetOwner getPetOwner() {
		return petOwner;
	}

	public void setPetOwner(PetOwner petOwner) {
		this.petOwner = petOwner;
	}

	
	
	
	
}
