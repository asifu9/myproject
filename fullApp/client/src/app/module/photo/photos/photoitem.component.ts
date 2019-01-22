import { Component, OnInit, Input } from '@angular/core';
import { Photo } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { HttpEventType } from "@angular/common/http";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { EventEmitter } from "@angular/core";
import { Output } from "@angular/core";
declare var jQuery: any;
@Component({
  selector: 'photoitem',
  templateUrl: './photoitem.component.html',
  styleUrls: ['./photoitem.component.scss']
})
export class PhotoItemComponent implements OnInit {

  @Input()
  item: Photo;
  description: string;
  isEdited: boolean = false;
  isSuccess: boolean = false;
  editClicked: boolean = false;
  editorEnabled: boolean = false;
  isSaving: boolean = false;
  private path: string = this.session.shareObj['url']+"/uploads";
  liked: boolean = false;
  savingMessage: string = 'Saving. Please wait..';
  successMessage: string = 'Successfully updated..';
  type: string = "photo";
  clickedShare: boolean = false;
  @Output() deleteEvent :EventEmitter<string>=new EventEmitter();
  constructor(private notificationService:NotificationServiceService, private apiService: HttpService, private session: SessionService) { }

  ngOnInit() {
    if (this.item != null && this.item != undefined) {
      this.description = this.item.description;
      if (this.item.likes == null || this.item.likes == undefined) {
        this.liked = false;
      } else {
        if (this.item.likes.count > 0) {
          let arr = Object.keys(this.item.likes.likedBy);
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
  }
  clickEditClicked() {
    this.editClicked = true;
    this.isEdited = true;
    this.editorEnabled = true;

  }
  enabledEdit() {
    this.isEdited = true;
  }
  disableEdit() {
    this.isEdited = false;
    this.editClicked = false;
  }
  hideTextBox() {
    this.editorEnabled = false;
    this.isSaving = true;
  }
  postData() {
    this.hideTextBox();

    if ((this.description == '' && this.item.description != '') || this.description != '') {
      this.isEdited = false;
      let desc = this.description;
      if (desc === '') {
        desc = ' ';
      }

      this.apiService.postDataString("/Albums/photo/update/" + this.item.id, desc).subscribe((data) => {
        this.isSuccess = true;
        this.isEdited = false;
        this.editClicked = true;
        this.editorEnabled = false;
        this.isSaving = false;
        setTimeout(() => {
          this.editClicked = false;
          this.isSuccess = false;
        }, 0);
      });
    }
  }
  deletePhoto(id){
    this.notificationService.showProgressBar();
    this.apiService.delete("/Albums/delete/photo/"+id).subscribe(
      result=>{
        this.notificationService.hideProgressBar();
        this.notificationService.showSaveDailog("photo","delete",false);
        this.deleteEvent.emit(id);
      },error=>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    )
  }
  likePhoto() {
    if (!this.session.isActivityExists("album", "likephoto")) {
      return;
    }
    this.apiService.postData("/PhotoLike/" + this.item.id + "/" + this.session.shareObj['user'], {}).subscribe((event) => {
      if(event.type== HttpEventType.Response){
        let data:any=event.body;
        this.item.likes = data;
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
    });
  }
  cancelPost() {
    this.description = this.item.description;
    this.isEdited = false;
    this.editClicked = false;
    this.editorEnabled = false;
  }

}
