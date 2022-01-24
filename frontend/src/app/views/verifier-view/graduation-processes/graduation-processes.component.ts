import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { GraduationProcessService } from 'src/app/shared/graduation-process.service';
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

const toolbarConfig_: ToolbarConfig = {
  titleKey: 'graduation_processes',
  iconName: 'view_module',
  buttonsConfig: []
}

@Component({
  selector: 'app-graduation-processes',
  templateUrl: './graduation-processes.component.html',
  styleUrls: ['./graduation-processes.component.scss']
})
export class GraduationProcessesComponent implements OnInit {

  graduationProcesses?: GraduationProcess[]
  loading = true
  error = false

  constructor(
    private readonly router: Router,
    private readonly graduationProcessService: GraduationProcessService,
    private readonly toolbarService: ToolbarService
  ) { }

  ngOnInit(): void {
    this.getGraduationProcesses()
    this.toolbarService.updateToolbarConfig(toolbarConfig_)
  }

  graduationProcessSelection(graduationProcess: GraduationProcess): void {
    this.graduationProcessService.setGraduationProcess(graduationProcess)
    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    this.router.navigate(['verifier', 'graduation_process', `${graduationProcess.graduationProcessId}`, 'subject'])
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
