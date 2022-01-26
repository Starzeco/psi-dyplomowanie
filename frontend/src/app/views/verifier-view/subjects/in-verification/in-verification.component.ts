import { Component, OnInit, Input } from '@angular/core';
import { FilterConfig, FiltersEvent } from 'src/app/components/filters/filters.component';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { SubjectType, Verification } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';

const filtersConfig_: FilterConfig[] = [
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

@Component({
  selector: 'app-in-verification',
  templateUrl: './in-verification.component.html',
  styleUrls: ['./in-verification.component.scss']
})
export class InVerificationComponent implements OnInit {

  @Input() verifierId!: number

  readonly filtersConfig = filtersConfig_

  loading = true
  error = true

  verifications: Verification[] = []

  private lastEvent: FiltersEvent = {}
  private readonly toolbarConfig: ToolbarConfig = {
    titleKey: 'verifications',
    iconName: 'check',
    buttonsConfig: [
      {
        textKey: 'accept_all',
        click: () => console.log('sru')
      },
      {
        textKey: 'reject_all',
        click: () => console.log('sru')
      }
    ]
  }

  constructor(
    private readonly restService: RestService,
    private readonly toolbarService: ToolbarService,
  ) { }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(this.toolbarConfig)
    this.fetchVerifications(null, null)
  }

  refetchVerificationsOnFiltersEvent(event: FiltersEvent) {
    this.lastEvent = event
    this.fetchVerifications(
      event.text_search as string | null,
      event.type as SubjectType | null
    )
  }

  refetchVerifications() {
    this.fetchVerifications(
      this.lastEvent.text_search as string | null,
      this.lastEvent.type as SubjectType | null
    )
  }

  fetchVerifications(
    phrase: string | null,
    type: SubjectType | null
  ) {
    this.loading = true
    this.error = false
    this.restService.fetchVerificationsForVerifier(this.verifierId, phrase, null, type).subscribe({
      next: result => {
        this.verifications = result
        this.loading = false
        this.error = false
      },
      error: () => {
        this.loading = false
        this.error = true
      }
    })
  }

}
