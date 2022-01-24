import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { GraduationProcess } from './model';

@Injectable({
  providedIn: 'root'
})

export class GraduationProcessService {

  private readonly subject = new Subject<GraduationProcess>();
  private currentGraduationProcessId?: number

  observable = this.subject.asObservable();

  setGraduationProcess(graduationProcess: GraduationProcess) {
    if (!this.currentGraduationProcessId || this.currentGraduationProcessId !== graduationProcess.graduationProcessId) {
      this.currentGraduationProcessId = graduationProcess.graduationProcessId
      this.subject.next(graduationProcess)
    }
  }
}
