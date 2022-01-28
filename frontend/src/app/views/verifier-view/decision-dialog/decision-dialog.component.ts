import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export type DecisionDialogRole = 'accept' | 'reject'

export type DecisionDialogConfig = {
  titleKey: string
  role: DecisionDialogRole
}

export type DecisionDialogResult = {
  justification: string,
  role: DecisionDialogRole

}

@Component({
  selector: 'app-decision-dialog',
  templateUrl: './decision-dialog.component.html',
  styleUrls: ['./decision-dialog.component.scss']
})
export class DecisionDialogComponent {

  // eslint-disable-next-line @typescript-eslint/unbound-method
  justificationControl = new FormControl('', [Validators.required]);

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public config: DecisionDialogConfig,
    public dialogRef: MatDialogRef<DecisionDialogComponent>,
  ) { }

  close() {
    this.dialogRef.close()
  }

  apply() {
    if (this.justificationControl.valid) {
      const result: DecisionDialogResult = {
        justification: this.justificationControl.value as string,
        role: this.config.role
      }
      this.dialogRef.close(result)
    }
  }


}
