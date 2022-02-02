import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Candidature, Student } from 'src/app/shared/model';
import { RestService } from 'src/app/shared/rest.service';

@Component({
  selector: 'app-candidatures-table',
  templateUrl: './candidatures-table.component.html',
  styleUrls: ['./candidatures-table.component.scss']
})
export class CandidaturesTableComponent implements OnInit {

  @Input()
  subjectId!: number;
  candidatures: Candidature[] = [];
  displayedColumns: string[] = ['applicant', 'co-realisers', 'buttons'];

  constructor(private readonly router: Router,
              private readonly restService: RestService) { }

  ngOnInit(): void {
    this.getCandidatures();
  }

  private getCandidatures() {
    this.restService.getAllCandidaturesBySubjectId(this.subjectId).subscribe(c => this.candidatures = c);
  }

  getStudentFullName(student: Student) {
    return student.name + ' ' + student.surname + ', ' + student.index;
  }

  getCoRealisers(candidature: Candidature) {
    if(candidature.candidatureAcceptances.length == 0) {
      return '-';
    }
    return candidature.candidatureAcceptances
      .map(s => this.getStudentFullName(s.student))
      .join('\n');
  }

  accept(candidatureId: number) {
    this.restService.decideAboutCandidature(candidatureId, true).subscribe(
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      _ => void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject'])
    );
  }

  decline(candidatureId: number) {
    this.restService.decideAboutCandidature(candidatureId, false).subscribe(
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      _ => void this.router.navigate(['supervisor', 'graduation_process', '1', 'subject'])
    );
  }
}
