import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { CandidaturePartialInfo, Subject, StaffMember, Student, SubjectType, Verification, Candidature, VerificationDecision, VerifierPartialInfo } from "./model";
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

  getSubjectsForSupervisor(supervisorId: number,
                           searchPhrase: string | null,
                           subjectType: string | null,
                           processingSubjects: boolean,
                           subjectStatus: string | null) {
    let params = new HttpParams();
    if (searchPhrase != null) {
      params = params.set('searchPhrase', searchPhrase);
    }
    if (subjectType != null) {
      params = params.set('subjectType', subjectType);
    }
    if (subjectStatus != null) {
      params = params.set('subjectStatus', subjectStatus);
    }
    params = params.set('processingSubjects', processingSubjects);
    return this.http.get<Subject[]>(`${environment.apiUrl}/supervisor/subject/${supervisorId}`, {
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

  fetchSubjectById(subjectId: number) {
    return this.http.get<Subject>(`${environment.apiUrl}/subject/${subjectId}`);
  }

  fetchCandidatureById(candidatureId: number) {
    return this.http.get<Candidature>(`${environment.apiUrl}/candidature/${candidatureId}`);
  }

  fetchSupervisorsByGraduationProcessId(graduationProcessId: number) {
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

  fetchAllVerificationsForVerifier(
    verifierId: number,
    phrase: string | null,
    verified: boolean | null,
    subjectType: SubjectType | null,
  ) {
    const params = new HttpParams()
    const p1 = phrase ? params.set('phrase', phrase) : params
    const p2 = verified || verified === false ? p1.set('verified', verified) : p1
    const p3 = subjectType ? p2.set('subject_type', subjectType) : p2

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

  acceptAsSupervisor(subjectId: number) {
    return this.http.put(`${environment.apiUrl}/subject/status/accept-supervisor/${subjectId}`, null);
  }

  rejectAsSupervisor(subjectId: number) {
    return this.http.put(`${environment.apiUrl}/subject/status/reject/${subjectId}`, null);
  }

  updateCandidateAcceptance(decision: boolean, candidatureAcceptanceId: number) {
    return this.http.put(`${environment.apiUrl}/subject/candidature_acceptance/${candidatureAcceptanceId}`, decision);
  }

  verifyAllVerifications(
    verifierId: number,
    verificationDecision: VerificationDecision
  ) {
    return this.http.put<Verification[]>(`${environment.apiUrl}/verifier/${verifierId}/verifications`, verificationDecision);
  }

  verifyVerification(
    verifierId: number,
    verificationId: number,
    verificationDecision: VerificationDecision
  ) {
    return this.http.put<Verification[]>(`${environment.apiUrl}/verifier/${verifierId}/verifications/${verificationId}`, verificationDecision);
  }

  fetchAllVerifiersOfStaffMember(staffMemberId: number) {
    return this.http.get<VerifierPartialInfo[]>(`${environment.apiUrl}/verifier/${staffMemberId}`);
  }

  fetchVerificationAsVerifier(verifierId: number, verificationId: number) {
    return this.http.get<Verification>(`${environment.apiUrl}/verifier/${verifierId}/verifications/${verificationId}`);
  }

  updateSubject(subject: Dictionary<any>) {
    return this.http.put(`${environment.apiUrl}/subject/`, subject);
  }

  sendToVerification(subjectId: number) {
    return this.http.put(`${environment.apiUrl}/subject/status/send-verification/${subjectId}`, null);
  }

  fetchAllCandidaturesBySubjectId(subjectId: number) {
    return this.http.get<Candidature[]>(`${environment.apiUrl}/candidature`, {
      params: {
        subject_id: subjectId
      }
    });
  }

  decideAboutCandidature(candidatureId: number, decision: boolean) {
    return this.http.put(`${environment.apiUrl}/subject/candidature/${candidatureId}`, decision);
  }
}
