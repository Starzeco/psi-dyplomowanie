import { Component, Input } from '@angular/core';

export type Realizer = {
  name: string
  iconName: string
}

export type MainRealizer = {
  labelKey: string
} & Realizer

@Component({
  selector: 'app-co-realisers',
  templateUrl: './co-realisers.component.html',
  styleUrls: ['./co-realisers.component.scss']
})
export class CoRealisersComponent {

  @Input() mainRealizer!: MainRealizer
  @Input() coRealizers: Realizer[] = []

}
