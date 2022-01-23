import { Injectable } from '@angular/core';
import { Subject } from "rxjs";

export type UserSessionConfig = {
  userFullName: string,
  initialSemesterName: string,
  finalSemesterName: string,
  facultyShortName: string,
  degreeCourseNameKey: string,
  degreeNameKey: string,
}


@Injectable({
  providedIn: 'root'
})
export class UserSessionConfigService {

  private sessionConfigSubject = new Subject<UserSessionConfig>();
  sessionConfigObservable = this.sessionConfigSubject.asObservable();

  updateSessionConfig(sessionConfig: UserSessionConfig) {
    this.sessionConfigSubject.next(sessionConfig);
  }
}
