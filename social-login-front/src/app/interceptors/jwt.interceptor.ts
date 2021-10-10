import { Injectable, Provider } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../services/token.service';
import { LoginService } from '../services/login.service';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(
    private readonly tokenService: TokenService,
    private readonly loginService: LoginService,
    private readonly router: Router
  ) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let req = request;
    const token = this.tokenService.getToken();
    if (token) {
      const tokenString = `Bearer ${
        JSON.parse(this.tokenService.getToken())?.token
      }`;
      req = req.clone({
        headers: request.headers.set('Authorization', tokenString),
      });
    }
    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        if (req.url.includes('/api')) {
          this.loginService.signOut();
          this.router.navigate(['/login']);
        }
        console.log(JSON.stringify(err));
        return next.handle(req);
      })
    );
  }
}

export const jwtInterceptor: Provider = {
  provide: HTTP_INTERCEPTORS,
  useClass: JwtInterceptor,
  multi: true,
};
