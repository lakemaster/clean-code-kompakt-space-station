
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SpaceStationService} from './services/spacestation/space-station.service';
import {FlexLayoutModule} from '@angular/flex-layout';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {BrowserModule} from '@angular/platform-browser';
import { SpaceModuleComponent } from './components/space-module/space-module.component';
import { NullValueSavePipe } from './pipes/empty-status-value.pipe';

@NgModule({
  declarations: [
    AppComponent,
    SpaceModuleComponent,
    NullValueSavePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FlexLayoutModule
  ],
  providers: [
    SpaceStationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
