import { Component, OnInit } from '@angular/core';
import { LinkConfig, LinkConfigService } from "../../shared/link-config.service";
import { UserSessionConfig, UserSessionConfigService } from "../../shared/session-config.service";


const linksConfig_: LinkConfig[] = [
  {
    textKey: 'marcel_krakowiak',
    href: 'https://www.facebook.com/marcel.krakowiak.963',
    iconName: 'account_circle'
  }
]

const userSessionConfig_: UserSessionConfig = {
  userFullName: "Marcel Krakowiak",
  initialSemesterName: "Z21/22",
  finalSemesterName: "L21/22",
  facultyShortName: "W4N",
  degreeCourseNameKey: "informatyka_stosowana",
  degreeNameKey: "master"
}


@Component({
  selector: 'app-student-view',
  templateUrl: './student-view.component.html',
  styleUrls: ['./student-view.component.scss']
})
export class StudentViewComponent implements OnInit {

  constructor(
    private readonly linkConfigService: LinkConfigService,
    private readonly userSessionConfigService: UserSessionConfigService
  ) { }

  ngOnInit(): void {
    this.linkConfigService.updateLinkConfig(linksConfig_);
    this.userSessionConfigService.updateSessionConfig(userSessionConfig_);
  }

}
