export class Product {
  name?: string;
  price?: number;

  constructor(json: any) {
    this.name = json?.name;
    this.price = json?.price;
  }
}
