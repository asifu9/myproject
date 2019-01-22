import { Component, OnInit, Input, ViewChild, ViewContainerRef, ComponentFactoryResolver, Output } from '@angular/core';
import { CommentsComponent } from "../comments/comments/comments.component";
import { PostShareComponent } from "./postshare.component";
import { PostInnerComponent } from "./postinner.component";
import { PopupComponent } from "../shared/shared/popup/popup.component";
import { Feed, User, MyMap } from "../shared/shared/models";
import { SessionService } from "../shared/shared/SessionService";
import { HttpService } from "../shared/shared/httpService";
import { HttpEventType } from "@angular/common/http";
import { PostShowComponent } from "../feed/postshow.component";

declare var jQuery: any;
@Component({
  selector: 'post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
  providers: [CommentsComponent, PostShowComponent, PostShareComponent, PostInnerComponent, PopupComponent]

})
export class PostComponent implements OnInit {
  //2e12753c-d249-45ae-a1df-f42bc367f79a
  @Input()
  private post: Feed;
  @Input()
  private user: User;
  path1: string = '';
  public myMap: MyMap[] = [];
  commented: boolean = false;
  private path: string = "";
  imgSize: number = 0;
  copom: any;
  @ViewChild("share1") mymodel;
  @ViewChild(PopupComponent) child: PopupComponent;
  @ViewChild("container", { read: ViewContainerRef }) container;
  postCreatedby: User;
  constructor(public apiService: HttpService, public session: SessionService, protected componentResolver: ComponentFactoryResolver,
    protected customComponentBuilder: PostShareComponent) {
    this.path = this.session.shareObj['url'] + "/uploads";
  }

  getLikeCss() {
    if (this.session.isActivityExists("feed", "feedlike")) {
      return "ui label likeStyleactive "
    } else {
      return "ui label likeStyleactive disableLink";
    }
  }
  shareMe() {
    //share this post
    if (!this.session.isActivityExists("feed", "feedshare")) {
      return;
    }
    if (this.post && this.post.createdBy != this.session.shareObj['user'] && this.post.feedSetting && !this.post.feedSetting["otherscanshare"]) {
      alert(" you can't share this post");
      return;
    }
    if (this.copom != null) {
      this.copom.destroy();
    }
    this.copom = this.container.createComponent(this.componentResolver.resolveComponentFactory(PostShareComponent));
    this.copom.instance.post = this.post;
    this.copom.instance.parent = this.child;

    this.mymodel.show();
    // const modalRef = this.modalService.open(MyDialogComponent);
    //   modalRef.componentInstance.post = this.post;
  }

  ngOnInit() {
    if (this.post != null && this.post != undefined) {
      let data = this.post;

      if (this.post.createdBy) {
        this.postCreatedby = this.session.users[this.post.createdBy];
        this.path1 = this.session.updatePhotoIcon(this.postCreatedby, "user");
      }
      // if(this.post.createdByObj!=null && this.post.createdByObj.photo!=null){
      //    this.post.createdByObj.photo=this.session.updatePhotoIcon(this.post.createdByObj.photo);
      //    this.path1=this.post.createdByObj.photo.displayPath;
      // }
      if (data.likeCountObj != null && data.likeCountObj.count > 0) {
        // var obj = jQuery.parseJSON(data.likeCount.likedBy);
        let arr = Object.keys(data.likeCountObj.likedBy);
        if (-1 !== jQuery.inArray(this.session.shareObj['user'], arr)) {
          this.commented = true;
        } else {
          this.commented = false;
        }

      } else {
        this.commented = false;
      }
      if (this.post.feedSetting && this.post.createdBy == this.session.shareObj['user']) {
        for (var k in this.post.feedSetting) {
          let imap = new MyMap();
          imap.key = k;
          imap.value = this.post.feedSetting[k];
          this.myMap.push(imap);
        }
        console.log("mh map " + this.myMap);
        console.log(this.myMap);
      }
    }

  }
  likePost() {
    if (!this.session.isActivityExists("feed", "feedlike")) {
      return;
    }
    if (this.post && this.post.createdBy != this.session.shareObj['user'] && this.post.feedSetting && !this.post.feedSetting["otherscanlike"]) {
      alert(" you can't like this post");
      return;
    }
    //this.user.dob=this.parseDate(""+this.user.dob);
    let userId = this.session.shareObj['user'];
    this.apiService.postData("/FeedLike/" + this.post.id + "/" + userId, {}).subscribe((event) => {
    if(event){
        let data:any=event;
          this.post.likeCountObj = data;
          if (data.count > 0) {
            let arr = Object.keys(data.likedBy);
            if (-1 !== jQuery.inArray(this.session.shareObj['user'], arr)) {
              this.commented = true;
            } else {
              this.commented = false;
            }
          } else {
            this.commented = false;
          }
    }
    });
  }
  updateFeedSetting() {
    console.log(this.myMap);
    if (!this.post.feedSetting) {
      this.post.feedSetting = {};
    }
    for (var item of this.myMap) {
      let result = "true";

      if (!item.value) {
        result = "false";
      }

      this.post.feedSetting[item.key] = result;
    }
    this.apiService.postData("/Feed/" + this.session.shareObj['wall'] + "/" + this.post.id, this.post.feedSetting).subscribe((data) => {
      // /this.feeds.unshift(data);

    });;
  }
  onContainerClickedPop() {
  }
  onContainerClicked() {
  }

}
