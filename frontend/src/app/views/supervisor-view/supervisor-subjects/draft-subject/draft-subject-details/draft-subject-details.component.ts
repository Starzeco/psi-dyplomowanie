import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MainRealizer, Realizer } from 'src/app/components/details/co-realisers/co-realisers.component';
import { Content, topicContent, objectvieContent } from 'src/app/components/details/topic-objective/topic-objective.component';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { PropositionAcceptance, Status, Subject } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';
import { getSubjectStatusTranslation } from 'src/app/views/student-view/subject/subject.common';

const toolbarEmpty_: ToolbarConfig = {
  titleKey: 'draft_subject_details',
  iconName: 'note',
  buttonsConfig: []
}

@Component({
  selector: 'app-draft-subject-details',
  templateUrl: './draft-subject-details.component.html',
  styleUrls: ['./draft-subject-details.component.scss']
})
export class DraftSubjectDetailsComponent implements OnInit {

  private toolbarDraftConfig: ToolbarConfig = {
    titleKey: 'details_subject_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'update',
        click: () => this.updateSubject()
      },
      {
        textKey: 'to_verification',
        click: () => this.sendToVerification()
      }
    ]
  }

  private toolbarToAcceptConfig: ToolbarConfig = {
    titleKey: 'details_subject_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'accept',
        click: () => this.accept()
      },
      {
        textKey: 'decline',
        click: () => this.decline()
      }
    ]
  }

  private toolbarAcceptedByInitiatorConfig: ToolbarConfig = {
    titleKey: 'details_subject_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'to_verification',
        click: () => this.sendToVerification()
      }
    ]
  }

  loading = true
  error = false

  subject!: Subject

  constructor(private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly router: Router,
    private readonly restService: RestService) { }

  ngOnInit(): void {
    this.fetchSubjectById();
  }

  private fetchSubjectById() {
    const subjectId = this.route.snapshot.paramMap.get('subject_id');
    if (subjectId == null) {
      this.loading = false;
      this.error = true;
    } else {
      this.loading = true;
      this.error = false;
      this.restService.fetchSubjectById(+subjectId).subscribe({
        next: sub => {
          this.subject = sub;
          this.chooseToolbar();
          this.loading = false;
          this.error = false;
        }, error: () => {
          this.loading = false;
          this.error = true;
        }
      });
    }
  }

  private chooseToolbar() {
    // Jak oczekuje na moją (promotora) decyzje to daj akceptuj i odrzuc
    // Jak jest po prostu draft to daj wyślij do weryfikacji  (tutaj to chyba tez przydatna byłaby edycja)
    // jak jest zaakceptowane przez inicjatora to tez daj wyślij do weryfikacji
    // Jak odrzuca lub oczekuje na decyzje inicjatora to empty
    if (this.subject.status == Status.REJECTED || this.subject.status == Status.ACCEPTED_BY_SUPERVISOR) {
      this.toolbarService.updateToolbarConfig(toolbarEmpty_);
    } else if (this.subject.status == Status.ACCEPTED_BY_INITIATOR) {
      this.toolbarService.updateToolbarConfig(this.toolbarAcceptedByInitiatorConfig);
    } else if (this.subject.status == Status.DRAFT && this.subject.initiator != null) {
      this.toolbarService.updateToolbarConfig(this.toolbarToAcceptConfig);
    } else if (this.subject.status == Status.DRAFT) {
      this.toolbarService.updateToolbarConfig(this.toolbarDraftConfig);
    }
  }

  private sendToVerification() {
    this.loading = true;
    this.error = false;
    this.restService.sendToVerification(this.subject.subjectId).subscribe({
      next: () => {
        this.loading = false;
        this.error = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  private accept() {
    this.loading = true;
    this.error = false;
    this.restService.acceptAsSupervisor(this.subject.subjectId).subscribe({
      next: () => {
        this.loading = false;
        this.error = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  private decline() {
    this.loading = true;
    this.error = false;
    this.restService.rejectAsSupervisor(this.subject.subjectId).subscribe({
      next: () => {
        this.loading = false;
        this.error = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  private updateSubject() {
    void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject', 'draft', this.subject.subjectId, 'update']);
  }

  prepareTopic(): Content {
    return topicContent(this.subject)
  }

  prepareObjective(): Content {
    return objectvieContent(this.subject)
  }

  getStatus(): string {
    return getSubjectStatusTranslation(this.subject)
  }

  prepareMainRealizer(): MainRealizer {
    return {
      iconName: 'done',
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
      name: this.subject.initiator!.index,
      labelKey: 'applicant',
    }
  }

  prepareCoorealizers(): Realizer[] {
    return this.subject.propositionAcceptances.map(pa => ({
      name: pa.student.index,
      iconName: this.getIconForAcceptanceAccepted(pa)
    }))
  }

  getIconForAcceptanceAccepted(pa: PropositionAcceptance): string {
    if (pa.accepted == true) return 'done'
    else if (pa.accepted == false) return 'close';
    else return 'help_outline';
  }

}
