import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent implements OnInit {
  constructor(
    public readonly loginService: LoginService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.loginService.currentUser.subscribe((resp) => {});
  }

  async logout() {
    await this.loginService.signOut();
    this.router.navigate(['/login']);
  }
}
