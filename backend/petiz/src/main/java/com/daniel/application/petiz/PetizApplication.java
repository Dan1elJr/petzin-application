package com.daniel.application.petiz;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.daniel.application.petiz.entity.Booking;
import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.entity.PetOwner;
import com.daniel.application.petiz.repository.BookingRepository;
import com.daniel.application.petiz.repository.PetOwnerRepository;

@SpringBootApplication
public class PetizApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetizApplication.class, args);
	}
	

@Bean
public CommandLineRunner run(
        PetOwnerRepository petOwnerRepository, BookingRepository bookingRepository) {

    return args -> {

        // Criando o proprietário
        PetOwner owner = new PetOwner();
        owner.setName("João Silva");
        owner.setTelephoneNumber(994371301);

        // Criando o animal
        Pet pet = new Pet();
        pet.setPetName("Rex");;

        // Definindo o proprietário do animal
        pet.setPetOwner(owner);

        // Associando o animal à lista do proprietário
        owner.getPet().add(pet);

        // Salvando proprietário e animal
        petOwnerRepository.save(owner);

        System.out.println(
            "Proprietário e animal cadastrados!"
        );
        
        Booking booking = new Booking();
        booking.setPet(pet);
        booking.setPetOwnerId(owner);
        booking.setPetCheckinDate(LocalDate.now());
        booking.setPetCheckoutDate(LocalDate.now().plusDays(1));
        booking.setBookingCost(BigDecimal.valueOf(10000));
        
        bookingRepository.save(booking);
        
        
    };
}


}
