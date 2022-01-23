import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ToolbarTitleKeyService {

  private titleKeySubject = new BehaviorSubject<string>('university_name');
  titleKeyObservable = this.titleKeySubject.asObservable();

  updateTitleKey(titleKey: string) {
    this.titleKeySubject.next(titleKey);
  }
}
