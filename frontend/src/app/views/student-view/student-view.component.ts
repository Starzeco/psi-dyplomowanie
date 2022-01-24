import { Component } from '@angular/core';
import {User} from "../../shared/model";
import {LinkConfig} from "../../components/sidenav/sidenav.component";
import {GraduationProcessServiceService} from "../../shared/graduation-process-service.service";


const user_: User = {
  userId: 1,
  type: "student",
  userFullName: "Marcel Krakowiak",
}

const linksConfig_: LinkConfig[] = [
  {
    textKey: 'graduation_processes',
    href: '',
    iconName: 'view_module'
  },
  {
    textKey: 'subjects',
    href: '',
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
    readonly gpService: GraduationProcessServiceService
  ) { }
}
