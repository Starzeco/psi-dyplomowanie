import { Routes } from '@angular/router';
import { StudentViewModule } from './student-view/student-view.module';
import { SupervisorViewModule } from './supervisor-view/supervisor-view.module';
import { VerifierViewModule } from './verifier-view/verifier-view.module';

export const VIEWS_ROUTES: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: "student"
  },
  {
    path: 'student', loadChildren: () => StudentViewModule
  },
  {
    path: 'supervisor', loadChildren: () => SupervisorViewModule
  },
  {
    path: 'verifier', loadChildren: () => VerifierViewModule
  }
];


