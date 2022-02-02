import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export type CandidateDialogResult = {
  indexes: string[],
}

export type CandidateDialogConfig = {
  titleKey: string
  realizersNumber: number
}

@Component({
  selector: 'app-candidate-dialog',
  templateUrl: './candidate-dialog.component.html',
  styleUrls: ['./candidate-dialog.component.scss']
})
export class CandidateDialogComponent implements OnInit {

  // eslint-disable-next-line @typescript-eslint/unbound-method
  controls: FormControl[] = []

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public config: CandidateDialogConfig,
    public dialogRef: MatDialogRef<CandidateDialogComponent>,
  ) { }

  ngOnInit(): void {
    this.controls = Array.from({ length: this.config.realizersNumber - 1 },
      // eslint-disable-next-line @typescript-eslint/unbound-method
      () => new FormControl('', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(6), Validators.maxLength(6)])
    )
  }

  close() {
    this.dialogRef.close()
  }

  apply() {
    if (this.controls.every(c => c.valid)) {
      const result: CandidateDialogResult = {
        indexes: this.controls.map(c => c.value as string)
      }
      this.dialogRef.close(result)
    }
  }

  prepareLabelTranslationKey(index: number) {
    return `co_realisers_${index + 1}`
  }
}
