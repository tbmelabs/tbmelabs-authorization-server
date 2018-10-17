import {async, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {LayoutComponent} from './ui/layout/layout.component';
import {HeaderComponent} from './ui/layout/header/header.component';
import {FooterComponent} from './ui/layout/footer/footer.component';
import {routes} from './app-routing.module';
import {HomeComponent} from './ui/home/home.component';
import {RouterTestingModule} from '@angular/router/testing';

let fixture;

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes)
      ],
      declarations: [
        AppComponent,
        LayoutComponent,
        HeaderComponent,
        FooterComponent,
        HomeComponent
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
  }));

  it('should create the app', () => {
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'tbmelabs-authorization-server'`, () => {
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('tbmelabs-authorization-server');
  });
});
