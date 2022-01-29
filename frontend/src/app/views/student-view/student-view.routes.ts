import { Route } from "@angular/router";
import { SubjectComponent } from "./subject/subject.component";
import {StudentViewComponent} from "./student-view.component";
import {GraduationProcessComponent} from "./graduation-process/graduation-process.component";
import {DetailsComponent} from "./subject/details/details.component";
import { AppliedDetailsComponent } from "./subject/applied-details/applied-details.component";

export const STUDENT_VIEW_ROUTES: Route[] = [
  {
    path: '', pathMatch: 'full', redirectTo: 'student'
  },
  {
    path: 'student', component: StudentViewComponent,
    children: [
      {
        path: '', component: GraduationProcessComponent,
      },
      {
        path: 'graduation_process/:graduation_process_id/subject', component: SubjectComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/create', component: DetailsComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/:subject_id', component: DetailsComponent
      },
      {
        path: 'graduation_process/:graduation_process_id/subject/applied/:subject_id', component: AppliedDetailsComponent
      }
    ]
  }
]
