import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-realizers-number',
  templateUrl: './realizers-number.component.html',
  styleUrls: ['./realizers-number.component.scss']
})
export class RealizersNumberComponent {

  @Input() realizersNumber!: number
  
}
