import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { ActivatedRoute, Router } from '@angular/router';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { Dictionary } from 'src/app/shared/dictionary';
import { Subject, Status } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';


// eslint-disable-next-line @typescript-eslint/unbound-method
const indexValidators = [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(6), Validators.maxLength(6)]

@Component({
  selector: 'app-draft-subject-update',
  templateUrl: './draft-subject-update.component.html',
  styleUrls: ['./draft-subject-update.component.scss']
})
export class DraftSubjectUpdateComponent implements OnInit {
  error = false;
  loading = true;
  subject!: Subject;
  subjectForm!: FormGroup;

  showInitiatorPanel = false;
  showRealisersForm1 = false;
  showRealisersForm2 = false;
  showRealisersForm3 = false;

  private toolbarConfig: ToolbarConfig = {
    titleKey: 'subject_update',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'save_changes',
        click: () => this.updateSubject()
      }
    ]
  }

  constructor(private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly formBuilder: FormBuilder,
    private readonly router: Router,
    private readonly restService: RestService) {
  }

  private buildFormGroup() {
    return this.formBuilder.group({
      /*eslint-disable */
      mainRealiser: new FormControl({
        disabled: true,
        value: ''
      }),
      topic: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      topicInEnglish: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      objective: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      objectiveInEnglish: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      realizationLanguage: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      coRealisersNumber: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      isGroup: new FormControl(''),
      firstCoRealiser: new FormControl({
        disabled: true,
        value: ''
      }, indexValidators),
      secondCoRealiser: new FormControl({
        disabled: true,
        value: ''
      }, indexValidators),
      thirdCoRealiser: new FormControl({
        disabled: true,
        value: ''
      }, indexValidators),
    });
  }

  ngOnInit(): void {
    this.subjectForm = this.buildFormGroup();
    this.toolbarService.updateToolbarConfig(this.toolbarConfig);
    this.fetchSubject();
  }

  private fetchSubject() {
    const subjectId = this.route.snapshot.paramMap.get('subject_id');
    if (subjectId == null) {
      this.loading = true;
      this.error = false;
    } else {
      this.restService.fetchSubjectById(+subjectId).subscribe({
        next: result => {
          this.subject = result;
          this.fillForm();
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

  private fillForm() {
    const controls = this.subjectForm.controls;
    const subject = this.subject;
    if (subject != null) {
      controls.topic.setValue(subject.topic);
      controls.topicInEnglish.setValue(subject.topicInEnglish);
      controls.objective.setValue(subject.objective);
      controls.objectiveInEnglish.setValue(subject.objectiveInEnglish);
      controls.realizationLanguage.setValue(subject.realizationLanguage);
      controls.coRealisersNumber.setValue(subject.realiseresNumber - 1);
      if (subject.realiseresNumber > 1) {
        controls.isGroup.enable();
        controls.isGroup.setValue(true);
      } else {
        controls.isGroup.setValue(false);
      }

      if (subject.initiator != null) {
        this.showInitiatorPanel = true;
        controls.mainRealiser.setValue(subject.initiator.index);
        if (subject.realiseresNumber > 1) {
          const acceptances = subject.propositionAcceptances;
          if (subject.realiseresNumber == 2) {
            this.showRealisersForm1 = true;
            controls.firstCoRealiser.setValue(acceptances[0].student.index);
          } else if (subject.realiseresNumber == 3) {
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

      if (subject.status == Status.DRAFT && !subject.initiator) {
        controls.topic.enable();
        controls.topicInEnglish.enable();
        controls.objective.enable();
        controls.objectiveInEnglish.enable();
      }
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
    this.error = false;
    const sub = this.collectData();
    this.restService.updateSubject(sub).subscribe({
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
}
