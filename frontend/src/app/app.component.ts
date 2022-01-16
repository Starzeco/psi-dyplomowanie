import {Component, OnInit} from '@angular/core';
import {TestService} from "./test.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(private readonly test: TestService) {
  }

  ngOnInit(): void {
    this.test.test().subscribe(y => {
      console.log(y)
    })
  }
}
