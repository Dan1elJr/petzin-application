import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Booking } from '../models/booking';
@Service()
export class BookingService {
  private url = '/api/bookings';

  private http = inject(HttpClient);
  findAll() {
    return this.http.get<Booking[]>(this.url);
  }
  findById(id: number) {
    return this.http.get<Booking>(`${this.url}/${id}`);
  }
  create(booking: Booking) {
    return this.http.post<Booking>(this.url, booking);
  }
  update(id: number, booking: Booking) {
    return this.http.put<Booking>(`${this.url}/${id}`, booking);
  }
  delete(id: number) {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
