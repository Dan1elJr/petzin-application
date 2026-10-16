import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PetOwner } from '../models/pet-owner';
@Service()
export class PetOwnerService {
  private url = '/api/owners';

  private http = inject(HttpClient);
  findAll() {
    return this.http.get<PetOwner[]>(this.url);
  }
  findById(id: number) {
    return this.http.get<PetOwner>(`${this.url}/${id}`);
  }
  create(owner: PetOwner) {
    return this.http.post<PetOwner>(this.url, owner);
  }
  update(id: number, owner: PetOwner) {
    return this.http.put<PetOwner>(`${this.url}/${id}`, owner);
  }
  delete(id: number) {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
