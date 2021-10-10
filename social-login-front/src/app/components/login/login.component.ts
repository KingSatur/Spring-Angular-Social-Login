import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  GoogleLoginProvider,
  SocialAuthService,
  SocialUser,
} from 'angularx-social-login';
import { LoginService } from 'src/app/services/login.service';
import { OauthService } from 'src/app/services/oauth.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  public socialUser!: SocialUser;
  public isLogged: boolean = false;

  constructor(
    private readonly router: Router,
    private readonly loginService: LoginService
  ) {}

  ngOnInit(): void {
    if (this.loginService.currentUserValue) {
      this.router.navigate(['/home']);
    }
  }

  signInWithGoogle() {
    this.loginService.googleLogin().subscribe((resp) => {
      console.log(resp);
    });
  }
}
