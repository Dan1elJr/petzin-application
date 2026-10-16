import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgIf } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PetOwnerService } from '../service/pet-owner-service';
import { PetService } from '../service/pet-service';
import { PetOwner } from '../models/pet-owner';
import { Pet } from '../models/pet';
import { PetOwnerForm } from '../pet-owner-form/pet-owner-form';
@Component({
  selector: 'app-pet-owner-editor',
  imports: [NgIf, RouterLink, PetOwnerForm],
  templateUrl: './pet-owner-editor.html',
  styleUrl: './pet-owner-editor.css',
})
export class PetOwnerEditor implements OnInit {
  owner = new PetOwner();
  pets: Pet[] = [new Pet()];
  loading = false;
  error = '';
  constructor(
    private ownersApi: PetOwnerService,
    private petsApi: PetService,
    private route: ActivatedRoute,
    private router: Router,
    private changeDetector: ChangeDetectorRef,
  ) {}
  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) return;
    this.loading = true;
    this.ownersApi.findById(id).subscribe({
      next: (owner) => {
        this.owner = owner;
        this.petsApi.findAll().subscribe({
          next: (pets) => {
            this.pets = pets.filter((pet) => pet.petOwnerId === id);
            this.loading = false;
            this.changeDetector.markForCheck();
          },
          error: () => this.showError(),
        });
      },
      error: () => this.showError(),
    });
  }
  saved() {
    this.router.navigate(['/owners']);
  }
  showError() {
    this.error = 'Não foi possível carregar o cadastro.';
    this.loading = false;
    this.changeDetector.markForCheck();
  }
}
