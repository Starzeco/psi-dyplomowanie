import {Component} from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Language } from './core/language';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {

  constructor(
    private readonly translateService: TranslateService,
  ) {
    translateService.addLangs(Object.values(Language))
    translateService.setDefaultLang(Language.EN)
  }
}
