import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Subject} from "./model";

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
                        subjectStatus: string | null) {
    let params = new HttpParams();
    if(searchPhrase != null) {
      params = params.set('searchPhrase', searchPhrase);
    }
    if(subjectType != null) {
      params = params.set('subjectType', subjectType);
    }
    if(availableSubjects != null) {
      params = params.set('availableSubjects', availableSubjects);
    }
    if(subjectStatus != null) {
      params = params.set('subjectStatus', subjectStatus);
    }
    return this.http.get<Subject[]>(`${environment.apiUrl}/subject/student/${studentId}`, {
      params: params
    });
  }
}
