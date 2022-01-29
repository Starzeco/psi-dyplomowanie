import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss']
})
export class SubjectsComponent implements OnInit {

  verifierId!: number

  constructor(private readonly route: ActivatedRoute) { }

  ngOnInit(): void {
    this.verifierId = Number(this.route.snapshot.paramMap.get('verifier_id'))
  }

}
