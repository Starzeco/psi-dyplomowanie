import { Component } from '@angular/core';
import { GraduationProcess } from 'src/app/components/graduation-processes-grid/graduation-process-card/graduation-process-card.component';

const graduationProcesses_: GraduationProcess[] = [
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
  selector: 'app-supervisor-view',
  templateUrl: './supervisor-view.component.html',
  styleUrls: ['./supervisor-view.component.scss']
})
export class SupervisorViewComponent {

  graduationProcesses = graduationProcesses_


}
