import { Route } from "@angular/router";
import { SubjectComponent } from "./subject/subject.component";

export const STUDENT_VIEW_ROUTES: Route[] = [
  {
    path: 'subject', component: SubjectComponent
  }
]
