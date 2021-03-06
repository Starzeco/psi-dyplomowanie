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
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from "@angular/router";
import SidenavComponent from './sidenav/sidenav.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import {
  LoadingStateComponent,
} from './loading-state/loading-state.component';
import { ErrorStateComponent } from './error-state/error-state.component';
import { EmptyStateComponent } from './empty-state/empty-state.component';
import { FiltersComponent } from './filters/filters.component';
import { TextSearchFilterComponent } from './filters/text-search-filter/text-search-filter.component';
import { SelectFilterComponent } from './filters/select-filter/select-filter.component';
import { GraduationProcessesGridComponent } from './graduation-processes-grid/graduation-processes-grid.component';
import { GraduationProcessCardComponent } from './graduation-processes-grid/graduation-process-card/graduation-process-card.component';
import { StateComponent } from './state/state.component';
import { ToolbarService } from './toolbar/toolbar.service';
import { TopicObjectiveComponent } from './details/topic-objective/topic-objective.component';
import { StatusComponent } from './details/status/status.component';
import { SupervisorComponent } from './details/supervisor/supervisor.component';
import { CoRealisersComponent } from './details/co-realisers/co-realisers.component';
import { RealizersNumberComponent } from './details/realizers-number/realizers-number.component';
import { VerificationsComponent } from './details/verifications/verifications.component';

const MAT_MODULES = [
  MatSidenavModule,
  MatToolbarModule,
  MatButtonModule,
  MatIconModule,
  MatDividerModule,
  MatFormFieldModule,
  MatInputModule,
  MatSelectModule,
  MatProgressSpinnerModule,
  MatCardModule,
];

@NgModule({
  declarations: [
    SidenavComponent,
    ToolbarComponent,
    LoadingStateComponent,
    ErrorStateComponent,
    EmptyStateComponent,
    FiltersComponent,
    TextSearchFilterComponent,
    SelectFilterComponent,
    GraduationProcessesGridComponent,
    GraduationProcessCardComponent,
    StateComponent,
    TopicObjectiveComponent,
    StatusComponent,
    SupervisorComponent,
    CoRealisersComponent,
    RealizersNumberComponent,
    VerificationsComponent,
  ],
  exports: [
    SidenavComponent,
    ToolbarComponent,
    TranslateModule,
    LoadingStateComponent,
    ErrorStateComponent,
    EmptyStateComponent,
    FiltersComponent,
    TextSearchFilterComponent,
    GraduationProcessesGridComponent,
    GraduationProcessCardComponent,
    StateComponent,
    TopicObjectiveComponent,
    StatusComponent,
    SupervisorComponent,
    CoRealisersComponent,
    RealizersNumberComponent,
    VerificationsComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    TranslateModule,
    ...MAT_MODULES,
  ],
  providers: [
    ToolbarService
  ]
})

export class ComponentsModule {
}
