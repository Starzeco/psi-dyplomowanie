import {Component, OnInit} from '@angular/core';
import {ToolbarButtonService} from "../../../shared/toolbar-button.service";
import {ToolbarTitleKeyService} from "../../../shared/toolbar-title-key.service";
import {ButtonConfig} from "../../../components/toolbar/toolbar.component";
import {Router} from "@angular/router";
import {RestService} from "../../../shared/rest.service";
import {Subject} from "../../../shared/model";
import {MatTabChangeEvent} from "@angular/material/tabs";
import {FilterConfig, FiltersEvent} from "../../../components/filters/filters.component";


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


@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent implements OnInit {

  filterAvailableConf = filtersConfigAvailable_;
  filterAppliedConf = filtersConfigApplied_;

  private buttonsConfig: ButtonConfig[] = [
    {
      textKey: 'create_subject',
      click: () => this.router.navigate(['student', 'subject'])
    }
  ];

  subjects: Subject[] = [];

  constructor(private readonly buttonService: ToolbarButtonService,
              private readonly titleService: ToolbarTitleKeyService,
              private readonly restService: RestService,
              private readonly router: Router) {
  }

  ngOnInit(): void {
    this.buttonService.updateButtonsConfig(this.buttonsConfig);
    this.titleService.updateTitleKey('subjects_header');
    this.getSubjects(null, null, null, true);
  }

  getSubjects(searchPhrase: string | null, subjectType: string | null, subjectStatus: string | null, availableSubjects: boolean | null) {
    this.restService.getSubjectsForStudent(1, searchPhrase, subjectType, availableSubjects, subjectStatus).subscribe(subs => {
      this.subjects = subs;
      console.log(this.subjects);
    });
  }

  onTabChange(event: MatTabChangeEvent) {
    if(event.index == 0) this.getSubjects(null, null, null, true);
    else if(event.index == 1) this.getSubjects(null, null, null, false);
  }

  onFilterChange(event: FiltersEvent, availableSubjects: boolean) {
    this.getSubjects(event.text_search as string, event.type as string, event.status as string, availableSubjects);
  }
}
