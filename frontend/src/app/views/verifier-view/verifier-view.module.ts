import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VerifierViewComponent } from "./verifier-view.component";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { ComponentsModule } from "../../components/components.module";
import { GraduationProcessesComponent } from './graduation-processes/graduation-processes.component';
import { SubjectsComponent } from './subjects/subjects.component';
import { InVerificationComponent } from './subjects/in-verification/in-verification.component';
import { AcceptedComponent } from './subjects/accepted/accepted.component';
import { RejectedComponent } from './subjects/rejected/rejected.component';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { VERIFIER_VIEW_ROUTES } from './verifier-view.routes';
import { DecisionDialogComponent } from './decision-dialog/decision-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SubjectsTableComponent } from './subjects/subjects-table/subjects-table.component';


const MAT_MODULES = [
  MatButtonModule,
  MatCardModule,
  MatTabsModule,
  MatInputModule,
  MatDialogModule,
  MatTableModule,
];

@NgModule({
  declarations: [
    VerifierViewComponent,
    GraduationProcessesComponent,
    SubjectsComponent,
    InVerificationComponent,
    AcceptedComponent,
    RejectedComponent,
    DecisionDialogComponent,
    SubjectsTableComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    ReactiveFormsModule,
    RouterModule.forChild(VERIFIER_VIEW_ROUTES),
    RouterModule,
    ...MAT_MODULES,
  ]
})
export class VerifierViewModule { }
