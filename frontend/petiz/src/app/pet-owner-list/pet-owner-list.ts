import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { PetOwnerService } from '../service/pet-owner-service';
import { PetService } from '../service/pet-service';
import { PetOwner } from '../models/pet-owner';
import { Pet } from '../models/pet';

@Component({
  selector: 'app-pet-owner-list',
  imports: [NgIf, NgFor, FormsModule, RouterLink],
  templateUrl: './pet-owner-list.html',
  styleUrl: './pet-owner-list.css',
})
export class PetOwnerList implements OnInit {
  owners: PetOwner[] = [];
  pets: Pet[] = [];
  loading = true;
  busy = false;
  error = '';
  query = '';
  constructor(
    private ownersApi: PetOwnerService,
    private petsApi: PetService,
    private changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.load();
  }
  load() {
    this.loading = true;
    this.error = '';
    this.ownersApi.findAll().subscribe({
      next: (owners) => {
        this.owners = owners;
        this.loading = false;
        this.changeDetector.markForCheck();
      },
      error: (e) => this.showError(e),
    });
    this.petsApi.findAll().subscribe({
      next: (pets) => {
        this.pets = pets;
        this.changeDetector.markForCheck();
      },
      error: (e) => this.showError(e),
    });
  }
  filtered() {
    const query = this.query.trim().toLocaleLowerCase('pt-BR');
    return this.owners.filter((owner) =>
      `${owner.name} ${owner.telephoneNumber} ${owner.id}`
        .toLocaleLowerCase('pt-BR')
        .includes(query),
    );
  }
  petNames(id: number | null) {
    return (
      this.pets
        .filter((pet) => pet.petOwnerId === id)
        .map((pet) => pet.petName)
        .join(', ') || 'Nenhum animal'
    );
  }
  remove(owner: PetOwner) {
    if (this.busy || !window.confirm('Excluir este registro?')) return;
    this.busy = true;
    this.ownersApi.delete(owner.id!).subscribe({
      next: () => {
        this.busy = false;
        this.load();
      },
      error: (e) => this.showError(e),
    });
  }
  showError(error: { error?: { detail?: string } }) {
    this.error =
      error.error?.detail || 'Não foi possível concluir a operação. Verifique o backend.';
    this.loading = false;
    this.busy = false;
    this.changeDetector.markForCheck();
  }
}
