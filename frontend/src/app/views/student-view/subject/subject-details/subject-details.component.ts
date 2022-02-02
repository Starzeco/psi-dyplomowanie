/* eslint-disable @typescript-eslint/unbound-method */
import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StaffMember, Verification, Subject, Student, Status, PropositionAcceptance } from 'src/app/shared/model';

@Component({
  selector: 'app-subject-details',
  templateUrl: './subject-details.component.html',
  styleUrls: ['./subject-details.component.scss']
})
export class SubjectDetailsComponent {

  @Input() subject!: Subject


  constructor(
    private readonly translateService: TranslateService,
  ) { }

  getMainRealizerLabel(subject?: Subject): string {
    if (subject)
      return this.checkIfSubjectIsNotReservedButHasInitiator(subject)
        ? 'applicant'
        : 'main-realiser'
    else return '-'
  }

  getMainRealiserIndex(subject?: Subject): string {
    if (subject) {
      if (this.checkIfSubjectIsNotReservedButHasInitiator(subject)) {
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        return subject.initiator!.index
      } else {
        return subject.realiser[0].index
      }
    }
    return '-'
  }

  getCoRealisersIndexes(subject?: Subject): string[] {
    if (subject) {
      if (this.checkIfSubjectIsNotReservedButHasInitiator(subject)) {
        return subject.propositionAcceptances.map(pa => pa.student.index)
      } else {
        return subject.realiser.map(r => r.index)
      }
    }
    return []
  }

  private checkIfSubjectIsNotReservedButHasInitiator(subject: Subject) {
    return subject.status != Status.RESERVED || subject.initiator;
  }

  getCoRealisersIndexesIcon(index: string, subject?: Subject): string {
    if (subject) {
      if (this.checkIfSubjectIsNotReservedButHasInitiator(subject)) {
        const pa = subject.propositionAcceptances.find(pa => pa.student.index === index)
        return pa
          ? this.getIconForAcceptanceAccepted(pa)
          : ''
      } else return 'done'
    }
    return ''
  }

  getIconForAcceptanceAccepted(pa: PropositionAcceptance): string {
    if (pa.accepted == true) return 'done'
    else if (pa.accepted == false) return 'close';
    else return 'help_outline';
  }


  getSupervisorFullName(supervisor?: StaffMember) {
    if (supervisor) {
      const title = this.translateService.instant(supervisor.title) as string
      return `${title} ${supervisor.name} ${supervisor.surname}`;
    } else return ""
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