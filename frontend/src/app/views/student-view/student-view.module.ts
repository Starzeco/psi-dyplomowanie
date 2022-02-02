import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectComponent } from "./subject/subject.component";
import { StudentViewComponent } from "./student-view.component";
import { ComponentsModule } from "../../components/components.module";
import { MatCardModule } from "@angular/material/card";
import { MatTabsModule } from "@angular/material/tabs";
import { MatTableModule } from "@angular/material/table";
import { MatButtonModule } from "@angular/material/button";
import { RouterModule } from '@angular/router';
import { STUDENT_VIEW_ROUTES } from './student-view.routes';
import { SubjectTableComponent } from './subject/subject-table/subject-table.component';
import { GraduationProcessComponent } from './graduation-process/graduation-process.component';
import { CandidatureTableComponent } from './subject/candidature-table/candidature-table.component';
import { DetailsComponent } from './subject/details/details.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatRadioModule } from "@angular/material/radio";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { AppliedDetailsComponent } from './subject/applied-details/applied-details.component';
import { MatIconModule } from "@angular/material/icon";
import { CandidatureDetailsComponent } from './subject/candidature-details/candidature-details.component';
import { AvailableDetailsComponent } from './subject/available-details/available-details.component';

const MAT_MODULES = [
  MatCardModule,
  MatTabsModule,
  MatTableModule,
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatSelectModule,
  MatRadioModule,
  MatCheckboxModule,
  MatIconModule,
];

@NgModule({
  declarations: [
    StudentViewComponent,
    SubjectComponent,
    SubjectTableComponent,
    GraduationProcessComponent,
    CandidatureTableComponent,
    DetailsComponent,
    AppliedDetailsComponent,
    CandidatureDetailsComponent,
    AvailableDetailsComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forChild(STUDENT_VIEW_ROUTES),
    ...MAT_MODULES,
  ]
})
export class StudentViewModule { }
