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
@Table(name="pet_owner")
public class PetOwner {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="telephone_number")
	private String telephoneNumber;

	@OneToMany(mappedBy = "petOwner",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<Pet> pet = new ArrayList<Pet>();

	@OneToMany(mappedBy="petOwnerId",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<Booking> bookingList = new ArrayList<>();


	public PetOwner () {}


	public PetOwner(Integer id, String name, String telephoneNumber, List<Pet> pet, List<Booking> bookingList) {
		super();
		this.id = id;
		this.name = name;
		this.telephoneNumber = telephoneNumber;
		this.pet = pet;
		this.bookingList = bookingList;
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


	public String getTelephoneNumber() {
		return telephoneNumber;
	}


	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}


	public List<Pet> getPet() {
		return pet;
	}


	public void setPet(List<Pet> pet) {
		this.pet = pet;
	}


	public List<Booking> getBookingList() {
		return bookingList;
	}


	public void setBookingList(List<Booking> bookingList) {
		this.bookingList = bookingList;
	}

	
}
