import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import AdminRoutingModule, { routes } from './admin-routing.module';

describe('AdminRoutingModule', () => {
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes(routes)],
    }).compileComponents();
    router = TestBed.inject(Router);
    router.initialNavigation();
  });

  it('should be defined', () => {
    expect(AdminRoutingModule).toBeDefined();
  });

  it('should navigate on health endpoint', () => {
    router.navigateByUrl('/health');
  });
  // jhipster-needle-angular-menu
});
