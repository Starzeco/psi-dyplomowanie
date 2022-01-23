import { Route } from "@angular/router";
import { SubjectComponent } from "./subject/subject.component";

export const STUDENT_VIEW_ROUTES: Route[] = [
  {
    path: '', pathMatch: 'full', redirectTo: 'subject'
  },
  {
    path: 'subject', component: SubjectComponent
  }
]
