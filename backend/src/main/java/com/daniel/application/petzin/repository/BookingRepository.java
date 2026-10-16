package com.daniel.application.petzin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.application.petzin.entity.Booking;


public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
