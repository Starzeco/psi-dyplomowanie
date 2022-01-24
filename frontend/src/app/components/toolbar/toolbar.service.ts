import { Injectable } from '@angular/core';
import { Subject } from "rxjs";
import { ToolbarConfig } from './toolbar.component';


@Injectable({
  providedIn: 'root'
})
export class ToolbarService {

  private subject = new Subject<ToolbarConfig>();
  observable = this.subject.asObservable();

  updateToolbarConfig(toolbarConfig: ToolbarConfig) {
    this.subject.next(toolbarConfig);
  }
}
