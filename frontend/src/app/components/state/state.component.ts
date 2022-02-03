import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-state',
  templateUrl: './state.component.html',
  styleUrls: ['./state.component.scss']
})
export class StateComponent {

  @Input() loading!: boolean;
  @Input() error!: boolean;
  @Input() empty?: boolean;

  @Output() tryAgain = new EventEmitter<void>()
  @Output() refetch = new EventEmitter<void>()

}
