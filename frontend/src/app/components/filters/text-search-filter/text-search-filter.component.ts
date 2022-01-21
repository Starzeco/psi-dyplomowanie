import { Component, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, Subscription } from 'rxjs';
import { DefaultFilterComponent, DefaultFilterConfig, DefaultFilterEvent } from '../filters.core.';


export type TextSearchFilterConfig = DefaultFilterConfig & {
  type: 'TEXT_SEARCH'
}

@Component({
  selector: 'app-text-search-filter',
  templateUrl: './text-search-filter.component.html',
  styleUrls: ['./text-search-filter.component.scss']
})
export class TextSearchFilterComponent extends DefaultFilterComponent implements OnInit, OnDestroy {

  @Input() config!: TextSearchFilterConfig;
  @Output() filterChange = new EventEmitter<DefaultFilterEvent>();

  readonly formControl = new FormControl()

  private subscription!: Subscription;

  ngOnInit(): void {
    this.formControl.setValue(this.config.defaultValue)
    this.subscription = this.formControl.valueChanges.pipe(
      debounceTime(300),
    ).subscribe((value: string) => {
      this.filterChange.emit({
        source: this.config.name,
        value,
      });
    });
  }

  ngOnDestroy(): void {
    this.subscription && this.subscription.unsubscribe();
  }

  clear(): void {
    if (this.formControl.value)
      this.formControl.reset()
  }

}

