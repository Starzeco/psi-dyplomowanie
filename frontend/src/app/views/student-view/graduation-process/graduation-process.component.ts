import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-graduation-process',
  templateUrl: './graduation-process.component.html',
  styleUrls: ['./graduation-process.component.scss']
})
export class GraduationProcessComponent {

  constructor( private readonly router: Router) { }

  onGraduationProcess() {
    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    this.router.navigate(['student', 'subject'])
  }
}
