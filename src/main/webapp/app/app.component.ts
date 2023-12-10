import { Component, OnInit } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'jhi-root',
  templateUrl: './app.component.html',
  imports: [CommonModule, RouterModule, MatMenuModule, MatToolbarModule, MatIconModule, MatButtonModule, NgOptimizedImage],
  standalone: true,
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  appName = '';

  ngOnInit(): void {
    this.appName = 'jhipsterSampleApplication';
  }
}
