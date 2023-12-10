import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin-routing.module'),
  },
  // jhipster-needle-angular-route
];
