import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

export type LinkConfig = {
  textKey: string,
  href: string,
  iconName?: string,
}


@Injectable({
  providedIn: 'root'
})
export class LinkConfigService {

  private linkConfigSubject = new Subject<LinkConfig[]>();
  linkConfigObservable = this.linkConfigSubject.asObservable();

  updateLinkConfig(linksConfig: LinkConfig[]) {
    this.linkConfigSubject.next(linksConfig);
  }
}
