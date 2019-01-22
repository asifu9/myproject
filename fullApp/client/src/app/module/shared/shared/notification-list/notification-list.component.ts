import { Component, OnInit } from '@angular/core';
import { HttpService } from "../httpService";
import { SessionService } from "../SessionService";
import { LeaveApplyComment } from "../models";

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent implements OnInit {
  comment:string;
  showTextArea:boolean=false;
  errorMessage:string;
  constructor(private apiService: HttpService, private session: SessionService) { 
    this.path=this.session.shareObj['url'];
  }

  ngOnInit() {
    this.fetchData();
  }
  notification: Notification[];
  res: any;
  path:string="";

  fetchData() {
    this.apiService.getData("/notificationapi/user/" + this.session.shareObj['user'] + "/1").subscribe(
      result => {
        if (result) {
          this.notification = result.notification;
          this.res = result;
        }
      }
    )
  }
  RejectLeaves(idd,notId){
    if(!this.showTextArea){
      this.showTextArea=true;
      return;
    }
     if(!this.comment || this.comment.length==0){
      this.errorMessage = "Comment is mandatory while rejecting leave";
    }else{
      this.errorMessage="";
    }
    let com=new LeaveApplyComment();
    com.commentedBy=this.session.shareObj['user'];
    com.description=this.comment;
    this.apiService.postData("/leaveapply/update/"+idd+"/rejected/"+this.session.shareObj['user']+"/"+notId,com).subscribe(
      result=>{
       this.fetchData();
      }
    )
  }

  updateLeaves(idd,notId){
    console.log(idd);
    this.apiService.getData("/leaveapply/update/"+idd+"/approved/"+this.session.shareObj['user']+"/"+notId).subscribe(
      result=>{
        this.fetchData();
      }
    )
  }

  getUserName(i){
    if(i==this.session.shareObj['user']){
      return this.session.currentUser.name;
    }else if(this.res && this.res.users && this.res.users[i]){
      return this.res.users[i].name;
    }
  }

  getPhotoPath(i){
    if(i==this.session.shareObj['user']){
      if(this.session.currentUser.photo){
        return this.path + "/uploads/"+this.session.currentUser.photo.createdBy+"/"+this.session.currentUser.photo.albumId+"/"+this.session.currentUser.photo.details[0].name;
      }else{
        return this.session.defaultPhotoAlbum;
      }
    }else if(this.res && this.res.users && this.res.users[i]){
      if(this.res.users[i].photo){
        let obj=this.res.users[i].photo;
        return this.path + "/uploads/"+obj.createdBy+"/"+obj.albumId+"/"+obj.details[0].name;
      }
    }
  }



}
