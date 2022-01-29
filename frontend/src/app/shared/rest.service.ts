import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { CandidaturePartialInfo, Subject, StaffMember, Student, SubjectType, Verification } from "./model";
import { Dictionary } from "./dictionary";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private readonly http: HttpClient) {
  }

  getSubjectsForStudent(studentId: number,
    searchPhrase: string | null,
    subjectType: string | null,
    availableSubjects: boolean | null,
    subjectStatus: string | null
  ) {
    let params = new HttpParams();
    if (searchPhrase != null) {
      params = params.set('searchPhrase', searchPhrase);
    }
    if (subjectType != null) {
      params = params.set('subjectType', subjectType);
    }
    if (availableSubjects != null) {
      params = params.set('availableSubjects', availableSubjects);
    }
    if (subjectStatus != null) {
      params = params.set('subjectStatus', subjectStatus);
    }
    return this.http.get<Subject[]>(`${environment.apiUrl}/subject/student/${studentId}`, {
      params: params
    });
  }

  getCandidaturesForStudent(studentId: number,
    graduationProcessId: number,
    phrase: string | null,
    type: string | null,
    status: string | null
  ) {
    let params = new HttpParams();
    if (phrase != null) {
      params = params.set('phrase', phrase);
    }
    if (type != null) {
      params = params.set('type', type);
    }
    if (status != null) {
      params = params.set('status', status);
    }
    return this.http.get<CandidaturePartialInfo[]>(`${environment.apiUrl}/student/${studentId}/graduation_process/${graduationProcessId}/candidature`, {
      params: params
    });
  }

  getSubjectById(subjectId: number) {
    return this.http.get<Subject>(`${environment.apiUrl}/subject/${subjectId}`);
  }

  getSupervisorsByGraduationProcessId(graduationProcessId: number) {
    return this.http.get<StaffMember[]>(`${environment.apiUrl}/supervisor`, {
      params: {
        graduation_process_id: graduationProcessId
      }
    });
  }

  getStudentsByIndexes(indexes: string[]) {
    return this.http.get<Student[]>(`${environment.apiUrl}/student`, {
      params: {
        indexes: indexes.join(',')
      }
    });
  }

  createSubject(subject: Dictionary<any>) {
    return this.http.post(`${environment.apiUrl}/subject`, subject);
  }

  fetchVerificationsForVerifier(
    verifierId: number,
    phrase: string | null,
    verified: boolean | null,
    type: SubjectType | null,
  ) {
    const params = new HttpParams()
    const p1 = phrase ? params.set('phrase', phrase) : params
    const p2 = verified ? p1.set('verified', verified) : p1
    const p3 = type ? p2.set('type', type) : p2

    return this.http.get<Verification[]>(`${environment.apiUrl}/verifier/${verifierId}/verifications`, {
      params: p3
    });
  }

  candidate(candidature: Dictionary<any>) {
    return this.http.post(`${environment.apiUrl}/subject/candidature`, candidature);
  }

  updateProposition(decision: boolean, propositionId: number) {
    return this.http.put(`${environment.apiUrl}/propositions/${propositionId}`, decision);
  }

  acceptInitiator(subjectId: number) {
    return this.http.put(`${environment.apiUrl}/subject/status/accept-initiator/${subjectId}`, null);
  }

  reject(subjectId: number) {
    return this.http.put(`${environment.apiUrl}/subject/status/reject/${subjectId}`, null);
  }
}
