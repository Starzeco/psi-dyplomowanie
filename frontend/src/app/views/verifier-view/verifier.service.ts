import { Injectable } from '@angular/core';
import { Verifier } from 'src/app/shared/model';

@Injectable({
  providedIn: 'root'
})
export class VerifierService {

  private verifier?: Verifier

  setVerifier(verifier: Verifier) {
    this.verifier = verifier
  }

  getVerifier(): Verifier {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    return this.verifier!
  }
}
