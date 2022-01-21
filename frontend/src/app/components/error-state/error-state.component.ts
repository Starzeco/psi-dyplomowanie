import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-error-state',
  templateUrl: './error-state.component.html',
  styleUrls: ['./error-state.component.scss']
})
export class ErrorStateComponent {

  @Input() messageKey = 'error_state_message'

  @Output() tryAgain = new EventEmitter<void>()

}
