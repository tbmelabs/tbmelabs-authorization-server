import {Injectable} from '@angular/core';

import {Title} from '@angular/platform-browser';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {filter, map, switchMap} from 'rxjs/operators';

const APP_TITLE = 'TBME Labs';
const SEPARATOR = '|';

@Injectable({
  providedIn: 'root'
})
export class TitleService {
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private titleService: Title,
  ) {
  }

  static ucFirst(string) {
    if (!string) {
      return string;
    }
    return string.charAt(0).toUpperCase() + string.slice(1);
  }

  init() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.activatedRoute),
      map(route => route.firstChild),
      switchMap(route => route.data),
      map((data) => {
        if (data.title) {
          // If a route has a title set (e.g. data: {title: "Foo"}) then we use it
          return `${SEPARATOR} ${data.title}`;
        }

        return '';
      })
    )
      .subscribe((pathString) => this.titleService.setTitle(`${APP_TITLE} ${pathString}`));
  }
}
