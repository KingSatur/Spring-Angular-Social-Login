import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const headers = {
  headers: new HttpHeaders({
    'Content-type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class OauthService {
  oauthUrl = 'http://localhost:8080/';

  constructor(private readonly httpClient: HttpClient) {}
}
