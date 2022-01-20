import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AppRoutingModule} from "../../app-routing.module";
import {ComponentsModule} from "../../components/components.module";
import {MatButtonModule} from "@angular/material/button";
import {SupervisorPageComponent} from "./supervisor-page.component";


const MAT_MODULES = [
  MatButtonModule
];


@NgModule({
  declarations: [
    SupervisorPageComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    ComponentsModule,
    ...MAT_MODULES,
  ]
})
export class SupervisorPageModule { }
