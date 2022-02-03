import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Candidature, Student } from 'src/app/shared/model';

@Component({
  selector: 'app-candidatures-table',
  templateUrl: './candidatures-table.component.html',
  styleUrls: ['./candidatures-table.component.scss']
})
export class CandidaturesTableComponent {

  @Input() candidatures: Candidature[] = [];
  @Output() accept = new EventEmitter<number>();
  @Output() decline = new EventEmitter<number>();

  displayedColumns: string[] = ['applicant', 'co-realisers', 'buttons'];

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
}
