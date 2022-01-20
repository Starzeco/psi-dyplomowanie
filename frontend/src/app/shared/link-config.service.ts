import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {LinkConfig} from "../components/sidenav/sidenav.component";

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
