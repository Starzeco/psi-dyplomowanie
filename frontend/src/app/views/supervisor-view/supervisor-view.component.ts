import { Component } from '@angular/core';
import { GraduationProcessServiceService } from 'src/app/shared/graduation-process-service.service';
import { LinkConfig } from 'src/app/shared/link-config.service';
import { User } from 'src/app/shared/model';

const user_: User = {
  userId: 1,
  type: "supervisor",
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
  selector: 'app-supervisor-view',
  templateUrl: './supervisor-view.component.html',
  styleUrls: ['./supervisor-view.component.scss']
})
export class SupervisorViewComponent {

  readonly linksConfig: LinkConfig[] = linksConfig_
  readonly user = user_

  constructor(
    readonly gpService: GraduationProcessServiceService
  ) {

  }

}
