import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VerifierViewComponent } from "./verifier-view.component";
import { MatButtonModule } from "@angular/material/button";
import { ComponentsModule } from "../../components/components.module";


const MAT_MODULES = [
  MatButtonModule
];

@NgModule({
  declarations: [
    VerifierViewComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    ...MAT_MODULES,
  ]
})
export class VerifierViewModule { }
