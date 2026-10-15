package com.daniel.application.petiz.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="client")
public class PetOwner {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="telephone_number")
	private int telephoneNumber;
	
	@OneToMany(
	        mappedBy = "petOwner",
	        cascade = CascadeType.ALL
	    )
	@Column(name="pet_name")
	private List<Pet> Pet = new ArrayList<Pet>();
	
	public PetOwner () {}

	public PetOwner(Integer id, String name, int telephone_number, List<Pet> pet) {
		super();
		this.id = id;
		this.name = name;
		this.telephoneNumber = telephone_number;
		Pet = pet;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTelephone_number() {
		return telephoneNumber;
	}

	public void setTelephone_number(int telephone_number) {
		this.telephoneNumber = telephone_number;
	}

	public List<Pet> getPet() {
		return Pet;
	}

	public void setPet(List<Pet> pet) {
		Pet = pet;
	}
	
}
