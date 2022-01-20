import { Component, OnInit } from '@angular/core';
import {LinkConfigService} from "../../shared/link-config.service";
import {SessionConfigService} from "../../shared/session-config.service";
import {LinkConfig, UserSessionConfig} from "../../components/sidenav/sidenav.component";


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
  selector: 'app-student-page',
  templateUrl: './student-page.component.html',
  styleUrls: ['./student-page.component.scss']
})
export class StudentPageComponent implements OnInit {

  constructor(private readonly linkConfigService: LinkConfigService,
              private readonly sessionConfigService: SessionConfigService,) { }

  ngOnInit(): void {
    this.linkConfigService.updateLinkConfig(linksConfig_);
    this.sessionConfigService.updateSessionConfig(userSessionConfig_);
  }

}
