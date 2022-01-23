import { Component, Input, Output, EventEmitter } from '@angular/core';
import { GraduationProcess } from 'src/app/shared/model';

@Component({
  selector: 'app-graduation-process-card',
  templateUrl: './graduation-process-card.component.html',
  styleUrls: ['./graduation-process-card.component.scss']
})
export class GraduationProcessCardComponent {

  @Input() graduationProcess!: GraduationProcess

  @Output() graduationProcessSelection = new EventEmitter<GraduationProcess>()
}
