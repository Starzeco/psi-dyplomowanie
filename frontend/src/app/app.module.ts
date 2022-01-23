import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { ComponentsModule } from './components/components.module';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ViewsModule } from './views/views.module';
import { RouterModule } from '@angular/router';
import { APP_ROUTES } from './app.routes';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GraduationProcessServiceService } from './shared/graduation-process-service.service';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ComponentsModule,
    ViewsModule,
    RouterModule.forRoot(APP_ROUTES),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (httpClient: HttpClient) =>
          // eslint-disable-next-line @typescript-eslint/no-unsafe-call, @typescript-eslint/no-unsafe-return
          new TranslateHttpLoader(httpClient),
        deps: [HttpClient]
      }
    })
  ],
  exports: [
    RouterModule
  ],
  providers: [
    GraduationProcessServiceService
  ],
  bootstrap: [
    AppComponent,
  ]
})

export class AppModule { }
