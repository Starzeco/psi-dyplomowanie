import { Route } from "@angular/router";
import { SupervisorGraduationProcessesComponent } from "./supervisor-graduation-processes/supervisor-graduation-processes.component";
import { SupervisorSubjectsComponent } from "./supervisor-subjects/supervisor-subjects.component";
import { SupervisorViewComponent } from "./supervisor-view.component";
import { CreateSubjectComponent } from "./supervisor-subjects/create-subject/create-subject.component";
import { UpdateSubjectComponent } from "./supervisor-subjects/update-subject/update-subject.component";
import { DraftSubjectDetailsComponent } from "./supervisor-subjects/draft-subject/draft-subject-details/draft-subject-details.component";
import { DraftSubjectUpdateComponent } from "./supervisor-subjects/draft-subject/draft-subject-update/draft-subject-update.component";

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
        path: 'graduation_process/:graduation_process_id/subject/:subject_id/update', component: UpdateSubjectComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/:subject_id/details', component: DraftSubjectDetailsComponent
      }, 
      {
        path: 'graduation_process/:graduation_process_id/subject/:subject_id/details/update', component: DraftSubjectUpdateComponent
      }
    ]
  },

]
