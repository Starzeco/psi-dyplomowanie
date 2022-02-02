import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComponentsModule } from "../../components/components.module";
import { MatButtonModule } from "@angular/material/button";
import { SupervisorViewComponent } from "./supervisor-view.component";
import { RouterModule } from '@angular/router';
import { SUPERVISOR_VIEW_ROUTES } from './supervisor-view.routes';
import { SupervisorGraduationProcessesComponent } from './supervisor-graduation-processes/supervisor-graduation-processes.component';
import { SupervisorSubjectsComponent } from './supervisor-subjects/supervisor-subjects.component';
import { MatCardModule } from "@angular/material/card";
import { MatTabsModule } from "@angular/material/tabs";
import { MatTableModule } from "@angular/material/table";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatRadioModule } from "@angular/material/radio";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatIconModule } from "@angular/material/icon";
import { SubjectTableComponent } from './supervisor-subjects/subject-table/subject-table.component';
import { CreateSubjectComponent } from './supervisor-subjects/create-subject/create-subject.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { UpdateSubjectComponent } from './supervisor-subjects/update-subject/update-subject.component';
import { DraftSubjectUpdateComponent } from './supervisor-subjects/draft-subject/draft-subject-update/draft-subject-update.component';
import { DraftSubjectDetailsComponent } from './supervisor-subjects/draft-subject/draft-subject-details/draft-subject-details.component';


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
    SupervisorViewComponent,
    SupervisorGraduationProcessesComponent,
    SupervisorSubjectsComponent,
    SubjectTableComponent,
    CreateSubjectComponent,
    UpdateSubjectComponent,
    DraftSubjectUpdateComponent,
    DraftSubjectDetailsComponent
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forChild(SUPERVISOR_VIEW_ROUTES),
    ...MAT_MODULES,
  ]
})
export class SupervisorViewModule { }
