import { Component, OnInit } from '@angular/core';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { FilterConfig, FiltersEvent } from "../../../components/filters/filters.component";
import { ToolbarConfig } from "../../../components/toolbar/toolbar.component";
import { Subject } from "../../../shared/model";
import { RestService } from "../../../shared/rest.service";
import { Router } from "@angular/router";
import { MatTabChangeEvent } from "@angular/material/tabs";

const firstFiltersConfig_: FilterConfig[] = [
  {
    name: 'text_search',
    labelKey: 'search_by_topic_and_supervison',
    type: 'TEXT_SEARCH'
  },
  {
    name: 'status',
    labelKey: 'status',
    options: [
      { nameKey: 'in_verification_status', value: 'IN_VERIFICATION' },
      { nameKey: 'in_correction_status', value: 'IN_CORRECTION' },
      { nameKey: 'verified_status', value: 'VERIFIED' },
      { nameKey: 'reserved_status', value: 'RESERVED' }
    ],
    type: 'SELECT',
  },
  {
    name: 'type',
    labelKey: 'type',
    options: [
      { nameKey: 'group_type_label', value: 'GROUP' },
      { nameKey: 'individual_type_label', value: 'INDIVIDUAL' },
    ],
    type: 'SELECT',
  }
]

const secondFiltersConfig_: FilterConfig[] = [
  {
    name: 'text_search',
    labelKey: 'search_by_topic_and_supervison',
    type: 'TEXT_SEARCH'
  },
  {
    name: 'status',
    labelKey: 'status',
    options: [
      { nameKey: 'draft_status', value: 'DRAFT' },
      { nameKey: 'accepted_by_supervisor_status', value: 'ACCEPTED_BY_SUPERVISOR' },
      { nameKey: 'accepted_by_initiator_status', value: 'ACCEPTED_BY_INITIATOR' },
      { nameKey: 'rejected_status', value: 'REJECTED' }
    ],
    type: 'SELECT',
  },
  {
    name: 'type',
    labelKey: 'type',
    options: [
      { nameKey: 'group_type_label', value: 'GROUP' },
      { nameKey: 'individual_type_label', value: 'INDIVIDUAL' },
    ],
    type: 'SELECT',
  }
]

@Component({
  selector: 'app-supervisor-subjects',
  templateUrl: './supervisor-subjects.component.html',
  styleUrls: ['./supervisor-subjects.component.scss']
})
export class SupervisorSubjectsComponent implements OnInit {

  firstTabFilterConfig = firstFiltersConfig_;
  secondTabFilterConfig = secondFiltersConfig_;

  private toolbarConfig_: ToolbarConfig = {
    titleKey: 'subjects_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'create_subject',
        click: () => void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject', 'create'])
      }
    ]
  }
  subjects: Subject[] = [];

  constructor(
    private readonly toolbarService: ToolbarService,
    private readonly restService: RestService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(this.toolbarConfig_);
    this.getSubjects(null, null, null, true);
  }

  getSubjects(searchPhrase: string | null, subjectType: string | null, subjectStatus: string | null, processingSubjects: boolean) {
    this.restService.getSubjectsForSupervisor(1, searchPhrase, subjectType, processingSubjects, subjectStatus).subscribe(subs => {
      this.subjects = subs;
      console.log(this.subjects);
    });
  }

  onTabChange(event: MatTabChangeEvent) {
    if (event.index == 0) this.getSubjects(null, null, null, true);
    else if (event.index == 1) this.getSubjects(null, null, null, false);
  }

  onFilterChange(event: FiltersEvent, processingSubjects: boolean) {
    this.getSubjects(event.text_search as string, event.type as string, event.status as string, processingSubjects);
  }
}
