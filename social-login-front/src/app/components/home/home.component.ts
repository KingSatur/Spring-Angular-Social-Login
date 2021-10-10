import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SocialAuthService, SocialUser } from 'angularx-social-login';
import { AuthState } from 'src/app/models/token-dto';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  public authState: AuthState;

  constructor(private readonly loginService: LoginService) {}

  ngOnInit(): void {
    this.loginService.currentUser.subscribe((resp) => {
      this.authState = resp;
    });
  }
}
