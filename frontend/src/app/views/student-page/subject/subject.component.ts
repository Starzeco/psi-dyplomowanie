import { Component, OnInit } from '@angular/core';
import {ToolbarButtonService} from "../../../shared/toolbar-button.service";
import {ToolbarTitleKeyService} from "../../../shared/toolbar-title-key.service";
import {ButtonConfig} from "../../../components/toolbar/toolbar.component";
import {Router} from "@angular/router";


@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent implements OnInit {

  private buttonsConfig: ButtonConfig[] = [
    {
      textKey: 'create_subject',
      click: () => this.router.navigate(['student', 'subject'])
    }
  ];

  constructor(private readonly buttonService: ToolbarButtonService,
              private readonly titleService: ToolbarTitleKeyService,
              private readonly router: Router) { }

  ngOnInit(): void {
    this.buttonService.updateButtonsConfig(this.buttonsConfig);
    this.titleService.updateTitleKey('subjects_header');
  }

}
