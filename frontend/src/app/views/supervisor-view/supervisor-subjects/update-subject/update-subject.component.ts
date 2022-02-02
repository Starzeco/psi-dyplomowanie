import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { RestService } from "../../../../shared/rest.service";
import { Status, Subject } from "../../../../shared/model";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { Dictionary } from "../../../../shared/dictionary";

@Component({
  selector: 'app-update-subject',
  templateUrl: './update-subject.component.html',
  styleUrls: ['./update-subject.component.scss']
})
export class UpdateSubjectComponent implements OnInit {
  error = false;
  loading = true;
  subject!: Subject;
  subjectForm: FormGroup;

  showRealisersForm1 = false;
  showRealisersForm2 = false;
  showRealisersForm3 = false;
  mainRealiserKey = 'applicant';
  showRealisers = false;
  showSidePanel = false;

  statuses = Status;

  private toolbarUpdate: ToolbarConfig = {
    titleKey: 'supervisor_update_header',
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
      this.restService.fetchSubjectById(+subjectId).subscribe(sub => {
        this.subject = sub;
        this.fillAndDisableForm();
        if(this.subject.status == Status.IN_CORRECTION) {
          this.toolbarService.updateToolbarConfig(this.toolbarUpdate);
        } else {
          this.toolbarService.updateToolbarConfig(this.toolbarEmpty);
        }
        this.loading = false;
      });
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

      if(subject.initiator != null) {
        this.showSidePanel = true;
        const mainRealiser = controls.mainRealiser;
        mainRealiser.setValue(subject.initiator.index); // TU MUSI BYC INICJATOR
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
      } else if(subject.status == Status.RESERVED) {
        this.showSidePanel = true;
        this.showRealisers = false;
        this.mainRealiserKey = 'realiser';
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
        } else if(realisers.length > 3){
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

      if(subject.status == Status.IN_CORRECTION) {
        const topic = controls.topic;
        topic.enable();
        const topicInEnglish = controls.topicInEnglish;
        topicInEnglish.enable();
        const objective = controls.objective;
        objective.enable();
        const objectiveInEnglish = controls.objectiveInEnglish;
        objectiveInEnglish.enable();
      }
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

  private collectData(): Dictionary<any> {
    const controls = this.subjectForm.controls;

    return {
      /*eslint-disable */
      subjectId: this.subject.subjectId,
      topic: controls.topic.value,
      topicInEnglish: controls.topicInEnglish.value,
      objective: controls.objective.value,
      objectiveInEnglish: controls.objectiveInEnglish.value,
      realiseresNumber: this.subject.realiseresNumber
      /*eslint-enable */
    }
  }
}
