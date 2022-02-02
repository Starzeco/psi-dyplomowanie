import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ToolbarService } from "../../../../../components/toolbar/toolbar.service";
import { FormBuilder } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { RestService } from "../../../../../shared/rest.service";
import { Candidature, Student } from "../../../../../shared/model";

@Component({
  selector: 'app-candidature-table',
  templateUrl: './candidature-table.component.html',
  styleUrls: ['./candidature-table.component.scss']
})
export class CandidatureTableComponent implements OnInit {

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
      return '--';
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
