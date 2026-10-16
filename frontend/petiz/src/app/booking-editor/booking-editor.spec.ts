import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ActivatedRoute, Router, provideRouter } from '@angular/router';
import { BookingEditor } from './booking-editor';

describe('BookingEditor', () => {
  let editor: BookingEditor;
  let fixture: ComponentFixture<BookingEditor>;
  let http: HttpTestingController;
  const owner = { id: 1, name: 'Ana', telephoneNumber: '092 99999-9999', petIds: [10], bookingIds: [] };
  const pet = { id: 10, petName: 'Rex', petBreed: 'SRD', petOwnerId: 1 };
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [BookingEditor],
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: new Map() } } },
      ],
    });
    fixture = TestBed.createComponent(BookingEditor);
    editor = fixture.componentInstance;
    http = TestBed.inject(HttpTestingController);
    Object.assign(editor.booking, {
      petId: 10,
      petCheckinDate: '2026-10-16',
      petCheckoutDate: '2026-10-17',
      bookingCost: 50,
      kannel: 3,
    });
    vi.spyOn(TestBed.inject(Router), 'navigate').mockResolvedValue(true);
  });
  afterEach(() => http.verify());
  it('cadastra tutor e animal na tela e depois envia a estadia com ngModel', async () => {
    fixture.detectChanges();
    http.expectOne('/api/owners').flush([]);
    await fixture.whenStable();
    const host = fixture.nativeElement as HTMLElement;
    (host.querySelector('#newOwner') as HTMLInputElement).click();
    await fixture.whenStable();
    async function fill(id: string, value: string) {
      const input = host.querySelector('#' + id) as HTMLInputElement;
      input.value = value;
      input.dispatchEvent(new Event('input', { bubbles: true }));
      await fixture.whenStable();
    }
    await fill('ownerName', 'Ana');
    await fill('telephone', '092 99999-9999');
    await fill('petName0', 'Rex');
    await fill('petBreed0', 'SRD');
    host
      .querySelector('form')!
      .dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));
    const createOwner = http.expectOne('/api/owners');
    expect(createOwner.request.body.telephoneNumber).toBe('092 99999-9999');
    createOwner.flush(owner);
    http.expectOne('/api/pets').flush(pet);
    http.expectOne('/api/owners/1').flush(owner);
    http.expectOne('/api/pets').flush([pet]);
    await fixture.whenStable();
    const select = host.querySelector('#bookingPet') as HTMLSelectElement;
    select.selectedIndex = 1;
    select.dispatchEvent(new Event('change', { bubbles: true }));
    await fixture.whenStable();
    await fill('checkin', '2026-10-16');
    await fill('checkout', '2026-10-17');
    await fill('cost', '50');
    await fill('kannel', '4');
    host
      .querySelector('form')!
      .dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));
    const request = http.expectOne('/api/bookings');
    expect(request.request.body.petId).toBe(10);
    expect(request.request.body.kannel).toBe(4);
    request.flush({ ...request.request.body, id: 7 });
  });
  it('valida o tutor e carrega somente os animais dele', () => {
    editor.loadOwner(1);
    http.expectOne('/api/owners/1').flush(owner);
    http.expectOne('/api/pets').flush([pet, { ...pet, id: 11, petOwnerId: 2 }]);
    expect(editor.selectedOwner?.id).toBe(1);
    expect(editor.pets).toEqual([pet]);
    editor.clearOwner();
    expect(editor.selectedOwner).toBeNull();
    expect(editor.booking.petId).toBeNull();
  });
  it('deixa a validação da estadia no backend e mostra sua resposta', () => {
    editor.selectedOwner = owner;
    editor.pets = [pet];
    editor.booking.petCheckoutDate = editor.booking.petCheckinDate;
    editor.save();
    http
      .expectOne('/api/bookings')
      .flush(
        { detail: 'A saída deve ser posterior à entrada.' },
        { status: 400, statusText: 'Bad Request' },
      );
    expect(editor.error).toBe('A saída deve ser posterior à entrada.');
    expect(editor.busy).toBe(false);
    expect(editor.booking.petCheckoutDate).toBe(editor.booking.petCheckinDate);
  });
  it('envia o canil e impede envio duplo', () => {
    editor.selectedOwner = owner;
    editor.pets = [pet];
    editor.save();
    editor.save();
    const request = http.expectOne('/api/bookings');
    expect(request.request.method).toBe('POST');
    expect(request.request.body.kannel).toBe(3);
    expect(request.request.body.petOwnerId).toBe(1);
    request.flush({ ...request.request.body, id: 7 });
    expect(TestBed.inject(Router).navigate).toHaveBeenCalledWith(['/bookings']);
  });
  it('mantém o canil ao carregar e editar', () => {
    (TestBed.inject(ActivatedRoute).snapshot.paramMap as unknown as Map<string, string>).set(
      'id',
      '7',
    );
    editor.ngOnInit();
    http.expectOne('/api/owners').flush([owner]);
    http.expectOne('/api/bookings/7').flush({ ...editor.booking, id: 7, petOwnerId: 1, kannel: 8 });
    http.expectOne('/api/owners/1').flush(owner);
    http.expectOne('/api/pets').flush([pet]);
    editor.save();
    const request = http.expectOne('/api/bookings/7');
    expect(request.request.method).toBe('PUT');
    expect(request.request.body.kannel).toBe(8);
    request.flush(request.request.body);
  });
});
