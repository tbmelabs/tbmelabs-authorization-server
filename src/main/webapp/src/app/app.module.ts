import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {UiModule} from './ui/ui.module';
import {TitleService} from './services/routing/title.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule.withServerTransition({appId: 'tbme-labs-authorization-server'}),
    AppRoutingModule,
    UiModule
  ],
  providers: [
    TitleService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {

  constructor(titleService: TitleService) {
    titleService.init();
  }
}
