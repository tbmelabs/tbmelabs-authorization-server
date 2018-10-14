// Credits and thanks:
//   https://gist.github.com/pini-girit/2303195c240add54ae8ce68d78e0fe03
//
import {Injectable} from '@angular/core';

import {Title} from '@angular/platform-browser';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {filter, map, switchMap} from 'rxjs/operators';

const APP_TITLE = 'TBME Labs';
const SEPARATOR = '|';

@Injectable()
export class TitleService {
  static ucFirst(string) {
    if (!string) {
      return string;
    }
    return string.charAt(0).toUpperCase() + string.slice(1);
  }

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private titleService: Title,
  ) {
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
          return data.title;
        }

        return '';
      })
    )
      .subscribe((pathString) => this.titleService.setTitle(`${APP_TITLE} ${SEPARATOR} ${pathString}`));
  }
}
