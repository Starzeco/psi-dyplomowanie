import { Component, Input, Output, EventEmitter, ViewChildren, QueryList, OnInit, OnDestroy } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, Subscription } from 'rxjs';
import { DefaultFilterComponent, DefaultFilterEvent } from './filters.core';

import { SelectFilterConfig } from './select-filter/select-filter.component';
import { TextSearchFilterConfig } from './text-search-filter/text-search-filter.component';


export type FiltersEvent = Record<string, unknown>

export type FilterConfig = SelectFilterConfig | TextSearchFilterConfig


@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent extends DefaultFilterComponent implements OnInit, OnDestroy {

  @ViewChildren("filter") filters!: QueryList<DefaultFilterComponent>;

  @Input() filtersConfig!: FilterConfig[]
  @Output() filtersChange = new EventEmitter<FiltersEvent>()

  readonly formControl = new FormControl()

  private subscription!: Subscription;


  ngOnInit(): void {
    const initialFiltersEvent: FiltersEvent = this.prepareInitialFiltersEvent()
    this.formControl.setValue(initialFiltersEvent)
    this.subscription = this.formControl.valueChanges.pipe(
      debounceTime(50),
    ).subscribe((event: FiltersEvent) => {
      this.filtersChange.emit(event);
    });
  }

  ngOnDestroy(): void {
    this.subscription && this.subscription.unsubscribe();
  }

  onFilterChange(event: DefaultFilterEvent) {
    const tempValue = this.formControl.value as FiltersEvent
    tempValue[event.source] = event.value
    this.formControl.setValue(tempValue)
  }


  clear() {
    this.filters.forEach(filter => filter.clear());
  }

  asSelectFilterConfig(filterConfig: FilterConfig): SelectFilterConfig {
    return filterConfig as SelectFilterConfig
  }

  asTextSearchFilterConfig(filterConfig: FilterConfig): TextSearchFilterConfig {
    return filterConfig as TextSearchFilterConfig
  }

  private prepareInitialFiltersEvent(): FiltersEvent {
    return this.filtersConfig.reduce((acc: FiltersEvent, filter) => (acc[filter.name] = filter.defaultValue, acc), {})
  }



}
