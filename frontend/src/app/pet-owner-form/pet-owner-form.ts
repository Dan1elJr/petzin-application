import { Component, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Pet } from '../models/pet';
import { PetOwner } from '../models/pet-owner';
import { PetOwnerService } from '../service/pet-owner-service';
import { PetService } from '../service/pet-service';
@Component({
  selector: 'app-pet-owner-form',
  imports: [FormsModule, NgIf, NgFor],
  inputs: ['owner', 'pets'],
  outputs: ['saved'],
  templateUrl: './pet-owner-form.html',
  styleUrl: './pet-owner-form.css',
})
export class PetOwnerForm {
  owner = new PetOwner();
  pets: Pet[] = [new Pet()];
  saved = new EventEmitter<PetOwner>();
  busy = false;
  error = '';
  constructor(
    private ownersApi: PetOwnerService,
    private petsApi: PetService,
    private changeDetector: ChangeDetectorRef,
  ) {}
  addPet() {
    this.pets.push(new Pet());
  }
  removePet(index: number) {
    const pet = this.pets[index];
    if (!pet.id) {
      this.pets.splice(index, 1);
      return;
    }
    if (!window.confirm('Excluir o animal ' + pet.petName + '?')) return;
    this.busy = true;
    this.petsApi.delete(pet.id).subscribe({
      next: () => {
        this.pets.splice(index, 1);
        this.busy = false;
        this.changeDetector.markForCheck();
      },
      error: (e) => this.showError(e),
    });
  }
  save() {
    if (this.busy) return;
    this.error = '';
    this.busy = true;
    if (this.owner.id) {
      this.ownersApi.findById(this.owner.id).subscribe({
        next: (current) => {
          this.owner.petIds = current.petIds;
          this.owner.bookingIds = current.bookingIds;
          this.saveOwner();
        },
        error: (e) => this.showError(e),
      });
    } else {
      this.saveOwner();
    }
  }
  saveOwner() {
    const request = this.owner.id
      ? this.ownersApi.update(this.owner.id, this.owner)
      : this.ownersApi.create(this.owner);
    request.subscribe({
      next: (owner) => {
        this.owner.id = owner.id;
        this.savePet(0);
      },
      error: (e) => this.showError(e),
    });
  }
  savePet(index: number) {
    if (index === this.pets.length) {
      this.busy = false;
      this.saved.emit(this.owner);
      this.changeDetector.markForCheck();
      return;
    }
    const pet = this.pets[index];
    pet.petOwnerId = this.owner.id;
    const request = pet.id ? this.petsApi.update(pet.id, pet) : this.petsApi.create(pet);
    request.subscribe({
      next: (saved) => {
        pet.id = saved.id;
        this.savePet(index + 1);
      },
      error: (e) => this.showError(e),
    });
  }
  showError(error: { error?: { detail?: string } }) {
    this.error =
      error.error?.detail || 'Não foi possível salvar. Verifique o backend e tente novamente.';
    this.busy = false;
    this.changeDetector.markForCheck();
  }
}
