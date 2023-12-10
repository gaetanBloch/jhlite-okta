import { By } from '@angular/platform-browser';
import { Oauth2AuthService } from "./auth/oauth2-auth.service";
import LoginComponent from './login/login.component';
import { PRECONNECT_CHECK_BLOCKLIST } from '@angular/common';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { AppComponent } from './app.component';

describe('App Component', () => {
  let comp: AppComponent;
  let oauth2AuthService: Oauth2AuthService;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        imports: [RouterTestingModule.withRoutes([])],
        providers: [
          { provide: PRECONNECT_CHECK_BLOCKLIST, useValue: 'https://jestjs.io' }
        ],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    comp = fixture.componentInstance;
    oauth2AuthService = TestBed.inject(Oauth2AuthService);
  });

  describe('ngOnInit', () => {
    it('should have appName', () => {
      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.appName).toEqual('jhipsterSampleApplication');
    });
    
    it('should display login component', () => {
      fixture.detectChanges();
    
      expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
    });

  });

});
