import { Component, Input } from '@angular/core';
import { SelectFilterConfig } from './select-filter/select-filter.component';
import { TextSearchFilterConfig } from './text-search-filter/text-search-filter.component';

export type DefaultFilterConfig = {
  name: string,
  labelKey: string,
  defaultValue?: string,
}

export type FilterConfig = SelectFilterConfig | TextSearchFilterConfig

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent {

  @Input() filtersConfig!: FilterConfig[]

  asSelectFilterConfig(filterConfig: FilterConfig): SelectFilterConfig {
    return filterConfig as SelectFilterConfig
  }

  asTextSearchFilterConfig(filterConfig: FilterConfig): TextSearchFilterConfig {
    return filterConfig as TextSearchFilterConfig
  }

}
