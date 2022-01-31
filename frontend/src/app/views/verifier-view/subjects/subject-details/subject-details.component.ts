/* eslint-disable @typescript-eslint/unbound-method */
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';
import { StaffMember, Verification, Verifier } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';
import { DecisionDialogComponent, DecisionDialogResult } from '../../decision-dialog/decision-dialog.component';
import { VerifierService } from '../../verifier.service';

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

  private verifier!: Verifier

  constructor(
    private readonly route: ActivatedRoute,
    private readonly toolbarService: ToolbarService,
    private readonly restService: RestService,
    private readonly translateService: TranslateService,
    private readonly matDialog: MatDialog,
    private readonly verifierService: VerifierService
  ) { }

  ngOnInit(): void {
    this.verifier = this.verifierService.getVerifier()
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
        this.prepareNowToolbarConfigBasedOnResult(result)
        this.loading = false;
        this.error = false;
      },
      error: () => {
        this.loading = false;
        this.error = true
      }
    })
  }

  private prepareNowToolbarConfigBasedOnResult(result: Verification) {
    const toolbarConfig = {
      ...toolbarConfig_,
      buttonsConfig: result.verified === true || result.verified === false ? [] :
        [
          {
            textKey: 'accept',
            click: () => this.openAcceptDialog()
          },
          {
            textKey: 'reject',
            click: () => this.openRejectDialog()
          }
        ]
    };

    this.toolbarService.updateToolbarConfig(toolbarConfig)
  }

  openAcceptDialog() {
    const dialogRef = this.matDialog.open(DecisionDialogComponent, { data: { titleKey: 'accept_all' } })
    dialogRef.afterClosed().subscribe((result?: DecisionDialogResult) => {
      if (result && this.verification) {
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        this.restService.verifyVerification(
          this.verifier.verifierId,
          this.verification.verificationId,
          { decision: true, justification: result.justification })
          .subscribe(() => this.fetchVerification())
      }
    })
  }

  openRejectDialog() {
    const dialogRef = this.matDialog.open(DecisionDialogComponent, { data: { titleKey: 'reject_all' } })
    dialogRef.afterClosed().subscribe((result?: DecisionDialogResult) => {
      if (result && this.verification) {
        this.restService.verifyVerification(
          this.verifier.verifierId,
          this.verification.verificationId,
          { decision: false, justification: result.justification })
          .subscribe(() => this.fetchVerification())
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