import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { RestService } from "../../../../shared/rest.service";
import { Candidature, CandidatureAcceptance } from "../../../../shared/model";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { MainRealizer, Realizer } from 'src/app/components/details/co-realisers/co-realisers.component';
import { Content, topicContent, objectvieContent } from 'src/app/components/details/topic-objective/topic-objective.component';
import { getCandidatureStatusTranslation, getSubjectStatusTranslation } from '../subject.common';

const toolbarEmpty_: ToolbarConfig = {
  titleKey: 'details_subject_header',
  iconName: 'note',
  buttonsConfig: []
}

@Component({
  selector: 'app-candidature-details',
  templateUrl: './candidature-details.component.html',
  styleUrls: ['./candidature-details.component.scss']
})
export class CandidatureDetailsComponent implements OnInit {

  loading = true;
  error = false;

  candidature!: Candidature;
  studentCA?: CandidatureAcceptance;

  private toolbalCandidature: ToolbarConfig = {
    titleKey: 'candidature_header_details',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'accept',
        click: () => this.updateCandidatureDecision(true)
      },
      {
        textKey: 'decline',
        click: () => this.updateCandidatureDecision(false)
      }
    ]
  }

  constructor(private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly router: Router,
    private readonly restService: RestService) {
  }

  ngOnInit(): void {
    this.fetchCandidature();
  }

  private fetchCandidature() {
    const candidatureId = this.route.snapshot.paramMap.get('candidature_id');
    if (!candidatureId) {
      this.loading = false;
      this.error = true;
    } else {
      this.loading = true;
      this.error = false;
      this.restService.fetchCandidatureById(+candidatureId).subscribe({
        next: result => {
          this.candidature = result;
          this.studentCA = this.getCurrentStudentCA(result);
          if (this.studentCA && this.studentCA.accepted === null) this.toolbarService.updateToolbarConfig(this.toolbalCandidature);
          else this.toolbarService.updateToolbarConfig(toolbarEmpty_);
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

  private updateCandidatureDecision(decision: boolean) {
    this.loading = true;
    this.error = false;
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    const caId = this.studentCA!.candidatureAcceptanceId;
    this.restService.updateCandidateAcceptance(decision, caId).subscribe({
      next:
        result => {
          console.log(result);
          this.loading = false;
          this.error = false;
          void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
        }, error: err => {
          console.log(err);
          console.log(err);
          this.loading = false;
          this.error = true;
        }
    });
  }

  private getCurrentStudentCA(candidature: Candidature) {
    return candidature.candidatureAcceptances.find(a => a.student.index == '242422');
  }

  prepareMainRealizer(): MainRealizer {
    return {
      iconName: 'done',
      name: this.candidature.student.index,
      labelKey: 'applicant',
    }
  }

  prepareCoorealizers(): Realizer[] {
    return this.candidature.candidatureAcceptances.map(ca => ({
      name: ca.student.index,
      iconName: this.getIconForAcceptanceAccepted(ca)
    }))
  }

  prepareTopic(): Content {
    return topicContent(this.candidature.subject)
  }

  prepareObjective(): Content {
    return objectvieContent(this.candidature.subject)
  }

  getIconForAcceptanceAccepted(ca: CandidatureAcceptance): string {
    if (ca.accepted == true) return 'done'
    else if (ca.accepted == false) return 'close';
    else return 'help_outline';
  }

  getStatus(): string {
    return getCandidatureStatusTranslation(this.candidature);
  }

  getSubjectStatus(): string {
    return getSubjectStatusTranslation(this.candidature.subject)
  }
}
