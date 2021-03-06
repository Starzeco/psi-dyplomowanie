import {Component, Input} from '@angular/core';
import {CandidaturePartialInfo} from "../../../../shared/model";
import {TranslateService} from "@ngx-translate/core";
import {Language} from "../../../../core/language";
import {
  SUBJECT_TYPE,
  SUBJECT_TYPE_POLISH
} from "../../../../shared/dictionary";
import { Router } from "@angular/router";
import { getCandidaturePartialInfoStatusTranslation } from '../subject.common';

@Component({
  selector: 'app-candidature-table',
  templateUrl: './candidature-table.component.html',
  styleUrls: ['./candidature-table.component.scss']
})
export class CandidatureTableComponent {

  @Input()
  candidatures: CandidaturePartialInfo[] = [];
  displayedColumns: string[] = ['topic', 'supervisor', 'type', 'status', 'details'];

  constructor(private readonly translateService: TranslateService,
              private readonly router: Router) { }

  getTopic(candidature: CandidaturePartialInfo): string {
    if(this.translateService.currentLang == Language.EN) return candidature.subjectTopicEnglish;
    else return candidature.subjectTopic;
  }

  getSupervisorFullName(candidature: CandidaturePartialInfo) {
    return candidature.supervisorName;
  }

  getType(candidature: CandidaturePartialInfo): string {
    if(this.translateService.currentLang == Language.EN) return SUBJECT_TYPE[candidature.type];
    else return SUBJECT_TYPE_POLISH[candidature.type];
  }

  getStatus(candidature: CandidaturePartialInfo): string {
    return getCandidaturePartialInfoStatusTranslation(candidature);
  }

  toDetails(candidature: CandidaturePartialInfo) {
    void this.router.navigate(['student', 'graduation_process', '1', 'subject', 'candidature', candidature.candidatureId]);
  }
  
}
