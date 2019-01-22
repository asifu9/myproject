import { Component, OnInit ,Input} from '@angular/core';
import {PhotoCommentComponent} from './photocomment.component';
import { CBlock, Feed, User } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { HttpEventType } from "@angular/common/http";
@Component({
  selector: 'photocomments',
  templateUrl: './photocomments.component.html',
  styleUrls: ['./photocomments.component.scss'],
  providers:[]
})
export class PhotoCommentsComponent implements OnInit {

 postComment:CBlock=new CBlock();
  @Input()
   post:Feed;
  @Input()
   user:User;
   photo1:string;
private path:string='/assets/uploads';
  constructor(private apiService:HttpService,private session:SessionService) { 
      this.postComment=new CBlock();
      this.postComment.comment='';
  }

  ngOnInit() {
    if(this.user!=null && this.user.photoId!=null){
      this.photo1=this.path+'/'+this.user.id+'/'+this.user.id+'/'+this.user.photo.details[0].name;
    }else{
      console.log("ok in here good");
        this.apiService.getData("/User/"+this.session.shareObj['user']).subscribe((data) => { 
                  this.user=data;
                   //this.user.photo=this.session.updatePhotoIcon(this.user.photo);
                  this.photo1=this.user.photo.displayPath;
            });
    }
  }
  postCommentCreate(){
    this.postComment.userId=this.session.shareObj['user'];
     this.apiService.postData("/FeedComment/"+this.post.id,this.postComment).subscribe((event) => { 
       if(event.type== HttpEventType.Response){
          let data:any=event.body;
          this.postComment.comment='';
          this.post.commentCount=data;
       }
      
        
  });


  }
}
