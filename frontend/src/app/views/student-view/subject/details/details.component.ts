import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { StaffMember, Subject } from "../../../../shared/model";
import { RestService } from "../../../../shared/rest.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { Dictionary } from 'src/app/shared/dictionary';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

  loading = true;
  subject: Subject | null = null;
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

  private toolbarCandidate: ToolbarConfig = {
    titleKey: 'candidate_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'candidate_button_header',
        click: () => this.candidate()
      }
    ]
  }

  showRealisersForm = true;
  showIsGroup = true;
  supervisorPresentation = false;

  constructor(private readonly route: ActivatedRoute,
              private readonly toolbarService: ToolbarService,
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
      supervisor: new FormControl('', Validators.required),
      supervisorPresentation: new FormControl({
        disabled: true,
        value: ''
      }),
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
      this.toolbarService.updateToolbarConfig(this.toolbarApplySubject);
      this.restService.getSupervisorsByGraduationProcessId(1).subscribe(supervisors => {
        this.supervisors = supervisors;
        this.loading = false;
      });
    } else {
      this.toolbarService.updateToolbarConfig(this.toolbarCandidate);
      this.restService.getSubjectById(+subjectId).subscribe(sub => {
        this.subject = sub;
        console.log(this.subject);
        this.fillAndDisableForm();
        this.loading = false;
      });
    }
  }

  private fillAndDisableForm() {
    const controls = this.subjectForm.controls;
    const subject = this.subject;
    if(subject != null) {
      this.showIsGroup = false;
      const topic = controls.topic;
      topic.setValue(subject.topic);
      topic.disable();
      const topicInEnglish = controls.topicInEnglish;
      topicInEnglish.setValue(subject.topicInEnglish);
      topicInEnglish.disable();
      const objective = controls.objective;
      objective.setValue(subject.objective);
      objective.disable();
      const objectiveInEnglish = controls.objectiveInEnglish;
      objectiveInEnglish.setValue(subject.objectiveInEnglish);
      objectiveInEnglish.disable();
      const realizationLanguage = controls.realizationLanguage;
      realizationLanguage.setValue(subject.realizationLanguage);
      realizationLanguage.disable();
      const supervisor = controls.supervisorPresentation;
      this.supervisorPresentation = true;
      supervisor.setValue(subject.supervisor.fullName);
      supervisor.disable();
      if(subject.realiseresNumber > 1) {
        this.showRealisersForm = true;
        if(subject.realiseresNumber == 2) {
          controls.firstCoRealiser.enable();
        } else if(subject.realiseresNumber == 3) {
          controls.firstCoRealiser.enable();
          controls.secondCoRealiser.enable();
        } else {
          controls.firstCoRealiser.enable();
          controls.secondCoRealiser.enable();
          controls.thirdCoRealiser.enable();
        }
      } else {
        this.showRealisersForm = false;
      }
    }
  }

  private candidate() {
    if(this.subject != null) {
      this.loading = true;
      const controls = this.subjectForm.controls;
      const realiseresNumber = this.subject.realiseresNumber;
      const realisersArr = [];
      if(realiseresNumber == 2) {
        const firstCoRealiser = controls.firstCoRealiser;
        if(!firstCoRealiser.value) {
          firstCoRealiser.setErrors({
            'required': true
          });
          firstCoRealiser.markAsTouched();
          this.loading = false;
          return
        }
        realisersArr.push(firstCoRealiser.value);
      } else if(realiseresNumber == 3) {
        const firstCoRealiser = controls.firstCoRealiser;
        if(!firstCoRealiser.value) {
          firstCoRealiser.setErrors({
            'required': true
          });
          firstCoRealiser.markAsTouched();
          this.loading = false;
          return
        }
        const secondCoRealiser = controls.secondCoRealiser;
        if(!secondCoRealiser.value) {
          secondCoRealiser.setErrors({
            'required': true
          });
          secondCoRealiser.markAsTouched();
          this.loading = false;
          return
        }

        realisersArr.push(firstCoRealiser.value);
        realisersArr.push(secondCoRealiser.value);
      } else if(realiseresNumber == 4) {
        const firstCoRealiser = controls.firstCoRealiser;
        if(!firstCoRealiser.value) {
          firstCoRealiser.setErrors({
            'required': true
          });
          firstCoRealiser.markAsTouched();
          this.loading = false;
          return
        }
        const secondCoRealiser = controls.secondCoRealiser;
        if(!secondCoRealiser.value) {
          secondCoRealiser.setErrors({
            'required': true
          });
          secondCoRealiser.markAsTouched();
          this.loading = false;
          return
        }
        const thirdCoRealiser = controls.thirdCoRealiser;
        if(!thirdCoRealiser.value) {
          thirdCoRealiser.setErrors({
            'required': true
          });
          thirdCoRealiser.markAsTouched();
          this.loading = false;
          return
        }
        realisersArr.push(firstCoRealiser.value);
        realisersArr.push(secondCoRealiser.value);
        realisersArr.push(thirdCoRealiser.value);
      }

      this.restService.getStudentsByIndexes(realisersArr as string[]).subscribe(
        students => {
          const studentsIds = students.map(s => s.studentId);
          this.restService.candidate({
            "studentId": 1,
            "subjectId": this.subject!.subjectId,
            "coauthors": studentsIds
          }).subscribe(resp => {
            console.log(resp);
            this.loading = false;
            void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
          }, err => {
            console.log(err);
            this.loading = false;
          });
        }, error => {
          console.log(error);
          this.loading = false;
        }
      );
    }
  }

  private createSubject() {
    this.loading = true;
    const controls = this.subjectForm.controls;
    const indexes = [controls.firstCoRealiser.value as string, controls.secondCoRealiser.value as string, controls.thirdCoRealiser.value as string];
    this.restService.getStudentsByIndexes(indexes).subscribe(students => {
      const studentsIds = students.map(s => s.studentId);
      const collectedData = this.collectData();
      collectedData.realiseresNumber = 1 + studentsIds.length;
      collectedData.proposedRealiserIds = studentsIds;
      this.restService.createSubject(collectedData).subscribe(resp => {
        console.log(resp);
        this.loading = false;
        void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
      }, error => {
        console.log(error);
        this.loading = false;
      })
    }, err => {
      console.log(err)
      this.loading = false
    });
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
      graduationProcessId: 1,
      initiatorId: 1,
      supervisorId: controls.supervisor.value.staffMemberId,
      /*eslint-enable */
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
}
