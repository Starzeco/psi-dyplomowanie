import { Injectable } from '@angular/core';
import { User } from './model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user?: User

  setUser(user: User) {
    this.user = user
  }

  getUser(): User {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    return this.user!
  }
}
