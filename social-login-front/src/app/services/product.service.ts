import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  productUrl: string = 'http://localhost:9091/product/';

  constructor(private readonly httpClient: HttpClient) {}

  public getAll(): Observable<Product[]> {
    return this.httpClient
      .get(`${this.productUrl}`)
      .pipe(map((m: any) => m?.map((j: any) => new Product(j))));
  }
}
