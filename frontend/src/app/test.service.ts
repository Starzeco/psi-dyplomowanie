import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TestService {

  constructor(private readonly http: HttpClient) { }

  test() {
    return this.http.post(`${environment.apiUrl}/subject`, {
        topic: "pup1",
        topicInEnglish: "pup2",
        objective: "ss",
        objectiveInEnglish: "ee",
        realizationLanguage: "POLISH",
        realiseresNumber: 2,
        initiatorId: 1,
        proposedRealiserIds: [2,3],
        supervisorId: 1,
        graduationProcessId: 1
      }
    )
  }
}
