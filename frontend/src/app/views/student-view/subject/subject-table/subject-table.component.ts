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
    const status = subject.status;
    if(status == Status.DRAFT) {
      if(subject.realiseresNumber == 1) {
        return 'draft_waiting';
      } else if(subject.realiseresNumber > 1) {
        const acceptances = subject.propositionAcceptances;
        const anyRejection = acceptances.find(a => a.accepted === false);
        if(anyRejection) {
          return 'rejected_status';
        }
        const allAccepted = acceptances.every(a => a.accepted);
        if(allAccepted) {
          return 'draft_waiting';
        } else if(subject.initiator?.studentId == 1) {
          return 'draft_waiting_co-realisers';
        } else {
          return 'draft_waiting_for_you';
        }
      } else return 'wtf';
    } else if(status == Status.ACCEPTED_BY_SUPERVISOR) {
      return 'accepted_by_supervisor_status';
    } else if(status == Status.ACCEPTED_BY_INITIATOR) {
      return 'accepted_by_initiator_status';
    } else if(status == Status.IN_VERIFICATION) {
      return 'in_verification_status';
    } else if(status == Status.IN_CORRECTION) {
      return 'in_correction_status';
    } else if(status == Status.VERIFIED) {
      return 'verified_status';
    } else if(status == Status.REJECTED) {
      return 'rejected_status';
    } else if(status == Status.RESERVED) {
      return 'reserved_status';
    } else return 'Unknown';
  }

  toDetails(subject: Subject) {
    if(subject.status != Status.VERIFIED) {
      void this.router.navigate(['student', 'graduation_process', '1', 'subject', 'applied', subject.subjectId]);
    } else {
      void this.router.navigate(['student', 'graduation_process', '1', 'subject', subject.subjectId]);
    }
  }
}
