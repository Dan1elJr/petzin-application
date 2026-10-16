import { Routes } from '@angular/router';
import { BookingEditor } from './booking-editor/booking-editor';
import { BookingList } from './booking-list/booking-list';
import { PetOwnerEditor } from './pet-owner-editor/pet-owner-editor';
import { PetOwnerList } from './pet-owner-list/pet-owner-list';

export const routes: Routes = [
  { path: '', redirectTo: 'bookings', pathMatch: 'full' },
  { path: 'owners', component: PetOwnerList },
  { path: 'owners/new', component: PetOwnerEditor },
  { path: 'owners/:id', component: PetOwnerEditor },
  { path: 'bookings', component: BookingList },
  { path: 'bookings/new', component: BookingEditor },
  { path: 'bookings/:id', component: BookingEditor },
  { path: '**', redirectTo: 'bookings' },
];
