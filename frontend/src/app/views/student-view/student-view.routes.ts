import { Route } from "@angular/router";
import { SubjectComponent } from "./subject/subject.component";
import {StudentViewComponent} from "./student-view.component";
import {GraduationProcessComponent} from "./graduation-process/graduation-process.component";

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
      }
    ]
  }
]
