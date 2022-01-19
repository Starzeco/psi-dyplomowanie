import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RouterModule } from "@angular/router";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidenavComponent } from './sidenav/sidenav.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import {
  LoadingStateComponent,
} from './loading-state/loading-state.component';
import { ErrorStateComponent } from './error-state/error-state.component';
import { EmptyStateComponent } from './empty-state/empty-state.component';

const MAT_MODULES = [
  MatSidenavModule,
  MatToolbarModule,
  MatButtonModule,
  MatIconModule,
  MatDividerModule,
  MatFormFieldModule,
  MatSelectModule,
  MatProgressSpinnerModule,
];

@NgModule({
  declarations: [
    SidenavComponent,
    ToolbarComponent,
    LoadingStateComponent,
    ErrorStateComponent,
    EmptyStateComponent,
  ],
  exports: [
    SidenavComponent,
    ToolbarComponent,
    TranslateModule,
    LoadingStateComponent,
    ErrorStateComponent,
    EmptyStateComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    BrowserAnimationsModule,
    TranslateModule,
    ...MAT_MODULES,
  ]
})

export class ComponentsModule {
}
