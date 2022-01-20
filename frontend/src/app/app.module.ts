import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { ComponentsModule } from './components/components.module';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ViewsModule } from './views/views.module';
import { AppRoutingModule } from "./app-routing.module";
import {StudentPageModule} from "./views/student-page/student-page.module";
import {SupervisorPageModule} from "./views/supervisor-page/supervisor-page.module";
import {VerifierPageModule} from "./views/verifier-page/verifier-page.module";


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ComponentsModule,
    ViewsModule,
    StudentPageModule,
    SupervisorPageModule,
    VerifierPageModule,
    AppRoutingModule,
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
  providers: [],
  bootstrap: [
    AppComponent,
  ]
})

export class AppModule { }
