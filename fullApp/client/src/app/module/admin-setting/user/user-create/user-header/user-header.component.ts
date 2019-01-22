import { Component, OnInit, Input } from '@angular/core';
import { User } from "../../../../shared/shared/models";

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent implements OnInit {

  constructor() { }
  private _user: User;
  get user(): User {
    return this._user;
  }
  @Input()
  set user(user: User) {
    this._user = user;
    if(!this.userId){
      if(this.user){
        this.userId=this.user.id;
      }
      
    }
  }

  @Input() current:string;

  private _userId: string;
  get userId(): string {
    return this._userId;
  }
  @Input()
  set userId(userId: string) {
    this._userId = userId;

  }

  ngOnInit() {
  }

}
