import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { PetOwnerForm } from './pet-owner-form';
import { PetOwner } from '../models/pet-owner';
import { Pet } from '../models/pet';

describe('PetOwnerForm', () => {
  let form: PetOwnerForm;
  let http: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PetOwnerForm],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    form = TestBed.createComponent(PetOwnerForm).componentInstance;
    http = TestBed.inject(HttpTestingController);
    form.owner = new PetOwner(null, 'Ana', '092 99999-9999');
    form.pets = [new Pet(null, 'Rex', 'SRD'), new Pet(null, 'Luna', 'Poodle')];
  });
  afterEach(() => http.verify());
  it('retoma cadastro parcial sem duplicar tutor e primeiro animal', () => {
    const owner = { ...form.owner, id: 1 };
    const firstPet = { ...form.pets[0], id: 10, petOwnerId: 1 };
    form.save();
    const create = http.expectOne('/api/owners');
    expect(create.request.body.telephoneNumber).toBe('092 99999-9999');
    create.flush(owner);
    http.expectOne('/api/pets').flush(firstPet);
    http
      .expectOne('/api/pets')
      .flush({ detail: 'Falha ao salvar' }, { status: 500, statusText: 'Error' });
    expect(form.owner.id).toBe(1);
    expect(form.pets[0].id).toBe(10);
    expect(form.pets[1].id).toBeNull();
    expect(form.error).toContain('Falha');
    const saved = vi.fn();
    form.saved.subscribe(saved);
    form.save();
    http.expectOne('/api/owners/1').flush({ ...owner, petIds: [10], bookingIds: [20] });
    const update = http.expectOne('/api/owners/1');
    expect(update.request.body.bookingIds).toEqual([20]);
    update.flush(owner);
    const petUpdate = http.expectOne('/api/pets/10');
    expect(petUpdate.request.method).toBe('PUT');
    petUpdate.flush(firstPet);
    http.expectOne('/api/pets').flush({ ...form.pets[1], id: 11 });
    expect(saved).toHaveBeenCalled();
    expect(form.busy).toBe(false);
  });
  it('mostra a mensagem de validação retornada pelo backend', () => {
    form.save();
    http
      .expectOne('/api/owners')
      .flush({ detail: 'Telefone inválido.' }, { status: 400, statusText: 'Bad Request' });
    expect(form.error).toBe('Telefone inválido.');
    expect(form.busy).toBe(false);
    http.expectNone('/api/pets');
  });
});
