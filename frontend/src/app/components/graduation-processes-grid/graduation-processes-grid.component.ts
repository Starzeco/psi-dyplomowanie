import { Component, Output, EventEmitter, Input } from '@angular/core';
import { GraduationProcess } from './graduation-process-card/graduation-process-card.component';


@Component({
  selector: 'app-graduation-processes-grid',
  templateUrl: './graduation-processes-grid.component.html',
  styleUrls: ['./graduation-processes-grid.component.scss']
})
export class GraduationProcessesGridComponent {

  @Input() graduationProcesses!: GraduationProcess[]

  @Output() graduationProcessSelection = new EventEmitter<GraduationProcess>()

}
