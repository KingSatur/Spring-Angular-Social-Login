import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  GoogleLoginProvider,
  SocialAuthService,
  SocialUser,
} from 'angularx-social-login';
import { BehaviorSubject, from, Observable, of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { AuthRequestDto, AuthState } from '../models/token-dto';
import { TokenService } from './token.service';

const BASE_URL = 'http://localhost:9091';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private currentUserSubject: BehaviorSubject<AuthState>;
  public currentUser: Observable<AuthState>;

  constructor(
    private readonly authService: SocialAuthService,
    private readonly httpClient: HttpClient,
    private readonly tokenService: TokenService,
    private readonly router: Router
  ) {
    this.currentUserSubject = new BehaviorSubject(
      JSON.parse(this.tokenService.getToken())
    );
    this.currentUser = this.currentUserSubject?.asObservable();
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  public googleLogin() {
    return from(this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)).pipe(
      switchMap((m) => {
        return this.httpClient
          .post(`${BASE_URL}/auth/google`, new AuthRequestDto(m?.idToken))
          .pipe(
            map((m: any) => new AuthState(m)),
            tap((authState) => {
              authState.photo_url = m?.photoUrl;
              authState.google_id = m?.idToken;
              this.tokenService.setToken(JSON.stringify(authState));
              this.currentUserSubject.next(authState);
              this.router.navigate(['/home']);
            })
          );
      }),
      catchError((err) => {
        console.log(err);
        return of(null);
      })
    );
  }

  public signOut() {
    this.currentUserSubject.next(null);
    this.tokenService.removeToken();
  }
}
