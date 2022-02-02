import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { Subject } from "../../../../shared/model";
import { RestService } from "../../../../shared/rest.service";
import { ToolbarConfig } from "../../../../components/toolbar/toolbar.component";
import { ToolbarService } from "../../../../components/toolbar/toolbar.service";
import { Content, topicContent, objectvieContent } from 'src/app/components/details/topic-objective/topic-objective.component';
import { getSubjectStatusTranslation } from '../subject.common';

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
        click: () => console.log('dupa')
      }
    ]
  }

  constructor(
    private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly restService: RestService
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
