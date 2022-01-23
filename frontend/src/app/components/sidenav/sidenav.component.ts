import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { TranslateService } from '@ngx-translate/core';
import { Language } from 'src/app/core/language';
import { LinkConfig, LinkConfigService } from "../../shared/link-config.service";
import { UserSessionConfigService, UserSessionConfig } from "../../shared/session-config.service";
import { Subscription } from "rxjs";


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit, OnDestroy {

  userSessionConfig!: UserSessionConfig
  linksConfig!: LinkConfig[]

  sessionConfigSubscription!: Subscription;
  linksConfigSubscription!: Subscription;

  languages: string[] = Object.values(Language)
  currentLanguage: string

  constructor(
    private readonly translateService: TranslateService,
    private readonly linkConfigService: LinkConfigService,
    private readonly userSessionConfigService: UserSessionConfigService,
  ) {
    this.currentLanguage = translateService.getDefaultLang()
  }

  switchLanguage(event: MatSelectChange) {
    this.translateService.use(event.value as string)
  }

  ngOnInit(): void {
    this.sessionConfigSubscription = this.userSessionConfigService.sessionConfigObservable.subscribe(
      sessionConfig => this.userSessionConfig = sessionConfig
    );
    this.linksConfigSubscription = this.linkConfigService.linkConfigObservable.subscribe(
      linksConfig => this.linksConfig = linksConfig
    );
  }

  ngOnDestroy(): void {
    this.sessionConfigSubscription.unsubscribe();
    this.linksConfigSubscription.unsubscribe();
  }
}
