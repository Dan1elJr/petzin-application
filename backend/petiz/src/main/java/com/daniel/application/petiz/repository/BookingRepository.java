package com.daniel.application.petiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.application.petiz.entity.Booking;


public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
