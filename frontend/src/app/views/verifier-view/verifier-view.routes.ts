import { Route } from "@angular/router";
import { GraduationProcessesComponent } from "./graduation-processes/graduation-processes.component";
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
        path: 'graduation_process/:graduation_process_id/subject', component: SubjectsComponent
      }
    ]
  },
]
