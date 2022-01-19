import { Component, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, Subscription } from 'rxjs';
import { DefaultFilterConfig } from '../filters.component';


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
export class SelectFilterComponent implements OnInit, OnDestroy {
  
  @Input() config!: SelectFilterConfig;
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
