import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home/home.component';
import {LayoutComponent} from './layout/layout.component';
import {HeaderComponent} from './layout/header/header.component';
import {FooterComponent} from './layout/footer/footer.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    HomeComponent,
    LayoutComponent,
    HeaderComponent,
    FooterComponent
  ],
  exports: [
    LayoutComponent
  ]
})
export class UiModule {
}
