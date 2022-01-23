import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GraduationProcessServiceService } from 'src/app/shared/graduation-process-service.service';
import { GraduationProcess } from 'src/app/shared/model';

const graduationProcesses_: GraduationProcess[] = [
  {
    graduationProcessId: 1,
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "applied_computer_science",
    degreeNameKey: "master"
  },
  {
    graduationProcessId: 1,
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "applied_computer_science",
    degreeNameKey: "master"
  },
  {
    graduationProcessId: 1,
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "applied_computer_science",
    degreeNameKey: "master"
  },
  {
    graduationProcessId: 1,
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "applied_computer_science",
    degreeNameKey: "master"
  },
  {
    graduationProcessId: 1,
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "applied_computer_science",
    degreeNameKey: "master"
  },
]

@Component({
  selector: 'app-supervisor-graduation-processes',
  templateUrl: './supervisor-graduation-processes.component.html',
  styleUrls: ['./supervisor-graduation-processes.component.scss']
})
export class SupervisorGraduationProcessesComponent implements OnInit {

  graduationProcesses?: GraduationProcess[]
  loading = true
  error = false

  constructor(
    private readonly graduationProcessServiceService: GraduationProcessServiceService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
    this.getGraduationProcesses()
  }

  graduationProcessSelection(graduationProcess: GraduationProcess): void {
    this.graduationProcessServiceService.setGraduationProcess(graduationProcess)
    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    this.router.navigate([`graduation_process/${graduationProcess.graduationProcessId}`])
  }

  private getGraduationProcesses(): void {
    this.loading = true
    this.error = false
    setTimeout(() => {
      this.graduationProcesses = graduationProcesses_
      this.loading = false
    }, 1000)
  }

}
