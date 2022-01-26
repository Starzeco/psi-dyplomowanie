import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/model';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss']
})
export class SubjectsComponent implements OnInit {

  user!: User

  constructor(private readonly userService: UserService) { }

  ngOnInit(): void {
    this.user = this.userService.getUser()
  }

}
