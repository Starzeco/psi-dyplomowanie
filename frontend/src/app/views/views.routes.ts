import { Routes } from '@angular/router';
import { StudentPageModule } from './student-view/student-view.module';
import { SupervisorViewModule } from './supervisor-view/supervisor-view.module';
import { VerifierPageModule } from './verifier-view/verifier-view.module';

export const VIEWS_ROUTES: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: 'student'
  },
  {
    path: 'student', loadChildren: () => StudentPageModule
  },
  {
    path: 'supervisor', loadChildren: () => SupervisorViewModule
  },
  {
    path: 'verifier', loadChildren: () => VerifierPageModule
  }
];


