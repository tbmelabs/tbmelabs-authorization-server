import {TestBed} from '@angular/core/testing';

import {TitleService} from './title.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {routes} from '../../app-routing.module';
import {Observable} from 'rxjs';
import {RouterTestingModule} from '@angular/router/testing';
import {AppComponent} from '../../app.component';
import {HomeComponent} from '../../ui/home/home.component';
import {LayoutComponent} from '../../ui/layout/layout.component';
import {HeaderComponent} from '../../ui/layout/header/header.component';
import {FooterComponent} from '../../ui/layout/footer/footer.component';

let router: Router;
let fixture;

describe('TitleService', () => {
  beforeEach(() => {
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
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            params: Observable.create({id: 123})
          }
        },
        Title
      ]
    });

    router = TestBed.get(Router);

    fixture = TestBed.createComponent(TitleService);
    router.initialNavigation();
  });

  it('should be created', () => {
    expect(fixture).toBeTruthy();
  });
});
