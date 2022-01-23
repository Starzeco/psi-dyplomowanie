import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectComponent } from "./subject/subject.component";
import { GraduationProcessComponent } from "./graduation-process/graduation-process.component";
import { StudentViewComponent } from "./student-view.component";
import { ComponentsModule } from "../../components/components.module";
import { MatCardModule } from "@angular/material/card";
import { MatTabsModule } from "@angular/material/tabs";
import { MatTableModule } from "@angular/material/table";
import { MatButtonModule } from "@angular/material/button";
import { RouterModule } from '@angular/router';
import { STUDENT_VIEW_ROUTES } from './student-view.routes';

const MAT_MODULES = [
  MatCardModule,
  MatTabsModule,
  MatTableModule,
  MatButtonModule,
];

@NgModule({
  declarations: [
    StudentViewComponent,
    SubjectComponent,
    GraduationProcessComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    RouterModule.forChild(STUDENT_VIEW_ROUTES),
    ...MAT_MODULES,
  ]
})
export class StudentViewModule { }
