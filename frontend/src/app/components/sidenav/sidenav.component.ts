import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { TranslateService } from '@ngx-translate/core';
import { Observable, Subscription } from 'rxjs';
import { Language } from 'src/app/core/language';
import { GraduationProcess, User } from 'src/app/shared/model';


export type LinkConfig = {
  textKey: string,
  href: string,
  iconName?: string,
}


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export default class SidenavComponent implements OnInit, OnDestroy {

  @Input() user!: User
  @Input() linksConfig!: LinkConfig[]
  @Input() graduationProcessObservable!: Observable<GraduationProcess>


  graduationProcess?: GraduationProcess
  subsription?: Subscription

  languages: string[] = Object.values(Language)
  currentLanguage: string

  constructor(private readonly translateService: TranslateService) {
    this.currentLanguage = translateService.getDefaultLang()
  }

  ngOnInit(): void {
    this.subsription = this.graduationProcessObservable.subscribe(gp => {
      this.graduationProcess = gp
    })
  }

  ngOnDestroy(): void {
    this.subsription && this.subsription.unsubscribe()
  }

  switchLanguage(event: MatSelectChange) {
    this.translateService.use(event.value as string)
  }


}
