import { Component, Input, Output, EventEmitter } from '@angular/core';

export type GraduationProcess = {
  gradudationProcessId: string,
  initialSemesterName: string,
  finalSemesterName: string,
  facultyShortName: string,
  degreeCourseNameKey: string,
  degreeNameKey: string,
}

@Component({
  selector: 'app-graduation-process-card',
  templateUrl: './graduation-process-card.component.html',
  styleUrls: ['./graduation-process-card.component.scss']
})
export class GraduationProcessCardComponent {

  @Input() graduationProcess!: GraduationProcess

  @Output() graduationProcessSelection = new EventEmitter<GraduationProcess>()
}
