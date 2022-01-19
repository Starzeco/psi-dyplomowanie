import { Component, Input } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { TranslateService } from '@ngx-translate/core';
import { Language } from 'src/app/core/language';

export type LinkConfig = {
  textKey: string,
  href: string,
  iconName?: string,
}

export type UserSessionConfig = {
  userFullName: string,
  initialSemesterName: string,
  finalSemesterName: string,
  facultyShortName: string,
  degreeCourseNameKey: string,
  degreeNameKey: string,
}

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent {

  @Input() userSessionConfig!: UserSessionConfig
  @Input() linksConfig!: LinkConfig[]

  languages: string[] = Object.values(Language)
  currentLanguage: string

  constructor(
    private readonly translateService: TranslateService
  ) {
    this.currentLanguage = translateService.getDefaultLang()
  }

  switchLanguage(event: MatSelectChange) {
    this.translateService.use(event.value as string)
  }
}
