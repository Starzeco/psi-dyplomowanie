import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ToolbarButtonService} from "../../shared/toolbar-button.service";
import {ToolbarTitleKeyService} from "../../shared/toolbar-title-key.service";
import {Subscription} from "rxjs";

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
export class ToolbarComponent implements OnInit, OnDestroy {
  titleKey!: string;
  buttonsConfig: ButtonConfig[] = []
  @Input() iconName!: string;

  titleSubscription!: Subscription;
  buttonSubscription!: Subscription;

  constructor(private readonly buttonService: ToolbarButtonService,
              private readonly titleService: ToolbarTitleKeyService) {}

  ngOnInit(): void {
    this.titleSubscription = this.titleService.titleKeyObservable.subscribe(titleKey => this.titleKey = titleKey);
    this.buttonSubscription = this.buttonService.buttonsConfigObservable.subscribe(buttonsConfig => this.buttonsConfig = buttonsConfig);
  }

  ngOnDestroy(): void {
    this.titleSubscription.unsubscribe();
    this.buttonSubscription.unsubscribe();
  }
}
