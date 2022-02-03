import { Route } from "@angular/router";
import { SupervisorGraduationProcessesComponent } from "./supervisor-graduation-processes/supervisor-graduation-processes.component";
import { SupervisorSubjectsComponent } from "./supervisor-subjects/supervisor-subjects.component";
import { SupervisorViewComponent } from "./supervisor-view.component";
import { CreateSubjectComponent } from "./supervisor-subjects/create-subject/create-subject.component";
import { DraftSubjectDetailsComponent } from "./supervisor-subjects/draft-subject/draft-subject-details/draft-subject-details.component";
import { DraftSubjectUpdateComponent } from "./supervisor-subjects/draft-subject/draft-subject-update/draft-subject-update.component";
import { ProcessedSubjectDetailsComponent } from "./supervisor-subjects/processed-subject/processed-subject-details/processed-subject-details.component";
import { ProcessedSubjectUpdateComponent } from "./supervisor-subjects/processed-subject/processed-subject-update/processed-subject-update.component";

export const SUPERVISOR_VIEW_ROUTES: Route[] = [
  {
    path: '', pathMatch: 'full', redirectTo: 'supervisor'
  },
  {
    path: 'supervisor', component: SupervisorViewComponent,
    children: [
      {
        path: '', component: SupervisorGraduationProcessesComponent,
      },
      {
        path: 'graduation_process/:graduation_process_id/subject', component: SupervisorSubjectsComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/create', component: CreateSubjectComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/processed/:subject_id/details', component: ProcessedSubjectDetailsComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/processed/:subject_id/update', component: ProcessedSubjectUpdateComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/draft/:subject_id/details', component: DraftSubjectDetailsComponent
      }, 
      {
        path: 'graduation_process/:graduation_process_id/subject/draft/:subject_id/update', component: DraftSubjectUpdateComponent
      }
    ]
  },

]
