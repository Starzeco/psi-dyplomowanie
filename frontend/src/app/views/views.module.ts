import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestComponent } from './test/test.component';
import { StudentPageModule } from "./student-view/student-view.module";
import { SupervisorViewModule } from "./supervisor-view/supervisor-view.module";
import { VerifierPageModule } from "./verifier-view/verifier-view.module";
import { RouterModule } from '@angular/router';
import { VIEWS_ROUTES } from './views.routes';

@NgModule({
  declarations: [
    TestComponent,
  ],
  exports: [
    TestComponent,
    StudentPageModule,
    SupervisorViewModule,
    VerifierPageModule,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(VIEWS_ROUTES),
    StudentPageModule,
    SupervisorViewModule,
    VerifierPageModule,

  ]
})
export class ViewsModule { }
