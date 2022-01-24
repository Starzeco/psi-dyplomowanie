import { Route } from "@angular/router";
import { SupervisorGraduationProcessesComponent } from "./supervisor-graduation-processes/supervisor-graduation-processes.component";
import { SupervisorSubjectsComponent } from "./supervisor-subjects/supervisor-subjects.component";
import { SupervisorViewComponent } from "./supervisor-view.component";

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
            }
        ]
    },

]
