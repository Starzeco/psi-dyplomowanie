import { Component } from '@angular/core';
import {User} from "../../shared/model";
import {LinkConfig} from "../../components/sidenav/sidenav.component";
import {GraduationProcessService} from "../../shared/graduation-process.service";


const user_: User = {
  userId: 1,
  type: "student",
  userFullName: "Marcel Krakowiak",
}

const linksConfig_: LinkConfig[] = [
  {
    textKey: 'graduation_processes',
    href: '/student',
    iconName: 'view_module'
  },
  {
    textKey: 'subjects',
    href: '/student/graduation_process/1/subject',
    iconName: 'note'
  }
]


@Component({
  selector: 'app-student-view',
  templateUrl: './student-view.component.html',
  styleUrls: ['./student-view.component.scss']
})
export class StudentViewComponent {

  readonly linksConfig: LinkConfig[] = linksConfig_
  readonly user = user_

  constructor(
    readonly gpService: GraduationProcessService
  ) { }
}
