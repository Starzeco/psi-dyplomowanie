import { Component } from '@angular/core';
import { FilterConfig, FiltersEvent } from 'src/app/components/filters/filters.component';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H' },
  { position: 2, name: 'Helium', weight: 4.0026, symbol: 'He' },
  { position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li' },
  { position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be' },
  { position: 5, name: 'Boron', weight: 10.811, symbol: 'B' },
  { position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C' },
  { position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N' },
  { position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O' },
  { position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F' },
  { position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne' },
];

const filtersConfig_: FilterConfig[] = [
  {
    name: 'text_search',
    labelKey: 'search_by_topic_and_supervison',
    type: 'TEXT_SEARCH'
  },
  {
    name: 'status',
    labelKey: 'status',
    options: [
      { nameKey: 'status_1', value: 'status_1' },
      { nameKey: 'status_2', value: 'status_2' },
      { nameKey: 'status_3', value: 'status_3' }
    ],
    type: 'SELECT',
  },
  {
    name: 'type',
    labelKey: 'type',
    options: [
      { nameKey: 'type_1', value: 'type_1' },
      { nameKey: 'type_2', value: 'type_2' },
      { nameKey: 'type_3', value: 'type_3' }
    ],
    type: 'SELECT',
  }
]

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent {

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
  dataSource = ELEMENT_DATA;
  filtersConfig = filtersConfig_

  logFiltersChange(event: FiltersEvent) {
    console.log(JSON.stringify(event))
  }
}
