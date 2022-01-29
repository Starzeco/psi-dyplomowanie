import { Route } from "@angular/router";
import { GraduationProcessesComponent } from "./graduation-processes/graduation-processes.component";
import { SubjectDetailsComponent } from "./subjects/subject-details/subject-details.component";
import { SubjectsComponent } from "./subjects/subjects.component";
import { VerifierViewComponent } from "./verifier-view.component";

export const VERIFIER_VIEW_ROUTES: Route[] = [
  {
    path: '', pathMatch: 'full', redirectTo: 'verifier'
  },
  {
    path: 'verifier', component: VerifierViewComponent,
    children: [
      {
        path: '', component: GraduationProcessesComponent,
      },
      {
        path: ':verifier_id/graduation_process/:graduation_process_id/verifications', component: SubjectsComponent
      },
      {
        path: ':verifier_id/graduation_process/:graduation_process_id/verifications/:verification_id', component: SubjectDetailsComponent,
      }
    ]
  },
]
