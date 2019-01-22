import { Component, OnInit ,Input} from '@angular/core';
import { CBlock, User } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
declare var jQuery: any;
@Component({
  selector: 'comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {

  @Input()
  com:CBlock;
  @Input()
 feedId:string;
 @Input()
 user:User;
photo1:string;
commentedUser:User;
 commented:boolean=false;
private path:string='/assets/uploads';
  constructor(private apiService:HttpService,private session:SessionService) { 
  }

  ngOnInit() {
      this.toggleLikeStatus(this.com);
      if(this.com && this.com.userId){
        this.commentedUser=this.session.users[this.com.userId];
        this.photo1=this.session.updatePhotoIcon(this.commentedUser,'user');
       
      }
    //    if(this.user!=null && this.user.photoId!=null){
    //      this.user.photo=this.session.updatePhotoIcon(this.user.photo);
    //   this.photo1=this.user.photo.displayPath;
    // }else{
    //   console.log("comment component bad");
    //        this.apiService.getData("/User/"+this.session.shareObj['user']).subscribe((data) => { 
    //               this.user=data;
    //                this.user.photo=this.session.updatePhotoIcon(this.user.photo);
    //               this.photo1=this.user.photo.displayPath;
    //         });
    // }
  }

  likeCommentSection(){
  
  
      this.apiService.getData("/FeedComment/like/"+this.feedId+"/"+this.com.id+"/"+this.session.shareObj['user']).subscribe((data) => { 
      this.com= data;
      this.toggleLikeStatus(data);
  });
}

  toggleLikeStatus(data){ 

     if(data!=null && data.likeCount>0){
         let arr=Object.keys(data.likedBy);
           if(-1!==jQuery.inArray(this.session.shareObj['user'], arr)){
              this.commented=true;
           }else{
             this.commented=false;
           }
      }else{
        this.commented=false;
      }
  }

  
}
