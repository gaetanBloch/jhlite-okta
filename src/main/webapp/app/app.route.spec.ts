import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { routes } from './app.route';

describe('AppRoutes', () => {
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes(routes)],
    }).compileComponents();
    router = TestBed.inject(Router);
    router.initialNavigation();
  });

  it('should be defined', () => {
    expect(routes).toBeDefined();
  });

  // jhipster-needle-angular-menu
});
