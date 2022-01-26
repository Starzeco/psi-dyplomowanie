import { Component } from '@angular/core';
import { ToolbarConfig } from "../../../components/toolbar/toolbar.component";
import { Router } from "@angular/router";
import { RestService } from "../../../shared/rest.service";
import { CandidaturePartialInfo, Subject } from "../../../shared/model";
import { MatTabChangeEvent } from "@angular/material/tabs";
import { FilterConfig, FiltersEvent } from "../../../components/filters/filters.component";
import { ToolbarService } from "../../../components/toolbar/toolbar.service";


const filtersConfigAvailable_: FilterConfig[] = [
  {
    name: 'text_search',
    labelKey: 'search_by_topic_and_supervison',
    type: 'TEXT_SEARCH'
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


const filtersConfigApplied_: FilterConfig[] = [
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
      { nameKey: 'in_verification_status', value: 'IN_VERIFICATION' },
      { nameKey: 'in_correction_status', value: 'IN_CORRECTION' },
      { nameKey: 'verified_status', value: 'VERIFIED' },
      { nameKey: 'rejected_status', value: 'REJECTED' },
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


const filterCandidatureConf_: FilterConfig[] = [
  {
    name: 'text_search',
    labelKey: 'search_by_topic_and_supervison',
    type: 'TEXT_SEARCH'
  },
  {
    name: 'status',
    labelKey: 'status',
    options: [
      { nameKey: 'to_accept_by_supervisor_students_status', value: 'TO_ACCEPT_BY_STUDENTS' },
      { nameKey: 'to_accept_by_supervisor_status', value: 'TO_ACCEPT_BY_SUPERVISOR' },
      { nameKey: 'accepted_status', value: 'ACCEPTED' },
      { nameKey: 'rejected_status', value: 'REJECTED' },
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
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent {

  filterAvailableConf = filtersConfigAvailable_;
  filterAppliedConf = filtersConfigApplied_;
  filterCandidatureConf = filterCandidatureConf_;

  private toolbarConfig_: ToolbarConfig = {
    titleKey: 'subjects_header',
    iconName: 'note',
    buttonsConfig: [
      {
        textKey: 'create_subject',
        click: () => this.router.navigate(['student', 'graduation_process', '1', 'subject', 'create'])
      }
    ]
  }

  subjects: Subject[] = [];
  candidatures: CandidaturePartialInfo[] = [];

  constructor(
    private readonly toolbarService: ToolbarService,
    private readonly restService: RestService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(this.toolbarConfig_);
    this.getSubjects(null, null, null, true);
  }

  getSubjects(searchPhrase: string | null, subjectType: string | null, subjectStatus: string | null, availableSubjects: boolean | null) {
    this.restService.getSubjectsForStudent(1, searchPhrase, subjectType, availableSubjects, subjectStatus).subscribe(subs => {
      this.subjects = subs;
      console.log(this.subjects);
    });
  }

  getCandidatures(phrase: string | null, type: string | null, status: string | null) {
    this.restService.getCandidaturesForStudent(1, 1, phrase, type, status).subscribe(cands => {
      this.candidatures = cands;
      console.log(this.candidatures);
    });
  }

  onTabChange(event: MatTabChangeEvent) {
    if (event.index == 0) this.getSubjects(null, null, null, true);
    else if (event.index == 1) this.getSubjects(null, null, null, false);
    else this.getCandidatures(null, null, null);
  }

  onFilterChange(event: FiltersEvent, availableSubjects: boolean) {
    this.getSubjects(event.text_search as string, event.type as string, event.status as string, availableSubjects);
  }

  onCandidatureFilterChange(event: FiltersEvent) {
    this.getCandidatures(event.text_search as string, event.type as string, event.status as string);
  }
}
