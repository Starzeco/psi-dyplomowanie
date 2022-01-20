import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';
import {StudentPageComponent} from "./views/student-page/student-page.component";
import {SupervisorPageComponent} from "./views/supervisor-page/supervisor-page.component";
import {VerifierPageComponent} from "./views/verifier-page/verifier-page.component";
import {studentRoutes} from "./views/student-page/student-routing.module";
import {supervisorRoutes} from "./views/supervisor-page/supervisor-routing.module";
import {verifierRoutes} from "./views/verifier-page/verifier-routing.module";

const routes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: 'student'
  },
  {
    path: 'student', component: StudentPageComponent, children: studentRoutes
  },
  {
    path: 'supervisor', component: SupervisorPageComponent, children: supervisorRoutes
  },
  {
    path: 'verifier', component: VerifierPageComponent, children: verifierRoutes
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
