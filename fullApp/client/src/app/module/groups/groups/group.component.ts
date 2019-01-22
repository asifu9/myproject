import { Component, OnInit,Input } from '@angular/core';
import {Http,RequestOptions} from '@angular/http';
import { Router,ActivatedRoute } from '@angular/router';
import { Group } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";

@Component({
  selector: 'group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss']
})
export class GroupComponent implements OnInit {

@Input()
  group:Group;
   private path:string="";
  constructor(private router: Router,public session:SessionService ) {

   }
    
  
 createdByPath:string='';
  profilePhotoPath:string='';
  showGroup(){
      this.router.navigate(['group/'+this.group.groupUniqueName]);
  }
  ngOnInit() {
      if(this.group!=null){
          this.path=this.session.shareObj['url']+"/uploads";
        if(this.group.profilePhoto!=null){
            if(this.group.profilePhoto.details.length>=3){
                this.profilePhotoPath=this.path+'/'+this.group.profilePhoto.createdBy+'/'+this.group.profilePhoto.albumId+'/'+this.group.profilePhoto.details[2].name;
            }else if(this.group.profilePhoto.details.length>=2){
                this.profilePhotoPath=this.path+'/'+this.group.profilePhoto.createdBy+'/'+this.group.profilePhoto.albumId+'/'+this.group.profilePhoto.details[1].name;
            }else{
              this.profilePhotoPath=this.path+'/'+this.group.profilePhoto.createdBy+'/'+this.group.profilePhoto.albumId+'/'+this.group.profilePhoto.details[0].name;
            }
            
        }else{
           this.profilePhotoPath=this.session.defaultPhotoAlbum;
        }
        //ok cool
        if(this.group.createdByUser!=null && this.group.createdByUser.photo!=null){
            if(this.group.createdByUser.photo.details.length>=3){
                this.createdByPath=this.path+'/'+this.group.createdByUser.photo.createdBy+'/'+this.group.createdByUser.photo.albumId+'/'+this.group.createdByUser.photo.details[2].name;
            }else if(this.group.profilePhoto.details.length>=2){
                this.createdByPath=this.path+'/'+this.group.createdByUser.photo.createdBy+'/'+this.group.createdByUser.photo.albumId+'/'+this.group.createdByUser.photo.details[1].name;
            }else{
              this.createdByPath=this.path+'/'+this.group.createdByUser.photo.createdBy+'/'+this.group.createdByUser.photo.albumId+'/'+this.group.createdByUser.photo.details[0].name;
            }
        }
          
      }
  }

}
