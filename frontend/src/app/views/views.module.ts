import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { VIEWS_ROUTES } from './views.routes';
import { StudentViewModule } from "./student-view/student-view.module";
import { SupervisorViewModule } from "./supervisor-view/supervisor-view.module";
import { VerifierViewModule } from "./verifier-view/verifier-view.module";


@NgModule({
  declarations: [],
  exports: [
    StudentViewModule,
    SupervisorViewModule,
    VerifierViewModule,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(VIEWS_ROUTES),
    StudentViewModule,
    SupervisorViewModule,
    VerifierViewModule,
  ]
})
export class ViewsModule { }
