import { Component, OnInit } from '@angular/core';
import { PropositionAcceptance, Status, Subject, Verification } from "../../../../shared/model";
import { ActivatedRoute, Router } from "@angular/router";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { RestService } from "../../../../shared/rest.service";
import { Content, objectvieContent, topicContent } from 'src/app/components/details/topic-objective/topic-objective.component';
import { MainRealizer, Realizer } from 'src/app/components/details/co-realisers/co-realisers.component';
import { getSubjectStatusTranslation } from '../subject.common';

const toolbarEmpty_: ToolbarConfig = {
  titleKey: 'details_subject_header',
  iconName: 'note',
  buttonsConfig: []
}

@Component({
  selector: 'app-applied-details',
  templateUrl: './applied-details.component.html',
  styleUrls: ['./applied-details.component.scss']
})
export class AppliedDetailsComponent implements OnInit {
  error = false;
  loading = true;
  subject!: Subject;

  private toolbarDecision: ToolbarConfig = {
    titleKey: 'accept_proposition_hedaer',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'accept',
        click: () => this.updateProposition(true)
      },
      {
        textKey: 'decline',
        click: () => this.updateProposition(false)
      }
    ]
  }

  private toolbalAcceptInitiator: ToolbarConfig = {
    titleKey: 'accept_by_initiator_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'accept',
        click: () => this.acceptInitiator(true)
      },
      {
        textKey: 'decline',
        click: () => this.acceptInitiator(false)
      }
    ]
  }

  constructor(private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly router: Router,
    private readonly restService: RestService
  ) { }

  ngOnInit(): void {
    this.fetchSubject();
  }

  private fetchSubject() {
    const subjectId = this.route.snapshot.paramMap.get('subject_id');
    if (subjectId == null) {
      this.loading = false;
      this.error = true;
    } else {
      this.restService.fetchSubjectById(+subjectId).subscribe({
        next: result => {
          this.chooseToolbar(result);
          this.subject = result;
          this.loading = false;
          this.error = false
        },
        error: () => {
          this.loading = false;
          this.error = true
        }
      });
    }
  }

  private chooseToolbar(subject: Subject) {
    if (subject.status == Status.DRAFT) {
      if (subject.initiator != null) { // W DRAFT i w tym komponencie nigdy inicjator nie jest nullem
        const acceptances = subject.propositionAcceptances;
        if (acceptances.length == 0 || subject.initiator.index == '242422') {
          this.toolbarService.updateToolbarConfig(toolbarEmpty_);
          return;
        }
        const acceptance = acceptances.find(a => a.student.index == '242422');
        if (acceptance != null && acceptance.accepted !== null) {
          this.toolbarService.updateToolbarConfig(toolbarEmpty_);
        } else {
          this.toolbarService.updateToolbarConfig(this.toolbarDecision);
        }
      }
    } else if (subject.status == Status.ACCEPTED_BY_SUPERVISOR  && subject.initiator?.index == '242422') {
      this.toolbarService.updateToolbarConfig(this.toolbalAcceptInitiator);
    } else {
      this.toolbarService.updateToolbarConfig(toolbarEmpty_);
    }
  }

  private updateProposition(decision: boolean) {
    if (this.subject) {
      this.loading = true;
      this.error = false;
      const proposition = this.subject.propositionAcceptances.find(a => a.student.index == '242422');
      if (proposition != null) {
        const propositionId = proposition.propositionAcceptanceId;
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        this.restService.updateProposition(decision, propositionId!).subscribe({
          next: () => {
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
            this.loading = false;
            this.error = false;
          },
          error: () => {
            this.loading = false;
            this.error = true;
          }
        });
      }
    }
  }

  private acceptInitiator(decision: boolean) {
    if (this.subject) {
      this.loading = true;
      this.error = false;
      if (decision) {
        this.restService.acceptInitiator(this.subject.subjectId).subscribe({
          next: () => {
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
            this.loading = false;
            this.error = false;
          },
          error: () => {
            this.loading = false;
            this.error = true;
          }
        });
      } else {
        this.restService.reject(this.subject.subjectId).subscribe({
          next: () => {
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
            this.loading = false;
            this.error = false;
          },
          error: () => {
            this.loading = false;
            this.error = true;
          }
        });
      }
    }
  }
  
  prepareMainRealizer(): MainRealizer {
    if (this.checkIfSubjectIsNotReservedButHasInitiator(this.subject))
      return {
        iconName: 'done',
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        name: this.subject.initiator!.index,
        labelKey: 'applicant',
      }
    else
      return {
        iconName: 'done',
        name: this.subject.realiser[0].index,
        labelKey: 'main-realiser',
      }
  }

  prepareCoorealizers(): Realizer[] {
    if (this.checkIfSubjectIsNotReservedButHasInitiator(this.subject)) {
      return this.subject.propositionAcceptances.map(pa => ({
        name: pa.student.index,
        iconName: this.getIconForAcceptanceAccepted(pa)
      }))
    } else {
      return this.subject.realiser.map(r => ({
        name: r.index,
        iconName: 'done'
      }))
    }
  }

  private checkIfSubjectIsNotReservedButHasInitiator(subject: Subject) {
    return subject.status != Status.RESERVED || subject.initiator;
  }

  prepareTopic(): Content {
    return topicContent(this.subject)
  }

  prepareObjective(): Content {
    return objectvieContent(this.subject)
  }


  getIconForAcceptanceAccepted(pa: PropositionAcceptance): string {
    if (pa.accepted == true) return 'done'
    else if (pa.accepted == false) return 'close';
    else return 'help_outline';
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

  getStatus(): string {
    return getSubjectStatusTranslation(this.subject)
  }
}
