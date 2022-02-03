import {Component, Input} from '@angular/core';
import { Status, Subject } from "../../../../shared/model";
import {Language} from "../../../../core/language";
import {
  SUBJECT_TYPE,
  SUBJECT_TYPE_POLISH,
  TITLE_TRANSLATION
} from "../../../../shared/dictionary";
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import { getSubjectStatusTranslation } from '../subject.common';

@Component({
  selector: 'app-subject-table',
  templateUrl: './subject-table.component.html',
  styleUrls: ['./subject-table.component.scss']
})
export class SubjectTableComponent {

  @Input()
  subjects: Subject[] = [];
  displayedColumns: string[] = ['topic', 'supervisor', 'type', 'status', 'details'];

  constructor(private readonly translateService: TranslateService,
              private readonly router: Router) {
  }

  getTopic(subject: Subject): string {
    if(this.translateService.currentLang == Language.EN) return subject.topicInEnglish;
    else return subject.topic;
  }

  getSupervisorFullName(subject: Subject) {
    const supervisor = subject.supervisor;
    return `${TITLE_TRANSLATION[supervisor.title]} ${supervisor.name} ${supervisor.surname}`;
  }

  getType(subject: Subject): string {
    if(this.translateService.currentLang == Language.EN) {
      if(subject.realiseresNumber > 1) return SUBJECT_TYPE.GROUP;
      else return SUBJECT_TYPE.INDIVIDUAL;
    } else {
      if(subject.realiseresNumber > 1) return SUBJECT_TYPE_POLISH.GROUP;
      else return SUBJECT_TYPE_POLISH.INDIVIDUAL;
    }
  }

  getStatus(subject: Subject): string {
    return getSubjectStatusTranslation(subject)
  }

  toDetails(subject: Subject) {
    if(subject.status != Status.VERIFIED) {
      void this.router.navigate(['student', 'graduation_process', '1', 'subject', 'applied', subject.subjectId]);
    } else {
      void this.router.navigate(['student', 'graduation_process', '1', 'subject', subject.subjectId]);
    }
  }
}
