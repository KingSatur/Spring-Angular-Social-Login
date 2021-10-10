import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthState';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  constructor() {}

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }

  public setToken(token: string) {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }

  public removeToken() {
    localStorage.removeItem(TOKEN_KEY);
  }
}
