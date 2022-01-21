import { Component, Input } from '@angular/core';

export type ButtonConfig = {
  textKey: string,
  click: () => void
  disabled?: boolean,
}

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent {
  @Input() titleKey!: string;
  @Input() iconName!: string;
  @Input() buttonsConfig: ButtonConfig[] = []
}
