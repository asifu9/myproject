import { Component, OnInit } from '@angular/core';

import { Router,ActivatedRoute } from '@angular/router';
import {GroupMemberComponent} from './groupmember.component';
import { Group, GroupMembers } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
declare var jQuery: any;
@Component({
  selector: 'groupusers',
  templateUrl: './groupusers.component.html',
  styleUrls: ['./groupusers.component.scss'],
  providers:[]
})
export class GroupusersComponent implements OnInit {
  groupName:string='';
  group:Group;
  profilePhotoPath:string='';
  path:string='/assets/uploads';
  createdByPath:string='';
  status:string='';
  statusOn:string='';
  joinedUsers:GroupMembers[];
  rejectedUsers:GroupMembers[];
  waitingUsers:GroupMembers[];
  isOwner:boolean=false;
  constructor(private apiService:HttpService, private router: Router ,private rp:ActivatedRoute,private session:SessionService) {
    this.groupName = this.rp.snapshot.params['name'];
   }

  ngOnInit() {
    let data = this.router.url.split("/");
    if (data.length == 4) {
      this.groupName = data[data.length - 2];
    }
    this.fetchGroup();
  }

  approveMe(obj){
    this.apiService.getData("/Group/approve/"+obj.id).subscribe((data) => {
         this.fetchGroup();
     }); 
  }
  rejectMe(){

  }
  fetchGroup(){

    this.apiService.getData("/Group/name/"+this.groupName+"/users").subscribe((data) => { 
          
          this.group= data;
          if(this.group.createdBy==this.session.shareObj['user']){
            this.isOwner=true;
          }
            this.joinedUsers=this.group.statusMembers['Joined'];
            this.rejectedUsers=this.group.statusMembers['Rejected'];
            this.waitingUsers=this.group.statusMembers['Waiting'];
            if(this.joinedUsers!=null){
              this.joinedUsers.forEach((i)=>{
                
                  i.user.photo=this.session.updatePhoto(i.user.photo);
              });
            }
               if(this.rejectedUsers!=null){
              this.rejectedUsers.forEach((i)=>{
                
                  i.user.photo=this.session.updatePhoto(i.user.photo);
              })
            }
               if(this.waitingUsers!=null){
              this.waitingUsers.forEach((i)=>{
                
                  i.user.photo=this.session.updatePhoto(i.user.photo);
              })
            }
          
      
      });
  }

}
