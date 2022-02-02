import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { Dictionary } from 'src/app/shared/dictionary';
import { Subject, Status } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';

const toolbarEmpty_: ToolbarConfig = {
  titleKey: 'processed_subject_update',
  iconName: 'note',
  buttonsConfig: []
}

@Component({
  selector: 'app-processed-subject-update',
  templateUrl: './processed-subject-update.component.html',
  styleUrls: ['./processed-subject-update.component.scss']
})
export class ProcessedSubjectUpdateComponent implements OnInit {

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

  private toolbarUpdateConfig: ToolbarConfig = {
    titleKey: 'processed_subject_update',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'update',
        click: () => this.updateSubject()
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
    this.fetchSubject();
  }

  private fetchSubject() {
    const subjectId = this.route.snapshot.paramMap.get('subject_id');
    if (subjectId == null) {
      this.loading = false;
      this.error = true;
    } else {
      this.loading = true;
      this.error = false;
      this.restService.fetchSubjectById(+subjectId).subscribe({
        next: result => {
          this.subject = result;
          this.fillAndDisableForm();

          if (this.subject.status == Status.IN_CORRECTION) this.toolbarService.updateToolbarConfig(this.toolbarUpdateConfig);
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

  private fillAndDisableForm() {
    const controls = this.subjectForm.controls;
    const subject = this.subject;
    if (subject != null) {
      controls.topic.setValue(subject.topic);
      controls.topicInEnglish.setValue(subject.topicInEnglish);
      controls.objective.setValue(subject.objective);
      controls.objectiveInEnglish.setValue(subject.objectiveInEnglish);
      controls.realizationLanguage.setValue(subject.realizationLanguage);
      if (subject.initiator != null) {
        this.showSidePanel = true;
        controls.mainRealiser.setValue(subject.initiator.index); // TU MUSI BYC INICJATOR
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
      } else if (subject.status == Status.RESERVED) {
        this.showSidePanel = true;
        this.showRealisers = false;
        this.mainRealiserKey = 'realiser';
        const realisers = subject.realiser;
        if (realisers.length == 1) {
          controls.mainRealiser.setValue(realisers[0].index);
        } else if (realisers.length == 2) {
          controls.mainRealiser.setValue(realisers[0].index);
          this.showRealisersForm1 = true;
          controls.firstCoRealiser.setValue(realisers[1].index);
        } else if (realisers.length == 3) {
          controls.mainRealiser.setValue(realisers[0].index);
          this.showRealisersForm1 = true;
          controls.firstCoRealiser.setValue(realisers[1].index);
          this.showRealisersForm2 = true;
          controls.secondCoRealiser.setValue(realisers[2].index);
        } else if (realisers.length > 3) {
          controls.mainRealiser.setValue(realisers[0].index);

          this.showRealisersForm1 = true;
          controls.firstCoRealiser.setValue(realisers[1].index);
          this.showRealisersForm2 = true;
          controls.secondCoRealiser.setValue(realisers[2].index);
          this.showRealisersForm3 = true;
          controls.thirdCoRealiser.setValue(realisers[3].index);
        }

      }

      if (subject.status == Status.IN_CORRECTION) {
        controls.topic.enable();
        controls.topicInEnglish.enable();
        controls.objective.enable();
        controls.objectiveInEnglish.enable();
      }
    }
  }

  private updateSubject() {
    this.loading = true;
    this.error = false;

    const sub = this.collectData();
    this.restService.updateSubject(sub).subscribe({
      next: () => {
        this.loading = true;
        this.error = false;
        void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject']);
      }, error: () => {
        this.loading = false;
        this.error = false;
      }
    });
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
