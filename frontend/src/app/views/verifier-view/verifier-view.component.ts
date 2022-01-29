import { Component, OnInit } from '@angular/core';
import { LinkConfig } from 'src/app/components/sidenav/sidenav.component';
import { GraduationProcessService } from 'src/app/shared/graduation-process.service';
import { User } from 'src/app/shared/model';
import { UserService } from 'src/app/shared/user.service';

const user_: User = {
  userId: 2,
  type: "verifier",
  userFullName: "Jakub Garstka",
}

const linksConfig_: LinkConfig[] = [
  {
    textKey: 'graduation_processes',
    href: '/verifier',
    iconName: 'view_module'
  },
  {
    textKey: 'verifications',
    href: '',
    iconName: 'check'
  }
]

@Component({
  selector: 'app-verifier-view',
  templateUrl: './verifier-view.component.html',
  styleUrls: ['./verifier-view.component.scss']
})
export class VerifierViewComponent implements OnInit {

  readonly linksConfig: LinkConfig[] = linksConfig_
  readonly user = user_

  constructor(
    readonly gpService: GraduationProcessService,
    private readonly userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.setUser(this.user)
  }
}
