import { Component, OnInit } from '@angular/core';
import { GraduationProcess } from './graduation-process-card/graduation-process-card.component';


// export type GraduationProcess = {
//   gradudationProcessId: string,
//   initialSemesterName: string,
//   finalSemesterName: string,
//   facultyShortName: string,
//   degreeCourseNameKey: string,
//   degreeNameKey: string,
// }

const graduationProcesses: GraduationProcess[] = [
  {
    gradudationProcessId: '1',
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "informatyka_stosowana",
    degreeNameKey: "master"
  },
  {
    gradudationProcessId: '1',
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "informatyka_stosowana",
    degreeNameKey: "master"
  },
  {
    gradudationProcessId: '1',
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "informatyka_stosowana",
    degreeNameKey: "master"
  },
  {
    gradudationProcessId: '1',
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "informatyka_stosowana",
    degreeNameKey: "master"
  },
  {
    gradudationProcessId: '1',
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "informatyka_stosowana",
    degreeNameKey: "master"
  },
]

@Component({
  selector: 'app-graduation-processes-view',
  templateUrl: './graduation-processes-view.component.html',
  styleUrls: ['./graduation-processes-view.component.scss']
})
export class GraduationProcessesViewComponent implements OnInit {

  readonly graduationProcesses = graduationProcesses

  constructor() { }

  ngOnInit(): void {
  }

}
