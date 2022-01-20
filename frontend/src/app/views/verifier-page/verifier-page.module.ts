import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {VerifierPageComponent} from "./verifier-page.component";
import {MatButtonModule} from "@angular/material/button";
import {AppRoutingModule} from "../../app-routing.module";
import {ComponentsModule} from "../../components/components.module";


const MAT_MODULES = [
  MatButtonModule
];


@NgModule({
  declarations: [
    VerifierPageComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    ComponentsModule,
    ...MAT_MODULES,
  ]
})
export class VerifierPageModule { }
