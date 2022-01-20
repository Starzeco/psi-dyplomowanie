import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestComponent } from './test/test.component';


@NgModule({
  declarations: [
    TestComponent,
  ],
  exports: [
    TestComponent,
  ],
  imports: [
    CommonModule,
  ]
})
export class ViewsModule { }
