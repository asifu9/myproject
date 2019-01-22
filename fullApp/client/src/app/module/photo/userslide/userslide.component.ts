import { Component, OnInit, Input } from '@angular/core';
import { PhotoCommentsComponent } from "../../photo/photocomments/photocomments.component";
import { User, Photo, PhotoComments, PhotoLikes, CBlock } from "../../shared/shared/models";
import { PopupSliderComponent } from "../../shared/shared/popup/popupslider.component";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { HttpEventType } from "@angular/common/http";
declare var jQuery: any;
@Component({
  selector: 'userslide',
  templateUrl: './userslide.component.html',
  styleUrls: ['./userslide.component.scss'],
  providers: []
})
export class UserslideComponent implements OnInit {

  @Input()
  user: User;
  @Input()
  photoId: string;
  @Input()
  photo: Photo;
  @Input()
  parent: PopupSliderComponent;

  comments: PhotoComments;
  likes: PhotoLikes;
  desc: string = '';
  liked: boolean = false;

  photoComment: CBlock;

  constructor(private session: SessionService, private apiService: HttpService) {

    this.photoComment = new CBlock();
    this.photoComment.comment = '';

  }
  onClose() {
    this.parent.hide();
  }

  ngOnInit() {
    console.log(" parent in userSlide " + this.parent);
    if (this.user != null) {
      //this.user.photo = this.session.updatePhotoIcon(this.user.photo);
    }
    console.log("photo id xchanged " + this.photoId);
    this.fetchComments(this.photoId);
  }

  fetchComments(photoId) {
    try {
      this.apiService.getData("/PhotoComment/commemnts/" + photoId).subscribe((data) => {
        this.comments = data;

      });
    } catch (e) {
      console.log(e);
      this.comments = null;
    }
    this.apiService.getData("/PhotoLike/likes/" + photoId).subscribe((data) => {
      this.likes = data;
      if (this.likes == null || this.likes == undefined) {
        this.liked = false;
      } else {
        if (this.likes.count > 0) {
          let arr = Object.keys(this.likes.likedBy);
          if (-1 !== jQuery.inArray(this.session.shareObj['user'], arr)) {
            this.liked = true;
          } else {
            this.liked = false;
          }
        } else {
          this.liked = false;
        }
      }
    });


  }

  photoCommentCreate() {
    let userId = this.session.shareObj['user'];
    this.photoComment.userId = userId;
    this.apiService.postData("/PhotoComment/" + this.photoId, this.photoComment).subscribe((event) => {
      if(event.type==HttpEventType.Response){
      let data:any=event.body;
      this.photoComment.comment = '';
      this.comments = data;
    }
    });

  }

  likePhoto() {
    this.apiService.postData("/PhotoLike/" + this.photoId + "/" + this.session.shareObj['user'], {}).subscribe((event) => {
      console.log("my data is ");
      if(event.type==HttpEventType.Response){
      let data:any=event.body;
      if (data != undefined) {
        this.likes = data;
        if (data.count > 0) {
          let arr = Object.keys(data.likedBy);
          if (-1 !== jQuery.inArray(this.session.shareObj['user'], arr)) {
            this.liked = true;
          } else {
            this.liked = false;
          }
        } else {
          this.liked = false;
        }
      }
    }
    });

  }
}
