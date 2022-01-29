import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FilterConfig, FiltersEvent } from 'src/app/components/filters/filters.component';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { SubjectType, Verification } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';

const toolbarConfig_: ToolbarConfig = {
  titleKey: 'verifications',
  iconName: 'check',
  buttonsConfig: []
}

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
  selector: 'app-rejected',
  templateUrl: './rejected.component.html',
  styleUrls: ['./rejected.component.scss']
})
export class RejectedComponent implements OnInit {
  @Input() verifierId!: number

  readonly filtersConfig = filtersConfig_
  lastEvent: FiltersEvent = {}

  loading = true
  error = true

  verifications: Verification[] = []

  constructor(
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly restService: RestService,
    private readonly toolbarService: ToolbarService,
  ) { }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(toolbarConfig_)
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
    this.restService.fetchAllVerificationsForVerifier(this.verifierId, phrase, false, type).subscribe({
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


  redirectToDetails(verification: Verification) {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    const verifierId = this.route.snapshot.paramMap.get('verifier_id')!;
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    const graduationProcessId = this.route.snapshot.paramMap.get('graduation_process_id')!;

    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    this.router.navigate(['verifier', verifierId, 'graduation_process', graduationProcessId, 'verifications', verification.verificationId])
  }

}
