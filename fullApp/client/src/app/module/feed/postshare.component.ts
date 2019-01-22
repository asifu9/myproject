import { Component, OnInit, Input, Output, EventEmitter, Inject } from '@angular/core';
import { CommentsComponent } from "../comments/comments/comments.component";
import { LoaderComponent } from "../shared/shared/loader/loader.component";
import { SuccessComponent } from "../shared/shared/success/success.component";
import { PopupComponent } from "../shared/shared/popup/popup.component";
import { Feed, User, DropdownValue } from "../shared/shared/models";
import { SessionService } from "../shared/shared/SessionService";
import { HttpService } from "../shared/shared/httpService";
import { TranslationService } from "../shared/shared/translate.service";
declare var jQuery: any;
@Component({
  selector: 'postshare',
  templateUrl: './postshare.component.html',
  styleUrls: ['./postshare.component.scss'],
  providers: [CommentsComponent, LoaderComponent, SuccessComponent]

})
export class PostShareComponent implements OnInit {

  @Input() parent: PopupComponent;
  @Input()
  post: Feed;
  photo1: string;
  photo2: string;
  profilePic: string;
  topProfilePic: string;
  user: User;
  imgSize: number = 200;
  itemLabel: string = "Share level";
  items: DropdownValue[];
  desc: string;
  selectedItem: DropdownValue[] = new Array<DropdownValue>();
  showLoader: boolean = false;
  loaderMesssage: string = "";
  successMessage: string = ""
  showSuccess: boolean = false;
  dropdownURL: string = '';
  constructor(private translationService:TranslationService,private apiService: HttpService, private session: SessionService) {


  }

  ngOnInit() {
    this.translationService.getAsyn("savingPleaseWait").subscribe(result=>{
      this.loaderMesssage=result;
    });
    this.translationService.getAsyn("successfullyShared").subscribe(result=>{
      this.successMessage=result;
    });
    
    this.apiService.getData("/User/" + this.session.shareObj['user']).subscribe((data) => {
      this.user = data
      this.topProfilePic = this.user.photo.displayPath;

    });
    if (this.post != null) {
      this.dropdownURL = "/share/groupDetails/" + this.session.shareObj['user'] + "/" + this.post.wallId;
      this.profilePic = this.post.createdByObj.photo.displayPath;
      if (this.post.feedType == 'NORMAL') {

      } else if (this.post.feedType == 'PHOTO') {

      } else if (this.post.feedType == 'WALL_PHOTO') {

      }

    }

  }
  onContainerClicked() {

  }
  cancelShare(event: MouseEvent) {

    this.parent.hide();
  }
  fetchWallsAndGroups() {
    this.apiService.getData("/share/groupDetails/" + this.session.shareObj['user'] + "/" + this.post.wallId).subscribe((data) => {
      this.items = data;
    });
  }
  likePost() {

  }
  sharePost() {
    this.showLoader = true;
    let userId = this.session.shareObj['user'];
    var data = [];
    if (this.selectedItem != undefined) {
      this.selectedItem.forEach(item => {
        data.push(item.value);
      })
    }
    try {
      let postId = this.post.originalPostId
      if (this.post.originalPostId == '' || this.post.originalPostId == null) {
        postId = this.post.id;
      }
      this.apiService.postData("/share/" + postId + "/" + userId + "/feed/" + this.post.wallId + "/" + this.desc, data).subscribe((result) => {
        this.showLoader = false;
        this.showSuccess = true;
        this.showSuccess = false;
        this.parent.hide();

      });
    } catch (e) {
      this.showLoader = false;
    }
  }


}
