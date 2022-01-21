import { Component, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, Subscription } from 'rxjs';
import { DefaultFilterComponent, DefaultFilterConfig, DefaultFilterEvent } from '../filters.core.';


export type SelectFilterConfig = DefaultFilterConfig & {
  type: 'SELECT'
  options: {
    nameKey: string,
    value: string
  }[]
}

@Component({
  selector: 'app-select-filter',
  templateUrl: './select-filter.component.html',
  styleUrls: ['./select-filter.component.scss']
})
export class SelectFilterComponent extends DefaultFilterComponent implements OnInit, OnDestroy {

  @Input() config!: SelectFilterConfig;
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
