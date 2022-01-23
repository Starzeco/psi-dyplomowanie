import { Component } from '@angular/core';
import { Router } from "@angular/router";


@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent {

  // private buttonsConfig: ButtonConfig[] = [
  //   {
  //     textKey: 'create_subject',
  //     click: () => this.router.navigate(['student', 'subject'])
  //   }
  // ];

  constructor(private readonly router: Router) { }

}
