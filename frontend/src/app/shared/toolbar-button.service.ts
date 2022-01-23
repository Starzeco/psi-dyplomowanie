import { Injectable } from '@angular/core';
import { ButtonConfig } from "../components/toolbar/toolbar.component";
import { Subject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ToolbarButtonService {

  private buttonsConfigSubject = new Subject<ButtonConfig[]>();
  buttonsConfigObservable = this.buttonsConfigSubject.asObservable();

  updateButtonsConfig(buttonsConfig: ButtonConfig[]) {
    this.buttonsConfigSubject.next(buttonsConfig);
  }
}
