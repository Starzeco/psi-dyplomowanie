import { Component, OnInit } from '@angular/core';
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { TranslateService } from "@ngx-translate/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { RestService } from "../../../../shared/rest.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { Dictionary } from "../../../../shared/dictionary";

@Component({
  selector: 'app-create-subject',
  templateUrl: './create-subject.component.html',
  styleUrls: ['./create-subject.component.scss']
})
export class CreateSubjectComponent implements OnInit {

  loading = false;
  subjectForm: FormGroup;

  private toolbarApplySubject: ToolbarConfig = {
    titleKey: 'create_subject_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'create_subject_button',
        click: () => this.createSubject()
      }
    ]
  }

  constructor(private readonly toolbarService: ToolbarService,
              private readonly translateService: TranslateService,
              private readonly fb: FormBuilder,
              private readonly router: Router,
              private readonly restService: RestService) {
    this.subjectForm = this.fb.group({
      /*eslint-disable */
      topic: new FormControl('', Validators.required),
      topicInEnglish: new FormControl('', Validators.required),
      objective: new FormControl('', Validators.required),
      objectiveInEnglish: new FormControl('', Validators.required),
      realizationLanguage: new FormControl('', Validators.required),
      coRealisersNumber: new FormControl({
        disabled: true,
        value: ''
      }, Validators.required),
      isGroup: new FormControl(''),
      /*eslint-enable */
    });
  }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(this.toolbarApplySubject);
  }

  private createSubject() {
    this.loading = true;
    const controls = this.subjectForm.controls;
    if(controls.isGroup.value && !controls.coRealisersNumber.value) {
      controls.coRealisersNumber.setErrors({
        'required': true
      });
      controls.coRealisersNumber.markAsTouched();
      this.loading = false;
      return;
    }
    const collectedData = this.collectData();
    this.restService.createSubject(collectedData).subscribe(resp => {
      console.log(resp);
      this.loading = false;
      void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
    }, error => {
      console.log(error);
      this.loading = false;
    })
  }

  private collectData(): Dictionary<any> {
    const controls = this.subjectForm.controls;
    return {
      /*eslint-disable */
      topic: controls.topic.value,
      topicInEnglish: controls.topicInEnglish.value,
      objective: controls.objective.value,
      objectiveInEnglish: controls.objectiveInEnglish.value,
      realizationLanguage: controls.realizationLanguage.value,
      realiseresNumber: +controls.coRealisersNumber.value + 1,
      graduationProcessId: 1,
      initiatorId: null,
      supervisorId: 1,
      proposedRealiserIds: []
      /*eslint-enable */
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
}
