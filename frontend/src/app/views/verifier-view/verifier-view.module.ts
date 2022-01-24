import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VerifierViewComponent } from "./verifier-view.component";
import { MatButtonModule } from "@angular/material/button";
import { ComponentsModule } from "../../components/components.module";
import { GraduationProcessesComponent } from './graduation-processes/graduation-processes.component';
import { SubjectsComponent } from './subjects/subjects.component';
import { InVerificationComponent } from './subjects/in-verification/in-verification.component';
import { AcceptedComponent } from './subjects/accepted/accepted.component';
import { RejectedComponent } from './subjects/rejected/rejected.component';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { RouterModule } from '@angular/router';
import { VERIFIER_VIEW_ROUTES } from './verifier-view.routes';


const MAT_MODULES = [
  MatButtonModule,
  MatCardModule,
  MatTabsModule
];

@NgModule({
  declarations: [
    VerifierViewComponent,
    GraduationProcessesComponent,
    SubjectsComponent,
    InVerificationComponent,
    AcceptedComponent,
    RejectedComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    RouterModule.forChild(VERIFIER_VIEW_ROUTES),
    RouterModule,
    ...MAT_MODULES,
  ]
})
export class VerifierViewModule { }
