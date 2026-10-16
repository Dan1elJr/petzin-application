import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Booking } from '../models/booking';
import { PetOwner } from '../models/pet-owner';
import { Pet } from '../models/pet';
import { BookingService } from '../service/booking-service';
import { PetOwnerService } from '../service/pet-owner-service';
import { PetService } from '../service/pet-service';

@Component({
  selector: 'app-booking-list',
  imports: [NgIf, NgFor, CurrencyPipe, DatePipe, FormsModule, RouterLink],
  templateUrl: './booking-list.html',
  styleUrl: './booking-list.css',
})
export class BookingList implements OnInit {
  bookings: Booking[] = [];
  owners: PetOwner[] = [];
  pets: Pet[] = [];
  loading = true;
  busy = false;
  error = '';
  query = '';
  constructor(
    private bookingsApi: BookingService,
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
    this.bookingsApi.findAll().subscribe({
      next: (bookings) => {
        this.bookings = bookings;
        this.loading = false;
        this.changeDetector.markForCheck();
      },
      error: (e) => this.showError(e),
    });
  }
  ownerName(booking: Booking) {
    const ownerId =
      booking.petOwnerId ?? this.pets.find((pet) => pet.id === booking.petId)?.petOwnerId;
    return this.owners.find((owner) => owner.id === ownerId)?.name ?? 'Tutor não encontrado';
  }
  petName(id: number | null) {
    return this.pets.find((pet) => pet.id === id)?.petName ?? 'Animal não encontrado';
  }
  filtered() {
    const query = this.query.trim().toLocaleLowerCase('pt-BR');
    return this.bookings.filter((booking) =>
      `${this.ownerName(booking)} ${this.petName(booking.petId)} ${booking.id}`
        .toLocaleLowerCase('pt-BR')
        .includes(query),
    );
  }
  remove(booking: Booking) {
    if (this.busy || !window.confirm('Excluir este registro?')) return;
    this.busy = true;
    this.bookingsApi.delete(booking.id!).subscribe({
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
