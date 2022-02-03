import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MainRealizer, Realizer } from 'src/app/components/details/co-realisers/co-realisers.component';
import { Content, topicContent, objectvieContent } from 'src/app/components/details/topic-objective/topic-objective.component';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { Subject, Status, PropositionAcceptance, Candidature, Verification } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';
import { getSubjectStatusTranslation } from 'src/app/views/student-view/subject/subject.common';

const toolbarEmpty_: ToolbarConfig = {
  titleKey: 'processed_subject_details',
  iconName: 'note',
  buttonsConfig: []
}

@Component({
  selector: 'app-processed-subject-details',
  templateUrl: './processed-subject-details.component.html',
  styleUrls: ['./processed-subject-details.component.scss']
})
export class ProcessedSubjectDetailsComponent implements OnInit {

  error = false;
  loading = true;

  subject!: Subject;
  candidatures: Candidature[] = []
  verifications: Verification[] = []
  statuses = Status;

  private toolbarUpdate: ToolbarConfig = {
    titleKey: 'processed_subject_details',
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

  constructor(private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly router: Router,
    private readonly restService: RestService) {
  }

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
          this.subject = result;

          switch (result.status) {
            case Status.IN_CORRECTION:
              this.toolbarService.updateToolbarConfig(this.toolbarUpdate);
              this.fetchAllRejectedVerificationBySubjectId(result.subjectId)
              break;
            case Status.VERIFIED:
              this.toolbarService.updateToolbarConfig(toolbarEmpty_);
              this.fetchAllCandidatures(result.subjectId)
              break;
            default:
              this.toolbarService.updateToolbarConfig(toolbarEmpty_);
              break;
          }
          if (result.status !== Status.VERIFIED) {
            this.loading = false;
            this.error = false;
          }
        },
        error: () => {
          this.loading = false;
          this.error = true;
        }
      });
    }
  }

  private fetchAllRejectedVerificationBySubjectId(subjectId: number) {
    this.loading = true;
    this.error = false;
    this.restService.fetchAllRejectedVerificationBySubjectId(subjectId).subscribe({
      next: result => {
        this.loading = false;
        this.error = false;
        this.verifications = result
      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  private fetchAllCandidatures(subjectId: number) {
    this.loading = true;
    this.error = false;
    this.restService.fetchAllCandidaturesBySubjectId(subjectId).subscribe({
      next: result => {
        this.loading = false;
        this.error = false;
        this.candidatures = result
      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  accept(candidatureId: number) {
    this.restService.decideAboutCandidature(candidatureId, true).subscribe({
      next: () => {
        this.loading = false;
        this.error = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject'])
      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  decline(candidatureId: number) {
    this.restService.decideAboutCandidature(candidatureId, false).subscribe({
      next: () => {
        this.loading = false;
        this.error = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject'])
      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  private updateSubject() {
    void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject', 'processed', this.subject.subjectId, 'update'])
  }

  private sendToVerification() {
    this.loading = false;
    this.error = true;
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


  prepareMainRealizer(): MainRealizer {
    if (this.subject.status === Status.RESERVED)
      return {
        iconName: 'done',
        name: this.subject.realiser[0].index,
        labelKey: 'main-realiser',
      }
    else
      return {
        iconName: 'done',
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        name: this.subject.initiator!.index,
        labelKey: 'applicant',
      }
  }

  prepareCoorealizers(): Realizer[] {
    if (this.subject.status === Status.RESERVED) {
      return this.subject.realiser.slice(1).map(r => ({
        name: r.index,
        iconName: 'done'
      }))
    } else {
      return this.subject.propositionAcceptances.map(pa => ({
        name: pa.student.index,
        iconName: this.getIconForAcceptanceAccepted(pa)
      }))
    }
  }

  private getIconForAcceptanceAccepted(pa: PropositionAcceptance): string {
    if (pa.accepted == true) return 'done'
    else if (pa.accepted == false) return 'close';
    else return 'help_outline';
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

}
