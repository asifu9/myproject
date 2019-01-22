import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';
import { GroupMemberComponent } from './groupmember.component';
import { Group } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
declare var jQuery: any;
@Component({
  selector: 'groupleft',
  templateUrl: './groupleft.component.html',
  styleUrls: ['./groupleft.component.scss'],
  providers: [GroupMemberComponent]
})
export class GroupLeftComponent implements OnInit {
  groupName: string = '';
  group: Group;
  profilePhotoPath: string = '';
  path: string ="";
  createdByPath: string = '';
  status: string = '';
  statusOn: string = '';
  liked:boolean=false;
  constructor(private apiService: HttpService, private router: Router, private rp: ActivatedRoute, private session: SessionService) {
    this.groupName = this.rp.snapshot.params['name'];
    this.path= this.session.shareObj['url']+"/uploads";
  }

  ngOnInit() {
    this.fetchGroup();
  }
  joinMe() {
    this.apiService.getData("/Group/join/" + this.groupName + "/" + this.session.shareObj['user']).subscribe((data) => {
      if (data.status == 'Joined') {
        this.status = 'member';
        this.statusOn = data.statusDateStr;
      } else if (data.status == 'Waiting') {
        this.status = 'waiting';
        this.statusOn = data.statusDateStr;
      }
    });

  }

  likeThisGroup(){
    this.apiService.getData( "/GroupLike/"+this.group.id+"/" + this.session.shareObj['user']).subscribe((data) => {
      this.group.likes=data;
      this.group.likesCount=data.count;
      if(data.count!=null && data.count>0){
         let arr=Object.keys(data.likedBy);
           if(-1!==jQuery.inArray(this.session.shareObj['user'], arr)){
              this.liked=true;
           }else{
             this.liked=false;
           }
      }else{
        this.liked=false;
      }
    });
  }
  fetchGroup() {

    this.apiService.getData( "/Group/name/" + this.groupName).subscribe((data) => {

      this.group = data;
      if(data.likes==null){
        this.liked=false;
      }else if(data.likesCount>0){
        let arr=Object.keys(data.likes.likedBy);
        if(-1!==jQuery.inArray(this.session.shareObj['user'], arr)){
              this.liked=true;
           }else{
             this.liked=false;
           }
      }
      if (this.group.members == null || this.group.members.length == 0) {
        if (this.session.shareObj['user'] == this.group.createdBy) {
          this.status = "owner";
        } else {
          this.status = "join";
        }
      } else if (this.group.members.length > 0) {
        if (jQuery.inArray(this.session.shareObj['user'], this.group.members) != -1) {
          // found it
          if (this.session.shareObj['user'] == this.group.createdBy) {
            this.status = "owner";
          } else {
            this.status = "member";
            for (var k in this.group.memberDetails) {
              let data = this.group.memberDetails[k];
              if (k == this.session.shareObj['user']) {
                this.statusOn = data.statusDateStr;
              }
            }
          }
        } else {
          if (this.session.shareObj['user'] == this.group.createdBy) {
            this.status = "owner";
          } else {
            this.status = "join";
          }
        }
      }
      if (this.group != null) {
        if (this.group.profilePhoto != null) {
          if (this.group.profilePhoto.details.length >= 3) {
            this.profilePhotoPath = this.path + '/' + this.group.profilePhoto.createdBy + '/' + this.group.profilePhoto.albumId + '/' + this.group.profilePhoto.details[2].name;
          } else if (this.group.profilePhoto.details.length >= 2) {
            this.profilePhotoPath = this.path + '/' + this.group.profilePhoto.createdBy + '/' + this.group.profilePhoto.albumId + '/' + this.group.profilePhoto.details[1].name;
          } else {
            this.profilePhotoPath = this.path + '/' + this.group.profilePhoto.createdBy + '/' + this.group.profilePhoto.albumId + '/' + this.group.profilePhoto.details[0].name;
          }

        } else {
          this.profilePhotoPath = this.session.defaultPhotoAlbum;
        }
        //ok cool
        if (this.group.createdByUser != null && this.group.createdByUser.photo != null) {
          if (this.group.createdByUser.photo.details.length >= 3) {
            this.createdByPath = this.path + '/' + this.group.createdByUser.photo.createdBy + '/' + this.group.createdByUser.photo.albumId + '/' + this.group.createdByUser.photo.details[2].name;
          } else if (this.group.profilePhoto.details.length >= 2) {
            this.createdByPath = this.path + '/' + this.group.createdByUser.photo.createdBy + '/' + this.group.createdByUser.photo.albumId + '/' + this.group.createdByUser.photo.details[1].name;
          } else {
            this.createdByPath = this.path + '/' + this.group.createdByUser.photo.createdBy + '/' + this.group.createdByUser.photo.albumId + '/' + this.group.createdByUser.photo.details[0].name;
          }
        }

      }
    });
  }

}
