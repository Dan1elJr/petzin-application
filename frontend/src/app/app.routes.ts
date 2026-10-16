import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'bookings', pathMatch: 'full' },
  {
    path: 'owners',
    loadComponent: () => import('./pet-owner-list/pet-owner-list').then((m) => m.PetOwnerList),
  },
  {
    path: 'owners/new',
    loadComponent: () =>
      import('./pet-owner-editor/pet-owner-editor').then((m) => m.PetOwnerEditor),
  },
  {
    path: 'owners/:id',
    loadComponent: () =>
      import('./pet-owner-editor/pet-owner-editor').then((m) => m.PetOwnerEditor),
  },
  {
    path: 'bookings',
    loadComponent: () => import('./booking-list/booking-list').then((m) => m.BookingList),
  },
  {
    path: 'bookings/new',
    loadComponent: () => import('./booking-editor/booking-editor').then((m) => m.BookingEditor),
  },
  {
    path: 'bookings/:id',
    loadComponent: () => import('./booking-editor/booking-editor').then((m) => m.BookingEditor),
  },
  { path: '**', redirectTo: 'bookings' },
];
