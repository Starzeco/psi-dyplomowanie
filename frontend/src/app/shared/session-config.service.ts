import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {UserSessionConfig} from "../components/sidenav/sidenav.component";

@Injectable({
  providedIn: 'root'
})
export class SessionConfigService {

  private sessionConfigSubject = new Subject<UserSessionConfig>();
  sessionConfigObservable = this.sessionConfigSubject.asObservable();

  updateSessionConfig(sessionConfig: UserSessionConfig) {
    this.sessionConfigSubject.next(sessionConfig);
  }
}
