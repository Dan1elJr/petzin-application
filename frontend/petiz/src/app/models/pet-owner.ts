// Contrato REST de PetOwner: relacionamentos representados por IDs.
export class PetOwner {
  constructor(
    public id: number | null = null,
    public name = '',
    public telephoneNumber = '',
    public petIds: number[] = [],
    public bookingIds: number[] = [],
  ) {}
}
