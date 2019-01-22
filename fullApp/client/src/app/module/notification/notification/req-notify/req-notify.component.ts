import { Component, OnInit } from '@angular/core';
import { SessionService } from "../../../shared/shared/SessionService";
import { FriendRequest } from "../../../shared/shared/models";
import { HttpService } from "../../../shared/shared/httpService";
@Component({
  selector: 'app-req-notify',
  templateUrl: './req-notify.component.html',
  styleUrls: ['./req-notify.component.scss']
})
export class ReqNotifyComponent implements OnInit {

requests:FriendRequest[];
private path: string = '/assets/uploads';
  constructor(private service:HttpService,private session:SessionService) { 
     this.populateNotification();
  }

  ngOnInit() {
   
  }
  populateNotification(){
      let userId=this.session.shareObj['user'];
    this.service.getData("/FriendRequest/requestList/"+userId+"/"  +this.session.shareObj['wall']).subscribe((data) => { 
      this.requests= data;
  });
  }
accept(i){
  this.service.putData("/FriendRequest/UPDATE/ACCEPTED",i).subscribe((data)=>{
      this.populateNotification();
  });
}
  reject(i){
    this.service.putData("/FriendRequest/UPDATE/REJECTED",i).subscribe((data)=>{
      this.populateNotification();
    });
  }
}
