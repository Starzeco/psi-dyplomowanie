import { Component, OnInit } from '@angular/core';
import { Status, Subject } from "../../../../shared/model";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { RestService } from "../../../../shared/rest.service";

@Component({
  selector: 'app-applied-details',
  templateUrl: './applied-details.component.html',
  styleUrls: ['./applied-details.component.scss']
})
export class AppliedDetailsComponent implements OnInit {
  error = false;
  loading = true;
  subject!: Subject;
  subjectForm: FormGroup;

  showRealisersForm1 = false;
  showRealisersForm2 = false;
  showRealisersForm3 = false;
  mainRealiserKey = 'main-realiser';
  showRealisersReserved = false;

  private toolbarEmpty: ToolbarConfig = {
    titleKey: 'details_subject_header',
    iconName: 'note',
    buttonsConfig: []
  }

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
              private readonly fb: FormBuilder,
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
    this.getSubject();
  }

  private getSubject() {
    const subjectId = this.route.snapshot.paramMap.get('subject_id');
    if (subjectId == null) {
      this.error = true;
    } else {
      this.restService.getSubjectById(+subjectId).subscribe(sub => {
        this.subject = sub;
        this.fillAndDisableForm();
        this.chooseToolbar(this.subject);
        this.loading = false;
      });
    }
  }

  private chooseToolbar(subject: Subject) {
    if(subject.status == Status.DRAFT) {
      if(subject.initiator != null) { // W DRAFT i w tym komponencie nigdy inicjator nie jest nullem
        const acceptances = subject.propositionAcceptances;
        if(acceptances.length == 0 || subject.initiator.index == '242422') {
          this.toolbarService.updateToolbarConfig(this.toolbarEmpty);
          return;
        }
        const acceptance = acceptances.find(a => a.student.index == '242422');
        if(acceptance != null && acceptance.accepted !== null) {
          this.toolbarService.updateToolbarConfig(this.toolbarEmpty);
        } else {
          this.toolbarService.updateToolbarConfig(this.toolbarDecision);
        }
      }
    } else if(subject.status == Status.ACCEPTED_BY_SUPERVISOR && subject.initiator != null && subject.initiator.index == '242422') {
      this.toolbarService.updateToolbarConfig(this.toolbalAcceptInitiator);
    } else {
      this.toolbarService.updateToolbarConfig(this.toolbarEmpty);
    }
  }

  private fillAndDisableForm() {
    const controls = this.subjectForm.controls;
    const subject = this.subject;
    if(subject != null) {
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
      supervisor.setValue(subject.supervisor.fullName);
      if(subject.status != Status.RESERVED || subject.initiator != null) {
        const mainRealiser = controls.mainRealiser;
        mainRealiser.setValue(subject.initiator!.index); // TU MUSI BYC INICJATOR
        this.mainRealiserKey = 'applicant';
        if(subject.realiseresNumber > 1) {
          const acceptances = subject.propositionAcceptances;
          if(subject.realiseresNumber == 2) {
            this.showRealisersForm1 = true;
            controls.firstCoRealiser.setValue(acceptances[0].student.index);
          } else if(subject.realiseresNumber == 3) {
            this.showRealisersForm1 = true;
            this.showRealisersForm2 = true;
            controls.firstCoRealiser.setValue(acceptances[0].student.index);
            controls.secondCoRealiser.setValue(acceptances[1].student.index);
          } else {
            this.showRealisersForm1 = true;
            this.showRealisersForm2 = true;
            this.showRealisersForm3 = true;
            controls.firstCoRealiser.setValue(acceptances[0].student.index);
            controls.secondCoRealiser.setValue(acceptances[1].student.index);
            controls.thirdCoRealiser.setValue(acceptances[2].student.index);
          }
        } else {
          this.showRealisersForm1 = false;
          this.showRealisersForm2 = false;
          this.showRealisersForm3 = false;
        }
      } else { // TU JEST RESERVED i nie ma inicjatora - temat wzięty poprzez kandydature
        this.mainRealiserKey = 'main-realiser';
        this.showRealisersReserved = true;
        const realisers = subject.realiser;
        if(realisers.length == 1) {
          const mainRealiser = controls.mainRealiser;
          mainRealiser.setValue(realisers[0].index);
        } else if(realisers.length == 2) {
          const mainRealiser = controls.mainRealiser;
          mainRealiser.setValue(realisers[0].index);
          this.showRealisersForm1 = true;
          controls.firstCoRealiser.setValue(realisers[1].index);
        } else if(realisers.length == 3) {
          const mainRealiser = controls.mainRealiser;
          mainRealiser.setValue(realisers[0].index);

          this.showRealisersForm1 = true;
          controls.firstCoRealiser.setValue(realisers[1].index);
          this.showRealisersForm2 = true;
          controls.secondCoRealiser.setValue(realisers[2].index);
        } else if(realisers.length == 4){
          const mainRealiser = controls.mainRealiser;
          mainRealiser.setValue(realisers[0].index);

          this.showRealisersForm1 = true;
          controls.firstCoRealiser.setValue(realisers[1].index);
          this.showRealisersForm2 = true;
          controls.secondCoRealiser.setValue(realisers[2].index);
          this.showRealisersForm3 = true;
          controls.thirdCoRealiser.setValue(realisers[3].index);
        }
      }
    }
  }

  private updateProposition(decision: boolean) {
    this.loading = true;
    if(this.subject != null) {
      const proposition = this.subject.propositionAcceptances.find(a => a.student.index == '242422');
      if(proposition != null) {
        const propositionId = proposition.propositionAcceptanceId;
        this.restService.updateProposition(decision, propositionId!).subscribe(
          resp => {
            console.log(resp);
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
            this.loading = false;
          },
          err => {
            console.log(err);
            this.loading = false;
          }
        );
      }
    } else {
      this.loading = false;
    }
  }

  private acceptInitiator(decision: boolean) {
    this.loading = true;
    if(this.subject != null) {
      if(decision) {
        this.restService.acceptInitiator(this.subject.subjectId).subscribe(
          resp => {
            console.log(resp);
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
            this.loading = false;
          },
          err => {
            console.log(err);
            this.loading = false;
          }
        );
      } else {
        this.restService.reject(this.subject.subjectId).subscribe(
          resp => {
            console.log(resp);
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
            this.loading = false;
          },
          err => {
            console.log(err);
            this.loading = false;
          }
        );
      }
    } else {
      this.loading = false;
    }
  }

  getIcon(index: number): string {
    if(this.subject != null) {
      if(this.showRealisersReserved) {
        return 'done';
      } else if(index != -1) {
        const acceptance = this.subject.propositionAcceptances[index];
        if(acceptance.accepted === null) {
          return 'help_outline';
        }
        return acceptance.accepted ? 'done' : 'close';
      }
    }
    return '';
  }
}
