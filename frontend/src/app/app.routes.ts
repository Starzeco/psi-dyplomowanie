import { Routes } from '@angular/router';
import { ViewsModule } from './views/views.module';

export const APP_ROUTES: Routes = [
  {
    path: '', loadChildren: () => ViewsModule
  },
];
