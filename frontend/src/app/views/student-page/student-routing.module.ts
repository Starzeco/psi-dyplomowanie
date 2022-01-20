import {Route} from "@angular/router";
import {GraduationProcessComponent} from "./graduation-process/graduation-process.component";
import {SubjectComponent} from "./subject/subject.component";

export const studentRoutes: Route[] = [
  {
    path: '', pathMatch: 'full', redirectTo: 'graduation-process'
  },
  {
    path: 'graduation-process', component: GraduationProcessComponent
  },
  {
    path: 'subject', component: SubjectComponent
  }
]
