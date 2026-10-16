package com.daniel.application.petiz.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name="booking")
public class Booking {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pet_owner_id")
	private PetOwner petOwnerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_id", nullable = false)
	private Pet pet;

	@Column(name = "checkin_date", nullable = false)
	private LocalDate petCheckinDate;

	@Column(name = "checkout_date", nullable = false)
	private LocalDate petCheckoutDate;
	
	@Column(name = "booking_cost")
	private BigDecimal bookingCost;
	
	@Column(name="kannel")
	private Integer kannel;


	public Integer getId() {
		return id;
	}
	
	public Booking() {}

	public Booking(Integer id, PetOwner petOwnerId, Pet pet, LocalDate petCheckinDate, LocalDate petCheckoutDate,
			BigDecimal bookingCost, Integer kannel) {
		super();
		this.id = id;
		this.petOwnerId = petOwnerId;
		this.pet = pet;
		this.petCheckinDate = petCheckinDate;
		this.petCheckoutDate = petCheckoutDate;
		this.bookingCost = bookingCost;
		this.kannel = kannel;
	}

	public PetOwner getPetOwnerId() {
		return petOwnerId;
	}

	public void setPetOwnerId(PetOwner petOwnerId) {
		this.petOwnerId = petOwnerId;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public LocalDate getPetCheckinDate() {
		return petCheckinDate;
	}

	public void setPetCheckinDate(LocalDate petCheckinDate) {
		this.petCheckinDate = petCheckinDate;
	}

	public LocalDate getPetCheckoutDate() {
		return petCheckoutDate;
	}

	public void setPetCheckoutDate(LocalDate petCheckoutDate) {
		this.petCheckoutDate = petCheckoutDate;
	}

	public BigDecimal getBookingCost() {
		return bookingCost;
	}

	public void setBookingCost(BigDecimal bookingCost) {
		this.bookingCost = bookingCost;
	}

	public Integer getKannel() {
		return kannel;
	}

	public void setKannel(Integer kannel) {
		this.kannel = kannel;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
