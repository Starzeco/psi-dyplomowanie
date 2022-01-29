import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { GraduationProcessService } from 'src/app/shared/graduation-process.service';
import { GraduationProcess, GraduationProcessPartialInfo, User, Verifier, VerifierPartialInfo } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';
import { UserService } from 'src/app/shared/user.service';
import { VerifierService } from '../verifier.service';

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

  private user?: User
  private verifiersPartialInfo: VerifierPartialInfo[] = []

  graduationProcesses?: GraduationProcess[]
  loading = true
  error = false

  constructor(
    private readonly router: Router,
    private readonly graduationProcessService: GraduationProcessService,
    private readonly toolbarService: ToolbarService,
    private readonly restService: RestService,
    private readonly userService: UserService,
    private readonly verifierService: VerifierService
  ) { }

  ngOnInit(): void {
    this.user = this.userService.getUser()
    this.fetchGraduationProcesses()
    this.toolbarService.updateToolbarConfig(toolbarConfig_)
  }

  graduationProcessSelection(graduationProcess: GraduationProcess): void {
    const verifier = this.findVerifier(graduationProcess.graduationProcessId)
    this.verifierService.setVerifier(verifier)
    this.graduationProcessService.setGraduationProcess(graduationProcess)
    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    this.router.navigate(['verifier', verifier.verifierId, 'graduation_process', graduationProcess.graduationProcessId, 'verifications'])
  }

  private fetchGraduationProcesses(): void {
    this.loading = true
    this.error = false
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion, @typescript-eslint/no-extra-non-null-assertion
    this.restService.fetchAllVerifiersOfStaffMember(this.user!!.userId).subscribe({
      next: (result) => {
        this.verifiersPartialInfo = result
        this.graduationProcesses = result.map(r => this.mapToGraduationProcess(r.graduationProcessPartialInfo))
        this.loading = false
        this.error = false
      }, error: () => {
        this.loading = false
        this.error = true
      }
    })
  }

  private findVerifier(graduationProcessId: number): Verifier {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    const partialInfo = this.verifiersPartialInfo.find(info => info.graduationProcessPartialInfo.graduationProcessId == graduationProcessId)!
    return this.mapToVerifier(partialInfo)
  }

  private mapToGraduationProcess(partialInfo: GraduationProcessPartialInfo): GraduationProcess {
    return ({
      graduationProcessId: partialInfo.graduationProcessId,
      initialSemesterName: partialInfo.initialSemester,
      finalSemesterName: partialInfo.initialSemester,
      facultyShortName: partialInfo.facultyShortName,
      degreeCourseNameKey: partialInfo.degreeCourseName,
      degreeNameKey: partialInfo.degree,
    })
  }

  private mapToVerifier(partialInfo: VerifierPartialInfo): Verifier {
    return ({
      verifierId: partialInfo.verifierId,
      name: partialInfo.name,
      verificationsDeadline: partialInfo.verificationsDeadline,
    })
  }

}
