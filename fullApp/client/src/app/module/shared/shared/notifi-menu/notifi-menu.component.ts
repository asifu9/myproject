import { Component, OnInit, Input } from '@angular/core';
import { SessionService } from "../SessionService";

@Component({
  selector: 'app-notifi-menu',
  templateUrl: './notifi-menu.component.html',
  styleUrls: ['./notifi-menu.component.scss']
})
export class NotifiMenuComponent implements OnInit {

  private _data:any;
  notifications:Notification[];
  get data():any{
    return this._data;
  }
  @Input()
  set data(data:any){
    this._data=data;
    if(this.data && this.data.notification){
      this.notifications=this.data.notification;
    }
  }
  constructor(private session:SessionService) { }

  ngOnInit() {
  }

  getUserName(i){
    if(i==this.session.shareObj['user']){
      return this.session.currentUser.name;
    }else if(this.data && this.data.users && this.data.users[i]){
      return this.data.users[i].name;
    }
  }

}
