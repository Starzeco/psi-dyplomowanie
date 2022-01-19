import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { TestComponent } from './test/test.component';


const MAT_MODULES = [
  MatCardModule,
  MatTabsModule,
  MatTableModule
];

@NgModule({
  declarations: [
    TestComponent
  ],
  exports: [
    TestComponent,
  ],
  imports: [
    CommonModule,
    ...MAT_MODULES,
  ]
})
export class ViewsModule { }
