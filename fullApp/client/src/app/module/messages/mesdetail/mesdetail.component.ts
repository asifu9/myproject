import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { Messages, MessageChannel, User } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { Util } from "../../shared/util";
import { ActivatedRoute, Router } from "@angular/router";
import { HttpEventType } from "@angular/common/http";
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { MatDialog } from "@angular/material";
import { CreateMessageGroupComponent } from "../../messages/create-message-group/create-message-group.component";
import { MessageService } from "../../messages/message.service";

declare var jQuery: any;
@Component({
  selector: 'app-mesdetail',
  templateUrl: './mesdetail.component.html',
  styleUrls: ['./mesdetail.component.scss']
})
export class MesdetailComponent implements OnInit, AfterViewInit {

  inPogress: boolean = false;
  textData: string;
  messages: Messages[] = new Array<Messages>();
  currentUser: string = '';
  currentUserObj: User;
  showButton: boolean = false;
  replay: Messages = new Messages();
  targetUserName: string;
  isReplayClicked: boolean = true;
  selectedComment: Messages;
  channelId: MessageChannel;
  showTextArea: boolean;
  isEditable:boolean=false;

  focusIn() {
    this.showButton = true;
  }
  focusOut() {
    if (this.textData && this.textData.length > 0) {
      this.showButton = true;
    } else {
      this.showButton = false;
    }


  }

  private _message: Messages;


