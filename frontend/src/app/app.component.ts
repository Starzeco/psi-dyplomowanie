import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { LinkConfig, UserSessionConfig } from './components/sidenav/sidenav.component';
import { ButtonConfig } from './components/toolbar/toolbar.component';
import { Language } from './core/language';

// export type UserSessionConfig = {
//   userFullName: string,
//   initialSemesterName: string,
//   finalSemesterName: string,
//   facultyShortName: string,
//   degreeCourseName: string,
//   degreeName: string,
// }

const buttonsConfig_: ButtonConfig[] = [
  {
    textKey: 'b1',
    click: () => console.log("ts")
  },
  {
    textKey: 'b2',
    click: () => console.log('ts'),
    disabled: true,
  }
]

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
  degreeCourseNameKey: "applied_computer_science",
  degreeNameKey: "master"
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {

  readonly buttonsConfig: ButtonConfig[] = buttonsConfig_
  readonly linksConfig: LinkConfig[] = linksConfig_
  readonly userSessionConfig: UserSessionConfig = userSessionConfig_

  constructor(
    translateService: TranslateService
  ) {
    translateService.addLangs(Object.values(Language))
    translateService.setDefaultLang(Language.EN)
  }

}
