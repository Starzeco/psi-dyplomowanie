import { Component, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, Subscription } from 'rxjs';
import { DefaultFilterConfig } from '../filters.component';


export type TextSearchFilterConfig = DefaultFilterConfig & {
  type: 'TEXT_SEARCH'
}

@Component({
  selector: 'app-text-search-filter',
  templateUrl: './text-search-filter.component.html',
  styleUrls: ['./text-search-filter.component.scss']
})
export class TextSearchFilterComponent implements OnInit, OnDestroy {

  @Input() config!: TextSearchFilterConfig;
  @Output() filterChange = new EventEmitter<string>();

  readonly formControl = new FormControl({ value: '' })

  private subscription!: Subscription;

  ngOnInit(): void {
    this.formControl.setValue(this.config.defaultValue)
    this.subscription = this.formControl.valueChanges.pipe(
      debounceTime(300),
    ).subscribe((value: string) => {
      this.filterChange.emit(value);
    });
  }

  ngOnDestroy(): void {
    this.subscription && this.subscription.unsubscribe();
  }

}

