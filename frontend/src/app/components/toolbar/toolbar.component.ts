import { Component, OnDestroy, OnInit } from '@angular/core';
import { ToolbarService } from "./toolbar.service";
import { Subscription } from "rxjs";

export type ButtonConfig = {
  textKey: string,
  click: () => void
  disabled?: boolean,
}

export type ToolbarConfig = {
  titleKey: string,
  iconName: string,
  buttonsConfig: ButtonConfig[]
}

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit, OnDestroy {

  config!: ToolbarConfig

  subscription!: Subscription;

  constructor(private readonly service: ToolbarService) { }

  ngOnInit(): void {
    this.subscription = this.service.observable.subscribe(config => this.config = config);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }
}
