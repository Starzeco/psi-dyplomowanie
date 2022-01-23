import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComponentsModule } from "../../components/components.module";
import { MatButtonModule } from "@angular/material/button";
import { SupervisorViewComponent } from "./supervisor-view.component";
import { RouterModule } from '@angular/router';
import { SUPERVISOR_VIEW_ROUTES } from './supervisor-view.routes';


const MAT_MODULES = [
  MatButtonModule
];


@NgModule({
  declarations: [
    SupervisorViewComponent
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    RouterModule.forChild(SUPERVISOR_VIEW_ROUTES),
    ...MAT_MODULES,
  ]
})
export class SupervisorViewModule { }
