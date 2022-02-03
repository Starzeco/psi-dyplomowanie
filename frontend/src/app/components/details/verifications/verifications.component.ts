import { Component, Input } from '@angular/core';
import { Verification } from 'src/app/shared/model';

@Component({
  selector: 'app-verifications',
  templateUrl: './verifications.component.html',
  styleUrls: ['./verifications.component.scss']
})
export class VerificationsComponent {

  @Input() verification!: Verification

  getDecisionTranslationKey(): string {
    const verified = this.verification.verified
    if (verified === true) {
      return 'accepted'
    } else if (verified === false) {
      return 'rejected'
    } else {
      return ''
    }
  }

}
