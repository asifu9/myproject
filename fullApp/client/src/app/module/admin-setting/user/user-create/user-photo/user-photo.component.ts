import { Component, OnInit } from '@angular/core';
import { AdminService } from "../../../admin.service";
import { Router } from "@angular/router";
import { SessionService } from "../../../../shared/shared/SessionService";
import { User } from "../../../../shared/shared/models";
import { HttpService } from "../../../../shared/shared/httpService";
import { NotificationServiceService } from "../../../../shared/shared/notification-service.service";

@Component({
  selector: 'app-user-photo',
  templateUrl: './user-photo.component.html',
  styleUrls: ['./user-photo.component.scss']
})
export class UserPhotoComponent implements OnInit {

  user: User;
  userId: string;
  constructor(private notificationServiceService:NotificationServiceService,public httpService: HttpService, private router: Router, private session: SessionService) { }

  ngOnInit() {
    let data = this.router.url.split("/");

    console.log(data);
    if (data.length > 0) {
      this.userId = data[data.length - 1];
      this.httpService.getData("/User/" + data[data.length - 1])
        .subscribe(result => {
          this.user = result;
        })
    }
  }
  onChangeupload(rrr){
    this.notificationServiceService.showSaveDailog("userphoto","update",false);

    if(rrr){
         this.httpService.getData("/User/" + this.userId)
        .subscribe(result => {
          this.user = result;
          if(this.user.id == this.session.shareObj['user']){
            this.session.currentUser=this.user;
            this.session.setCurrentUser(this.user);
          }
        })
    }
  }

}
