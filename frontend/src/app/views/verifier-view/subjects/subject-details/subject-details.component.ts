/* eslint-disable @typescript-eslint/unbound-method */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { StaffMember, Verification } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';

const toolbarConfig_: ToolbarConfig = {
  titleKey: 'verifications',
  iconName: 'check',
  buttonsConfig: []
}


@Component({
  selector: 'app-subject-details',
  templateUrl: './subject-details.component.html',
  styleUrls: ['./subject-details.component.scss']
})
export class SubjectDetailsComponent implements OnInit {

  loading = true;
  error = false;
  verification?: Verification

  constructor(
    private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly restService: RestService,
    private readonly translateService: TranslateService,
  ) { }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(toolbarConfig_)
    this.fetchVerification();
  }


  private fetchVerification() {
    this.loading = true;
    this.error = false;

    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    const verifierId = this.route.snapshot.paramMap.get('verifier_id')!;
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    const verificationId = this.route.snapshot.paramMap.get('verification_id')!;

    this.restService.fetchVerificationAsVerifier(+verifierId, +verificationId).subscribe({
      next: (result) => {
        this.verification = result
        this.loading = false;
        this.error = false;
      },
      error: () => {
        this.loading = false;
        this.error = true
      }
    })
  }

  getSupervisorFullName(supervisor?: StaffMember) {
    if (supervisor) {
      const title = this.translateService.instant(supervisor.title) as string
      return `${title} ${supervisor.name} ${supervisor.surname}`;
    } else return ""
  }


  getDecisionTranslationKey(verification: Verification): string {
    const verified = verification.verified
    if (verified === true) {
      return 'accepted'
    } else if (verified === false) {
      return 'rejected'
    } else {
      return ''
    }
  }

}