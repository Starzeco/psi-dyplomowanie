import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from "@angular/router";
import { SidenavComponent } from './sidenav/sidenav.component';


const MAT_MODULES = [
  MatSidenavModule
];

@NgModule({
  declarations: [
    SidenavComponent,
  ],
  exports: [
    SidenavComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    ...MAT_MODULES,
  ]
})
export class ComponentsModule {
}
