import { Component, OnInit } from '@angular/core';
import { Status, Subject } from "../../../../shared/model";
import { ActivatedRoute, Router } from "@angular/router";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { RestService } from "../../../../shared/rest.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { Dictionary } from "../../../../shared/dictionary";

@Component({
  selector: 'app-draft-subject',
  templateUrl: './draft-subject.component.html',
  styleUrls: ['./draft-subject.component.scss']
})
export class DraftSubjectComponent implements OnInit {
  error = false;
  loading = true;
  subject!: Subject;
  subjectForm: FormGroup;


  showInitiatorPanel = false;
  showRealisersForm1 = false;
  showRealisersForm2 = false;
  showRealisersForm3 = false;

  private toolbarDraft: ToolbarConfig = {
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

  private toolbarToAccept: ToolbarConfig = {
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

  private toolbarAcceptedByInitiator: ToolbarConfig = {
    titleKey: 'details_subject_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'to_verification',
        click: () => this.sendToVerification()
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
      coRealisersNumber: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      isGroup: new FormControl(''),
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
        this.fillForm();
        this.chooseToolbar();
        this.loading = false;
      });
    }
  }

  private fillForm() {
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
      const coRealisersNumber = controls.coRealisersNumber;
      coRealisersNumber.setValue(subject.realiseresNumber - 1);
      const isGroup = controls.isGroup;
      if(subject.realiseresNumber > 1) {
        coRealisersNumber.enable();
        isGroup.setValue(true);
      } else {
        isGroup.setValue(false);
      }

      if(subject.initiator != null) {
        this.showInitiatorPanel = true;
        const mainRealiser = controls.mainRealiser;
        mainRealiser.setValue(subject.initiator.index);
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

      } else {
        this.showInitiatorPanel = false;
      }

      if(subject.status == Status.DRAFT && subject.initiator == null) {
        topic.enable();
        topicInEnglish.enable();
        objective.enable();
        objectiveInEnglish.enable();

      }
    }
  }

  private chooseToolbar() {
    // Jak oczekuje na moją (promotora) decyzje to daj akceptuj i odrzuc
    // Jak jest po prostu draft to daj wyślij do weryfikacji  (tutaj to chyba tez przydatna byłaby edycja KURWA)
    // jak jest zaakceptowane przez inicjatora to tez daj wyślij do weryfikacji
    // Jak odrzuca lub oczekuje na decyzje inicjatora to empty
    const subject = this.subject;
    if(subject.status == Status.REJECTED || subject.status == Status.ACCEPTED_BY_SUPERVISOR) {
      this.toolbarService.updateToolbarConfig(this.toolbarEmpty);
    } else if(subject.status == Status.ACCEPTED_BY_INITIATOR) {
      this.toolbarService.updateToolbarConfig(this.toolbarAcceptedByInitiator);
    } else if(subject.status == Status.DRAFT && subject.initiator != null) {
      this.toolbarService.updateToolbarConfig(this.toolbarToAccept);
    } else if(subject.status == Status.DRAFT) {
      this.toolbarService.updateToolbarConfig(this.toolbarDraft);
    }
  }

  groupChange(event: MatCheckboxChange) {
    const controls = this.subjectForm.controls;
    if (event.checked) {
      controls.coRealisersNumber.enable();
    } else {
      controls.coRealisersNumber.reset();
      controls.coRealisersNumber.disable();
    }
  }

  private sendToVerification() {
    this.loading = true;
    this.restService.sendToVerification(this.subject.subjectId).subscribe(
      resp => {
        console.log(resp)
        this.loading = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
      },
      err => {
        console.log(err)
        this.loading = false;
      }
    );
  }

  private accept() {
    this.loading = true;
    this.restService.acceptSupervisor(this.subject.subjectId).subscribe(
      resp => {
        console.log(resp)
        this.loading = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
      },
      err => {
        console.log(err)
        this.loading = false;
      }
    );
  }

  private decline() {
    this.loading = true;
    this.restService.reject(this.subject.subjectId).subscribe(
      resp => {
        console.log(resp)
        this.loading = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
      },
      err => {
        console.log(err)
        this.loading = false;
      }
    );
  }

  private collectData(): Dictionary<any> {
    const controls = this.subjectForm.controls;

    return {
      /*eslint-disable */
      subjectId: this.subject.subjectId,
      topic: controls.topic.value,
      topicInEnglish: controls.topicInEnglish.value,
      objective: controls.objective.value,
      objectiveInEnglish: controls.objectiveInEnglish.value,
      realiseresNumber: +controls.coRealisersNumber.value + 1
      /*eslint-enable */
    }
  }

  private updateSubject() {
    this.loading = true;
    const sub = this.collectData();
    this.restService.updateSubject(sub).subscribe(resp => {
      console.log(resp);
      this.loading = false;
      void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
    }, err => {
      console.log(err);
      this.loading = false;
    });
  }
}
