import { Component, EventEmitter, Input, Output } from '@angular/core';
import { StaffMember, Subject, Verification } from "../../../../shared/model";
import { Language } from "../../../../core/language";
import { TranslateService } from "@ngx-translate/core";

@Component({
  selector: 'app-subjects-table',
  templateUrl: './subjects-table.component.html',
  styleUrls: ['./subjects-table.component.scss']
})
export class SubjectsTableComponent {

  @Input()
  verifications: Verification[] = [];

  @Output()
  verificationSelection = new EventEmitter<Verification>()

  readonly displayedColumns = ['topic', 'supervisor', 'type', 'decision', 'decision_date', 'details'];

  constructor(private readonly translateService: TranslateService) {
  }

  getSubjectTopic(subject: Subject): string {
    return this.translateService.currentLang === Language.EN ? subject.topicInEnglish : subject.topic;
  }

  getSupervisorFullName(supervisor: StaffMember) {
    const title = this.translateService.instant(supervisor.title) as string
    return `${title} ${supervisor.name} ${supervisor.surname}`;
  }

  getSubjectTypeTranslationKey(subject: Subject): string {
    return subject.realiseresNumber > 1 ? 'group' : 'individual'
  }

  getDecisionTranslationKey(verification: Verification): string {
    const verified = verification.verified
    if (verified === true) {
      return 'accepted'
    } else if (verified === false) {
      return 'rejected'
    } else {
      return ''
    }
  }


}
