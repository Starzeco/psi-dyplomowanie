import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from "@angular/material/button";
import { ComponentsModule } from "../../components/components.module";
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatDividerModule } from '@angular/material/divider';
import { GraduationProcessesViewComponent } from './graduation-processes-view.component';
import { GraduationProcessCardComponent } from './graduation-process-card/graduation-process-card.component';

const MAT_MODULES = [
  MatCardModule,
  MatTabsModule,
  MatTableModule,
  MatButtonModule,
  MatDividerModule
];


@NgModule({
  declarations: [
    GraduationProcessesViewComponent,
    GraduationProcessCardComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    ...MAT_MODULES,
  ]
})
export class GraduationProcessesViewModule {}
