import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { Subject } from "../../../../shared/model";
import { RestService } from "../../../../shared/rest.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { Content, topicContent, objectvieContent } from 'src/app/components/details/topic-objective/topic-objective.component';
import { getSubjectStatusTranslation } from '../subject.common';
import { CandidateDialogComponent, CandidateDialogResult } from './candidate-dialog/candidate-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-available-details',
  templateUrl: './available-details.component.html',
  styleUrls: ['./available-details.component.scss']
})
export class AvailableDetailsComponent implements OnInit {

  loading = true;
  error = false;

  subject!: Subject;


  private readonly toolbarConfig: ToolbarConfig = {
    titleKey: 'candidate_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'candidate_button_header',
        click: () => this.openCandidateDialog()
      }
    ]
  }

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly toolbarService: ToolbarService,
    private readonly restService: RestService,
    private readonly matDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.fetchSubject();
  }

  private fetchSubject() {
    const subjectId = this.route.snapshot.paramMap.get('subject_id');
    if (!subjectId) {
      this.loading = false;
      this.error = true;
    } else {
      this.loading = true;
      this.error = false;
      this.restService.fetchSubjectById(+subjectId).subscribe({
        next: result => {
          this.subject = result;
          this.toolbarService.updateToolbarConfig(this.toolbarConfig);
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

  openCandidateDialog() {
    const dialogRef = this.matDialog.open(CandidateDialogComponent,
      {
        data: {
          titleKey: 'candidate',
          realizersNumber: this.subject.realiseresNumber
        }
      })

    dialogRef.afterClosed().subscribe((result?: CandidateDialogResult) => {
      if (result) {
        this.restService.getStudentsByIndexes(result.indexes).subscribe({
          next: result => {
            const studentsIds = result.map(r => r.studentId);
            this.restService.candidate({
              "studentId": 1,
              "subjectId": this.subject.subjectId,
              "coauthors": studentsIds
            }).subscribe({
              next: () => {
                this.loading = false;
                this.error = false;
                void this.router.navigate(['student', 'graduation_process', '1', 'subject']);
              },
              error: () => {
                this.loading = false;
                this.error = true;
              }
            });
          }, error: () => {
            this.loading = false;
            this.error = true;
          }
        }
        );
      }
    }
    )
  }


  prepareTopic(): Content {
    return topicContent(this.subject)
  }

  prepareObjective(): Content {
    return objectvieContent(this.subject)
  }

  getStatus(): string {
    return getSubjectStatusTranslation(this.subject)
  }

}
