import { Component, OnInit,Input } from '@angular/core';
import { User, ReportMeta } from "../../../shared/shared/models";
import { NotificationServiceService } from "../../../shared/shared/notification-service.service";
import { HttpService } from "../../../shared/shared/httpService";
import { SessionService } from "../../../shared/shared/SessionService";
import { EventEmitter } from "@angular/core";
import { Output } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Inject } from "@angular/core";

@Component({
  selector: 'app-user-select',
  templateUrl: './user-select.component.html',
  styleUrls: ['./user-select.component.css']
})
export class UserSelectComponent implements OnInit {

  users: User[];
  meta: ReportMeta[] = [];
  //@Output() outputList = new EventEmitter<any>()
  metaSecond: ReportMeta[] = [];
  members: string[] = [];
  allUsers: User[];
  addedUsers: User[];
  constructor(public dialogRef: MatDialogRef<UserSelectComponent>,
    @Inject(MAT_DIALOG_DATA) public type: any,
    private notificationService: NotificationServiceService, private httpService: HttpService,
    private sessionService: SessionService) { }

  ngOnInit() {
    console.log(this.type);
    this.notificationService.showProgressBar("", "");
    this.members=this.type.members;
    if(!this.members){
      this.members=[];
    }
    this.httpService.getData('/User/wall/' + this.sessionService.shareObj['wall']).subscribe(
      data => {
        this.users = data;
        this.notificationService.hideProgressBar();
        this.initData();
      },
      error => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );

  }

  initData() {
    this.meta = [];
    
    this.meta.push(new ReportMeta("photoPath", "photoPath", undefined, "image"));
    this.meta.push(new ReportMeta("name", "name"));
    this.meta.push(new ReportMeta("", "id", undefined, "checkbox"));

    this.metaSecond = []
    this.metaSecond.push(new ReportMeta("", "id", undefined, "checkbox"));
    this.metaSecond.push(new ReportMeta("photoPath", "photoPath", undefined, "image"));
    this.metaSecond.push(new ReportMeta("name", "name"));

    if (this.users) {
      this.allUsers = [];
      for (let uu of this.users) {
        this.allUsers.push(uu);
      }
    }
    this.updatedList(this.members);
  }

  updatedList(data) {
    this.members = data;
    this.addedUsers = [];
    if (this.users && this.users.length > 0) {
      for (let u of this.users) {
        if (this.members.indexOf(u.id) !== -1) {
          this.addedUsers.push(u);
          this.type.members=this.members;
        }
      }
    }
    this.allUsers = [];
    for (let uu of this.users) {
      if (this.members.indexOf(uu.id) === -1) {
        this.allUsers.push(uu);
      }
    }
  }

updatedReverseList(data){
    console.log(data);
    if(this.members){
      if(this.members.indexOf(data)!==-1){
        this.members.splice(this.members.indexOf(data),1);
      }
    }
    this.updatedList(this.members);
}
updateParent(){
  //this.outputList.emit(this.members);
  this.type.members=this.members;
  this.dialogRef.close(this.members);
  this.type.members=this.members;
  
}

}
