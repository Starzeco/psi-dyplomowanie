import { Component, OnInit } from '@angular/core';
import { ToolbarConfig } from 'src/app/components/toolbar/toolbar.component';
import { ToolbarService } from 'src/app/components/toolbar/toolbar.service';

const toolbarConfig_: ToolbarConfig = {
  titleKey: 'subjects',
  iconName: 'note',
  buttonsConfig: []
}

@Component({
  selector: 'app-supervisor-subjects',
  templateUrl: './supervisor-subjects.component.html',
  styleUrls: ['./supervisor-subjects.component.scss']
})
export class SupervisorSubjectsComponent implements OnInit {

  constructor(
    private readonly toolbarService: ToolbarService
  ) { }

  ngOnInit(): void {
    this.toolbarService.updateToolbarConfig(toolbarConfig_)
  }

}
