import { Component, OnInit } from '@angular/core';
import {ToolbarButtonService} from "../../../shared/toolbar-button.service";
import {ToolbarTitleKeyService} from "../../../shared/toolbar-title-key.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-graduation-process',
  templateUrl: './graduation-process.component.html',
  styleUrls: ['./graduation-process.component.scss']
})
export class GraduationProcessComponent implements OnInit {

  constructor(private readonly buttonService: ToolbarButtonService,
              private readonly titleService: ToolbarTitleKeyService,
              private readonly router: Router) { }

  ngOnInit(): void {
    this.buttonService.updateButtonsConfig([]);
    this.titleService.updateTitleKey('graduation_process_header');
  }

  onGraduationProcess() {
    this.router.navigate(['student', 'subject'])
  }
}
