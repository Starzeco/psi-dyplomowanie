/* eslint-disable @typescript-eslint/unbound-method */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { StaffMember } from "../../../../shared/model";
import { RestService } from "../../../../shared/rest.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { Dictionary } from 'src/app/shared/dictionary';
import { TranslateService } from "@ngx-translate/core";

const indexValidators = [Validators.pattern("^[0-9]*$"), Validators.minLength(6), Validators.maxLength(6)]

@Component({
  selector: 'app-subject-create',
  templateUrl: './subject-create.component.html',
  styleUrls: ['./subject-create.component.scss']
})
export class SubjectCreateComponent implements OnInit {

  loading = true;
  error = false;

  supervisors: StaffMember[] = [];

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

  showRealisersForm = true;
  showIsGroup = true;
  supervisorPresentation = false;

  constructor(private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly translateService: TranslateService,
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly restService: RestService) {
    this.subjectForm = this.fb.group({
      topic: new FormControl('', Validators.required),
      topicInEnglish: new FormControl('', Validators.required),
      objective: new FormControl('', Validators.required),
      objectiveInEnglish: new FormControl('', Validators.required),
      realizationLanguage: new FormControl('', Validators.required),
      supervisor: new FormControl('', Validators.required),
      supervisorPresentation: new FormControl({
        disabled: true,
        value: ''
      }),
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
        value: '',
      }, indexValidators),
    });
  }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(this.toolbarApplySubject);
    this.fetchSupervisors();
  }

  private fetchSupervisors() {
    this.loading = true;
    this.error = false;
    this.restService.fetchSupervisorsByGraduationProcessId(1).subscribe({
      next: result => {
        this.supervisors = result;
        this.loading = false;
        this.error = false;

      },
      error: () => {
        this.loading = false;
        this.error = true;
      }
    });
  }

  private createSubject() {
    this.loading = true;
    this.error = false;

    const controls = this.subjectForm.controls;
    const indexes = [
      controls.firstCoRealiser.value as string,
      controls.secondCoRealiser.value as string,
      controls.thirdCoRealiser.value as string
    ];
    this.restService.getStudentsByIndexes(indexes).subscribe({
      next: result => {
        const studentsIds = result.map(r => r.studentId);
        const collectedData = {
          ...this.collectData(),
          realiseresNumber: 1 + studentsIds.length,
          proposedRealiserIds: studentsIds,
        }

        this.restService.createSubject(collectedData).subscribe({
          next: () => {
            this.loading = false;
            this.error = false;
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
          },
          error: () => {
            this.loading = false;
            this.error = true;
          }
        })
      }, error: () => {
        this.loading = false
        this.error = true;

      }
    });
  }

  private collectData(): Dictionary<unknown> {
    const controls = this.subjectForm.controls;
    return {
      topic: controls.topic.value,
      topicInEnglish: controls.topicInEnglish.value,
      objective: controls.objective.value,
      objectiveInEnglish: controls.objectiveInEnglish.value,
      realizationLanguage: controls.realizationLanguage.value,
      graduationProcessId: 1,
      initiatorId: 1,
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      supervisorId: controls.supervisor.value.staffMemberId,
    }
  }

  groupChange(event: MatCheckboxChange) {
    const controls = this.subjectForm.controls;
    if (event.checked) {
      controls.firstCoRealiser.enable();
      controls.secondCoRealiser.enable();
      controls.thirdCoRealiser.enable();
    } else {
      controls.firstCoRealiser.reset();
      controls.secondCoRealiser.reset();
      controls.thirdCoRealiser.reset();
      controls.firstCoRealiser.disable();
      controls.secondCoRealiser.disable();
      controls.thirdCoRealiser.disable();
    }
  }

  getSupervisorFullName(supervisor?: StaffMember) {
    if (supervisor) {
      const title = this.translateService.instant(supervisor.title) as string
      return `${title} ${supervisor.name} ${supervisor.surname}`;
    } else return ""
  }
}
