package com.daniel.application.petiz.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Booking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="pet_owner")
	private PetOwner petOwner;
	
	@Column(name="pet")
	private Pet pet;
	
	@Column(name = "checkin_date", nullable = false)
	private LocalDate petCheckinDate;
	
	@Column(name = "checkout_date", nullable = false)
	private LocalDate petCheckoutDate;
	
}
