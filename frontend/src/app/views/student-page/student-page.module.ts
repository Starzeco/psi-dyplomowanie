import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SubjectComponent} from "./subject/subject.component";
import {GraduationProcessComponent} from "./graduation-process/graduation-process.component";
import {StudentPageComponent} from "./student-page.component";
import {AppRoutingModule} from "../../app-routing.module";
import {ComponentsModule} from "../../components/components.module";
import {MatCardModule} from "@angular/material/card";
import {MatTabsModule} from "@angular/material/tabs";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";


const MAT_MODULES = [
  MatCardModule,
  MatTabsModule,
  MatTableModule,
  MatButtonModule,
];


@NgModule({
  declarations: [
    StudentPageComponent,
    SubjectComponent,
    GraduationProcessComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    ComponentsModule,
    ...MAT_MODULES,
  ]
})
export class StudentPageModule { }
