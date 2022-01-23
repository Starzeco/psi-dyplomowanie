import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { VIEWS_ROUTES } from './views.routes';
import { TestComponent } from './test/test.component';
import { StudentViewModule } from "./student-view/student-view.module";
import { SupervisorViewModule } from "./supervisor-view/supervisor-view.module";
import { VerifierViewModule } from "./verifier-view/verifier-view.module";
import { GraduationProcessesViewModule } from './graduation-processes-view/graduation-processes-view.module';


@NgModule({
  declarations: [
    TestComponent,

  ],
  exports: [
    TestComponent,
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
    GraduationProcessesViewModule,
  ]
})
export class ViewsModule { }
