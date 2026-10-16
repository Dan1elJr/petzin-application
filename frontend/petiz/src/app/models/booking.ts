export class Booking {
  constructor(
    public id: number | null = null,
    public petOwnerId: number | null = null,
    public petId: number | null = null,
    public petCheckinDate = '',
    public petCheckoutDate = '',
    public bookingCost: number | null = null,
    public kannel: number | null = null,
  ) {}
}
