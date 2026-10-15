package com.daniel.application.petiz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.entity.PetOwner;
import com.daniel.application.petiz.repository.PetOwnerRepository;

@SpringBootApplication
public class PetizApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetizApplication.class, args);
	}
	

@Bean
public CommandLineRunner run(
        PetOwnerRepository petOwnerRepository) {

    return args -> {

        // Criando o proprietário
        PetOwner owner = new PetOwner();
        owner.setName("João Silva");
        owner.setTelephone_number(994371301);

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
    };
}


}
