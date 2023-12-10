import LoginComponent from './login/login.component';
import { Oauth2AuthService } from './auth/oauth2-auth.service';
import { Component, inject, OnInit } from '@angular/core';

import { CommonModule, NgOptimizedImage } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'jhi-root',
  templateUrl: './app.component.html',
  imports: [CommonModule, RouterModule, MatMenuModule, MatToolbarModule, MatIconModule, MatButtonModule, NgOptimizedImage, LoginComponent],
  standalone: true,
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  appName = '';
  private oauth2AuthService = inject(Oauth2AuthService);

  ngOnInit(): void {
    this.appName = 'jhipsterSampleApplication';
    this.oauth2AuthService.initAuthentication();
  }
}
