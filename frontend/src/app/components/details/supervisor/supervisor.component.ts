import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StaffMember } from 'src/app/shared/model';

@Component({
  selector: 'app-supervisor',
  templateUrl: './supervisor.component.html',
  styleUrls: ['./supervisor.component.scss']
})
export class SupervisorComponent {

  @Input() supervisor!: StaffMember

  constructor(
    private readonly translateService: TranslateService
  ) {

  }

  getSupervisorFullName() {
    const title = this.translateService.instant(this.supervisor.title) as string
    return `${title} ${this.supervisor.name} ${this.supervisor.surname}`;
  }

}
