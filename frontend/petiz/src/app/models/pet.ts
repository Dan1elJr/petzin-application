export class Pet {
  constructor(
    public id: number | null = null,
    public petName = '',
    public petBreed = '',
    public petOwnerId: number | null = null,
  ) {}
}
