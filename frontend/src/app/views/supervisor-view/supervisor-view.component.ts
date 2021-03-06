import { Component } from '@angular/core';
import { GraduationProcessService } from 'src/app/shared/graduation-process.service';
import { User } from 'src/app/shared/model';
import {LinkConfig} from "../../components/sidenav/sidenav.component";

const user_: User = {
  userId: 1,
  type: "supervisor",
  userFullName: "Marcin Nowak",
}

const linksConfig_: LinkConfig[] = [
  {
    textKey: 'graduation_processes',
    href: '/supervisor',
    iconName: 'view_module'
  },
  {
    textKey: 'subjects',
    href: '/supervisor/graduation_process/1/subject',
    iconName: 'note'
  }
]

@Component({
  selector: 'app-supervisor-view',
  templateUrl: './supervisor-view.component.html',
  styleUrls: ['./supervisor-view.component.scss']
})
export class SupervisorViewComponent {

  readonly linksConfig: LinkConfig[] = linksConfig_
  readonly user = user_

  constructor(
    readonly gpService: GraduationProcessService
  ) {

  }

}
