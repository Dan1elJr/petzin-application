import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Booking } from '../models/booking';
import { PetOwner } from '../models/pet-owner';
import { Pet } from '../models/pet';
import { BookingService } from '../service/booking-service';
import { PetOwnerService } from '../service/pet-owner-service';
import { PetService } from '../service/pet-service';
import { PetOwnerForm } from '../pet-owner-form/pet-owner-form';
@Component({
  selector: 'app-booking-editor',
  imports: [FormsModule, NgIf, NgFor, RouterLink, PetOwnerForm],
  templateUrl: './booking-editor.html',
  styleUrl: './booking-editor.css',
})
export class BookingEditor implements OnInit {
  booking = new Booking();
  owners: PetOwner[] = [];
  pets: Pet[] = [];
  selectedOwner: PetOwner | null = null;
  mode = 'existing';
  query = '';
  loading = false;
  busy = false;
  error = '';
  constructor(
    private bookingsApi: BookingService,
    private ownersApi: PetOwnerService,
    private petsApi: PetService,
    private route: ActivatedRoute,
    private router: Router,
    private changeDetector: ChangeDetectorRef,
  ) {}
  ngOnInit() {
    this.loading = true;
    this.ownersApi.findAll().subscribe({
      next: (owners) => {
        this.owners = owners;
        this.loading = false;
        this.changeDetector.markForCheck();
      },
      error: (e) => this.showError(e),
    });
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id)
      this.bookingsApi.findById(id).subscribe({
        next: (booking) => {
          this.booking = booking;
          this.loadOwner(booking.petOwnerId!, false);
        },
        error: (e) => this.showError(e),
      });
  }
  matches() {
    const query = this.query.trim().toLowerCase();
    return query
      ? this.owners.filter((owner) =>
          (owner.name + ' ' + owner.telephoneNumber + ' ' + owner.id).toLowerCase().includes(query),
        )
      : [];
  }
  clearOwner() {
    this.selectedOwner = null;
    this.booking.petId = null;
  }
  loadOwner(id: number, resetPet = true) {
    this.selectedOwner = null;
    if (resetPet) this.booking.petId = null;
    this.busy = true;
    this.error = '';
    this.ownersApi.findById(id).subscribe({
      next: (owner) => {
        this.petsApi.findAll().subscribe({
          next: (pets) => {
            this.selectedOwner = owner;
            this.pets = pets.filter((pet) => pet.petOwnerId === id);
            this.query = owner.name;
            this.busy = false;
            this.changeDetector.markForCheck();
          },
          error: (e) => this.showError(e),
        });
      },
      error: (e) => this.showError(e),
    });
  }
  ownerSaved(owner: PetOwner) {
    if (!this.owners.some((item) => item.id === owner.id)) this.owners.push(owner);
    this.loadOwner(owner.id!);
  }
  save() {
    if (this.busy) return;
    this.error = '';
    if (!this.selectedOwner || !this.booking.petId) return;
    this.booking.petOwnerId = this.selectedOwner.id;
    this.busy = true;
    const request = this.booking.id
      ? this.bookingsApi.update(this.booking.id, this.booking)
      : this.bookingsApi.create(this.booking);
    request.subscribe({
      next: () => {
        this.busy = false;
        this.router.navigate(['/bookings']);
        this.changeDetector.markForCheck();
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
