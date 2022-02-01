import { Component, Input } from '@angular/core';
import { Status, Subject } from "../../../../shared/model";
import { Router } from "@angular/router";
import { Language } from "../../../../core/language";
import { TranslateService } from "@ngx-translate/core";
import { SUBJECT_TYPE, SUBJECT_TYPE_POLISH } from "../../../../shared/dictionary";

@Component({
  selector: 'app-subject-table',
  templateUrl: './subject-table.component.html',
  styleUrls: ['./subject-table.component.scss']
})
export class SubjectTableComponent {

  @Input()
  subjects: Subject[] = [];
  displayedColumns: string[] = ['topic', 'author', 'type', 'status', 'details'];

  constructor(private readonly translateService: TranslateService,
              private readonly router: Router) { }


  getTopic(subject: Subject): string {
    if(this.translateService.currentLang == Language.EN) return subject.topicInEnglish;
    else return subject.topic;
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

  getAuthor(subject: Subject): string {
    if(subject.initiator != null) {
      return 'student_author';
    } else {
      return 'supervisor_author';
    }
  }

  getStatus(subject: Subject): string {
    const status = subject.status;
    switch (status) {
      case Status.DRAFT: {
        if(subject.initiator != null) {
          return 'draft_waiting_for_you';
        } else {
          return 'DRAFT';
        }
      }
      case Status.ACCEPTED_BY_SUPERVISOR: {
        return 'accepted_by_supervisor_supervisor_view';
      }
      case Status.ACCEPTED_BY_INITIATOR: {
        return 'accepted_by_initiator_supervisor_view';
      }
      case Status.REJECTED: {
        return 'REJECTED';
      }
      case Status.IN_VERIFICATION: {
        return 'IN_VERIFICATION';
      }
      case Status.IN_CORRECTION: {
        return 'IN_CORRECTION';
      }
      case Status.VERIFIED: {
        return 'verified_status';
      }
      case Status.RESERVED: {
        return 'reserved_status';
      }
      default: {
        return '';
      }
    }
  }

  toDetails(subject: Subject) {
    if([Status.IN_CORRECTION.toString(), Status.IN_VERIFICATION.toString(), Status.RESERVED.toString(), Status.VERIFIED.toString()].includes(subject.status)){
      void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject', 'update', subject.subjectId]);
    } else {
      void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject', 'details', subject.subjectId]);
    }
  }
}
