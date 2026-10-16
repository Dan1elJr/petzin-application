import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pet } from '../models/pet';
@Service()
export class PetService {
  private url = '/api/pets';

  private http = inject(HttpClient);
  findAll() {
    return this.http.get<Pet[]>(this.url);
  }
  findById(id: number) {
    return this.http.get<Pet>(`${this.url}/${id}`);
  }
  create(pet: Pet) {
    return this.http.post<Pet>(this.url, pet);
  }
  update(id: number, pet: Pet) {
    return this.http.put<Pet>(`${this.url}/${id}`, pet);
  }
  delete(id: number) {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
