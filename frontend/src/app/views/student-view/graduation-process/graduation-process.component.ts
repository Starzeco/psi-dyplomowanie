import { Component, OnInit } from '@angular/core';
import {GraduationProcess} from "../../../shared/model";
import {ToolbarConfig} from "../../../components/toolbar/toolbar.component";
import {ActivatedRoute, Router} from "@angular/router";
import {GraduationProcessService as GraduationProcessService} from "../../../shared/graduation-process.service";
import {ToolbarService} from "../../../components/toolbar/toolbar.service";


const graduationProcesses_: GraduationProcess[] = [
  {
    graduationProcessId: 1,
    initialSemesterName: "Z21/22",
    finalSemesterName: "L21/22",
    facultyShortName: "W4N",
    degreeCourseNameKey: "applied_computer_science",
    degreeNameKey: "BATCHELOR"
  }
]

const toolbarConfig_: ToolbarConfig = {
  titleKey: 'graduation_processes',
  iconName: 'view_module',
  buttonsConfig: []
}


@Component({
  selector: 'app-graduation-process',
  templateUrl: './graduation-process.component.html',
  styleUrls: ['./graduation-process.component.scss']
})
export class GraduationProcessComponent implements OnInit {

  graduationProcesses?: GraduationProcess[]
  loading = true
  error = false

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly graduationProcessService: GraduationProcessService,
    private readonly toolbarService: ToolbarService
  ) { }

  ngOnInit(): void {
    const z = this.route.snapshot.paramMap.get('graduation_process_id')!
    console.log(`Graduation process in graduations: ${z}`)
    this.getGraduationProcesses()
    this.toolbarService.updateToolbarConfig(toolbarConfig_)
  }

  graduationProcessSelection(graduationProcess: GraduationProcess): void {
    this.graduationProcessService.setGraduationProcess(graduationProcess)
    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    this.router.navigate(['student', 'graduation_process', `${graduationProcess.graduationProcessId}`, 'subject'])
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
