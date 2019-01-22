import { Component, OnInit ,Input} from '@angular/core';
import {CommentComponent} from './comment.component';
import { CBlock, User, Feed } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { HttpEventType } from "@angular/common/http";
@Component({
  selector: 'comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss'],
  providers:[CommentComponent]
})
export class CommentsComponent implements OnInit {

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
       this.photo1=this.session.updatePhotoIcon(this.user,'user');
    }else{
      console.log("comments ok cool");
        this.photo1="/assets/static/user.png";
    }
  }
  isCanComment(){
    if(this.session.isActivityExists('feed','feedcreatecomment')){
      if(this.post.createdBy==this.session.shareObj['user']){
        return true;
      }else if(this.post.feedSetting && this.post.feedSetting['otherscancomment']){
        return true;
      }
    }
    return false;
  }
  postCommentCreate(){
    this.postComment.userId=this.session.shareObj['user'];
     this.apiService.postData("/FeedComment/"+this.post.id,this.postComment).subscribe((event) => { 
       if(event){
         let data:any=event;
      this.postComment.comment='';
      this.post.commentCountObj=data;
       }
  });


  }
}