  constructor(public dialog: MatDialog,private apiService: HttpService, 
    private router: Router, private session: SessionService,
    private activatedRoute: ActivatedRoute, 
    private messageService: MessageService,
    private notificaitonSerivce: NotificationServiceService) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
  }

  currentId: string;

  cancelReply() {
    this.isReplayClicked = false;
  }
  ngOnInit() {
    //this.fetchData();
    this.session.updateMenu("messages");
    this.type = this.activatedRoute.snapshot.params['type'];
    this.currentId = this.activatedRoute.snapshot.params['id'];
    this.currentUser = this.session.shareObj['user'];
    console.log("in detail message " + this.currentId + " : " + this.type);
    this.getUserById(this.currentUser);
    this.fetchData();
  }
  ngAfterViewInit() {
    console.log("after init");

  }
  mainPhoto: string;
  type: string;
  targetUser: User;
  fetchData() {
    this.notificaitonSerivce.showProgressBar();
    //flowData
    //console.log(this.targetUser + " : " + this.channelId);
    this.messages = [];
    this.currentId = this.activatedRoute.snapshot.params['id'];
    this.type = this.activatedRoute.snapshot.params['type'];
    if (this.type == 'user') {
      this.messageService.selectedMessageId.next(this.currentId);
      this.apiService.getData("/User/" + this.currentId).subscribe(result => {
        this.targetUser = result;
        this.targetUserName = this.targetUser.name;
        this.mainPhoto = this.session.showPhoto(this.targetUser.photo);

        this.apiService.getData("/Message/flowData/" + this.currentUser + "/" + this.targetUser.id).subscribe(
          (data) => {
          console.log(data);
          if (null != data && data.length > 0) {
            data.forEach(e => {
              if (e.messageToUser.photoId != null) {
                e.messageToUser.photoPath = 'http://localhost:6541/attachments/uploads/' + e.messageToUser.photo.createdBy + '/' + e.messageToUser.photo.albumId + '/' + e.messageToUser.photo.details[0].name;
              } else {
                e.messageToUser.photoPath = "http://localhost:6541/attachments/profileimages/img1.jpg";
              }
              if (e.messagedByUser.photoId != null) {
                e.messagedByUser.photoPath = 'http://localhost:6541/attachments/uploads/' + e.messagedByUser.photo.createdBy + '/' + e.messagedByUser.photo.albumId + '/' + e.messagedByUser.photo.details[0].name;
              } else {
                e.messagedByUser.photoPath = "http://localhost:6541/attachments/profileimages/img1.jpg";
              }

            });
          }
          this.messages = data;
          this.notificaitonSerivce.hideProgressBar();
        },error=>{
        this.notificaitonSerivce.hideProgressBar();
        this.notificaitonSerivce.showErrorDialog(error);
      });
      },error=>{
        this.notificaitonSerivce.hideProgressBar();
        this.notificaitonSerivce.showErrorDialog(error);
      })

    } else if (this.type == 'channel') {
      this.messageService.selectedMessageId.next(this.currentId);
      this.apiService.getData("/MessageChannel/" + this.currentId).subscribe(
        result => {
            
          if (result) {
            this.mainPhoto = this.session.showPhoto(result.photo);
            this.channelId = result;
            if(this.channelId.createdBy==this.session.shareObj['user']){
              this.isEditable=true;
            }
            this.targetUserName = result.name;
            this.apiService.getData("/Message/channelData/" + this.channelId.id).subscribe(
              (data) => {
                console.log(data);
                if (null != data && data.length > 0) {
                  data.forEach(e => {
                    if (e.messageToUser) {
                      if (e.messageToUser.photoId != null) {
                        e.messageToUser.photoPath = 'http://localhost:6541/attachments/uploads/' + e.messageToUser.photo.createdBy + '/' + e.messageToUser.photo.albumId + '/' + e.messageToUser.photo.details[0].name;
                      } else {
                        e.messageToUser.photoPath = "http://localhost:6541/attachments/profileimages/img1.jpg";
                      }
                    }
                    if (e.messagedByUser.photoId != null) {
                      e.messagedByUser.photoPath = 'http://localhost:6541/attachments/uploads/' + e.messagedByUser.photo.createdBy + '/' + e.messagedByUser.photo.albumId + '/' + e.messagedByUser.photo.details[0].name;
                    } else {
                      e.messagedByUser.photoPath = "http://localhost:6541/attachments/profileimages/img1.jpg";
                    }

                  });
                }
                this.messages = data;
                this.notificaitonSerivce.hideProgressBar();
              }, error => {
                this.notificaitonSerivce.hideProgressBar();
                this.notificaitonSerivce.showErrorDialog(error);
              });
          }else{
            this.notificaitonSerivce.hideProgressBar();
          }
        }, error => {
          this.notificaitonSerivce.hideProgressBar();
          this.notificaitonSerivce.showErrorDialog(error);
        }
      )

    }
  }

  editGroup(){
        const dialogRef = this.dialog.open(CreateMessageGroupComponent, { height: '70%',
  width: '60%',
      data: { channel: this.channelId}
    });
    dialogRef.afterClosed().subscribe(result => {

    });
  
  }
  sendMessage() {

    if (this.channelId && !this.targetUser) {
      this.inPogress = true;
      let channelMessge = new Messages();
      channelMessge.messagedBy = this.session.shareObj['user'];
      channelMessge.tenantId = this.session.shareObj['wall'];
      channelMessge.message = this.textData;
      channelMessge.groupId = this.channelId.id;
      channelMessge.status = 1;
      this.apiService.postData("/Message/", channelMessge).subscribe((event) => {

        if (event) {
          let data1: any = event;
          if (!data1.messagedByUser.photoId) {
            data1.messagedByUser.photoPath = "http://localhost:6541/attachments/profileimages/img1.jpg";
          } else {
            data1.messagedByUser.photoPath = 'http://localhost:6541/attachments/uploads/' + data1.messagedByUser.photo.createdBy + '/' + data1.messagedByUser.photo.albumId + '/' + data1.messagedByUser.photo.details[0].name;
          }
          this.messages.unshift(data1);
          this.textData = "";
          this.showButton = false;
          this.inPogress = false;
        }
         this.textData="";
      }, error => {
        //handle here me
        this.inPogress = false;
      });;
    } else {
      this.inPogress = true;

      var data = [];
      this.replay.messagedBy = this.session.shareObj['user'];
      this.replay.tenantId = this.session.shareObj['wall'];
      this.replay.status = 1;
      this.replay.message = this.textData;
      this.replay.messagedTo = this.targetUser.id;

      this.apiService.postData("/Message/", this.replay).subscribe((event) => {
        if (event) {
          let data1: any = event;
          if (!data1.messagedByUser.photoId) {
            data1.messagedByUser.photoPath = "http://localhost:6541/attachments/profileimages/img1.jpg";
          } else {
            data1.messagedByUser.photoPath = 'http://localhost:6541/attachments/uploads/' + data1.messagedByUser.photo.createdBy + '/' + data1.messagedByUser.photo.albumId + '/' + data1.messagedByUser.photo.details[0].name;
          }
          this.messages.unshift(data1);
           
          this.isReplayClicked = false;
          setTimeout(() => {
            this.inPogress = false;
            this.textData="";
          }, 1000);

        }
      }, error => {
        this.inPogress = false;
        this.notificaitonSerivce.showErrorDialog(error);
      });;

    }
  }
  getUserById(id) {
    let url = "/User/" + id;
    console.log("url is " + url)
    this.apiService.getData(url).subscribe((data) => {

      this.currentUserObj = data;
      if (!this.currentUserObj.photoId) {
        this.currentUserObj.photoPath = this.session.defaultPhotoAlbum;
      } else {
        if (this.currentUserObj.photo) {
          this.currentUserObj.photoPath = 'http://localhost:6541/attachments/uploads/' + this.currentUserObj.photo.createdBy + '/' + this.currentUserObj.photo.albumId + '/' + this.currentUserObj.photo.details[0].name;
        } else {
          this.currentUserObj.photoPath = this.session.defaultPhotoAlbum;
        }

      }
      // this.user.dob =this.convertTodisplay(this.user.dob);
    });
  }

  getTimefromEpoch(t) {
    var d = new Date(0); // The 0 there is the key, which sets the date to the epoch
    d.setMilliseconds(t);
    return Util.ConvertTimeFormat(d);
  }

  showComment(messageid) {
    this.selectedComment = messageid;
    this.showTextArea = true;
  }
  deleteMe(iii) {
    this.apiService.delete("/Message/delete/" + iii.id).subscribe(
      result => {
        this.messages.forEach((ii, index) => {
          if (ii.id == iii.id) {
            this.messages.splice(index, 1);
          }
        });
      }
    )
  }
  likedMe(message) {
    this.apiService.getData("/Message/like/" + message.id).subscribe(
      result => {
        this.messages.forEach(iii => {
          if (iii.id == message.id) {
            console.log("got a match now updated");
            iii.likeCount = result;
          }
        });
      }
    )
  }
  showHideValue(value) {
    this.showTextArea = value;

  }
  commentRemovedInMessage(event) {
    let item: Messages;
    this.messages.forEach(iii => {
      if (iii.id == event.id) {
        console.log("got a match now updated");
        console.log(iii.comments);
        console.log(event.comments);
        iii.comments = event.comments;
      }
    });
  }
  addedData(value) {
    if (!this.selectedComment.comments) {
      this.selectedComment.comments = [];
    }

    this.selectedComment.comments.unshift(value.comments[0]);
  }

}
