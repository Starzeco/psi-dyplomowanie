import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { RestService } from "../../../../shared/rest.service";
import { Candidature, StaffMember } from "../../../../shared/model";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { TranslateService } from "@ngx-translate/core";

@Component({
  selector: 'app-candidature-details',
  templateUrl: './candidature-details.component.html',
  styleUrls: ['./candidature-details.component.scss']
})
export class CandidatureDetailsComponent implements OnInit {

  loading = true;
  error = false;
  candidature!: Candidature;
  subjectForm: FormGroup;


  showRealisersForm1 = false;
  showRealisersForm2 = false;
  showRealisersForm3 = false;

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

  private toolbarEmpty: ToolbarConfig = {
    titleKey: 'details_subject_header',
    iconName: 'note',
    buttonsConfig: []
  }

  constructor(private readonly route: ActivatedRoute,
              private readonly toolbarService: ToolbarService,
              private readonly fb: FormBuilder,
              private readonly translateService: TranslateService,
              private readonly router: Router,
              private readonly restService: RestService) {
    this.subjectForm = this.fb.group({
      /*eslint-disable */
      mainRealiser: new FormControl({
        disabled: true,
        value: ''
      }),
      topic: new FormControl({
        disabled: true,
        value: ''
      }),
      topicInEnglish: new FormControl({
        disabled: true,
        value: ''
      }),
      objective: new FormControl({
        disabled: true,
        value: ''
      }),
      objectiveInEnglish: new FormControl({
        disabled: true,
        value: ''
      }),
      realizationLanguage: new FormControl({
        disabled: true,
        value: ''
      }),
      supervisorPresentation: new FormControl({
        disabled: true,
        value: ''
      }),
      firstCoRealiser: new FormControl({
        disabled: true,
        value: ''
      }),
      secondCoRealiser: new FormControl({
        disabled: true,
        value: ''
      }),
      thirdCoRealiser: new FormControl({
        disabled: true,
        value: ''
      }),
      /*eslint-enable */
    });
  }

  ngOnInit(): void {
    this.getCandidature();
  }

  private getCandidature() {
    const candidatureId = this.route.snapshot.paramMap.get('candidature_id');
    if (candidatureId == null) {
      this.error = true;
    } else {
      this.restService.getCandidatureById(+candidatureId).subscribe(resp => {
          this.candidature = resp;
          this.fillForm();
          if(this.canDecideAboutCandidature()) {
            this.toolbarService.updateToolbarConfig(this.toolbalCandidature);
          } else {
            this.toolbarService.updateToolbarConfig(this.toolbarEmpty);
          }
          this.loading = false;
        },
error => {
        console.log(error);
        this.loading = false;
      });
    }
  }

  private canDecideAboutCandidature(): boolean {
    if(this.candidature != null) {
      return this.candidature.candidatureAcceptances.filter(a => a.accepted === null).map(a => a.student.index).includes('242422')
    }
    return false;
  }

  private fillForm() {
    const subject = this.candidature.subject;
    const controls = this.subjectForm.controls;
    const topic = controls.topic;
    topic.setValue(subject.topic);
    const topicInEnglish = controls.topicInEnglish;
    topicInEnglish.setValue(subject.topicInEnglish);
    const objective = controls.objective;
    objective.setValue(subject.objective);
    const objectiveInEnglish = controls.objectiveInEnglish;
    objectiveInEnglish.setValue(subject.objectiveInEnglish);
    const realizationLanguage = controls.realizationLanguage;
    realizationLanguage.setValue(subject.realizationLanguage);
    const supervisor = controls.supervisorPresentation;
    supervisor.setValue(this.getSupervisorFullName(subject.supervisor));
    const mainRealiser = controls.mainRealiser;
    mainRealiser.setValue(this.candidature.student.index);
    const acceptances = this.candidature.candidatureAcceptances;
    if(acceptances != null && acceptances.length != 0) {
      if(acceptances.length == 1) {
        this.showRealisersForm1 = true;
        controls.firstCoRealiser.setValue(acceptances[0].student.index);
      } else if(acceptances.length == 2) {
        this.showRealisersForm1 = true;
        this.showRealisersForm2 = true;
        controls.firstCoRealiser.setValue(acceptances[0].student.index);
        controls.secondCoRealiser.setValue(acceptances[1].student.index);
      } else if(acceptances.length == 3) {
        this.showRealisersForm1 = true;
        this.showRealisersForm2 = true;
        this.showRealisersForm3 = true;
        controls.firstCoRealiser.setValue(acceptances[0].student.index);
        controls.secondCoRealiser.setValue(acceptances[1].student.index);
        controls.thirdCoRealiser.setValue(acceptances[2].student.index);
      }
    }
  }

  private updateCandidatureDecision(decision: boolean) {
    this.loading = true;
    const cA = this.candidature.candidatureAcceptances.find(a => a.student.index == '242422');
    if(cA != null) {
      this.restService.updateCandidateAcceptance(decision, cA.candidatureAcceptanceId).subscribe(
        resp => {
          console.log(resp);
          this.loading = false;
          void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
        }, err => {
          console.log(err);
          this.loading = false;
        }
      );
    }
  }

  getIcon(index: number): string {
    if(this.candidature != null) {
      if(this.candidature.accepted) {
        return 'done';
      } else if(index != -1) {
        const acceptance = this.candidature.candidatureAcceptances[index];
        if(acceptance.accepted === null) {
          return 'help_outline';
        }
        return acceptance.accepted ? 'done' : 'close';
      }
    }
    return '';
  }

  getSupervisorFullName(supervisor?: StaffMember) {
    if (supervisor) {
      const title = this.translateService.instant(supervisor.title) as string
      return `${title} ${supervisor.name} ${supervisor.surname}`;
    } else return ""
  }
}
