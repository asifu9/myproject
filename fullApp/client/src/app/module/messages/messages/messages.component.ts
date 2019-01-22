import { Component, OnInit, ComponentFactoryResolver, ViewChild, ViewContainerRef } from '@angular/core';
import { Messages, DropdownValue, Items, User, UserMapItems } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { Router, ActivatedRoute } from "@angular/router";
import { ScrollTopService } from "../../shared/shared/scroll-top-service.service";
import { MatDialog } from "@angular/material";
import { CreateMessageGroupComponent } from "../../messages/create-message-group/create-message-group.component";
import { SearchOptionComponent } from "../../messages/search-option/search-option.component";
import { MessageService } from "../../messages/message.service";
declare var jQuery: any;
@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {
  
  searchInput: string;
  messages: Messages[];
  searchResult: User[];
  searchResultChannel: MessageChannel[];
  message: Messages = new Messages();
  newMessage: boolean = false;
  showInboxItem: boolean = false;
  showSentItem: boolean = false;
  defaultUser: string = '/assets/static/user.png';
  component: any;
  users: any[];
  channelId: string;
  items: any[] = new Array<any>();
  selectedItem: DropdownValue[] = new Array<DropdownValue>();;
  selectedMessage: User;
  selectedIndex: number;
  channels: Items[];
  userMapItems: UserMapItems;
  @ViewChild("messageModel") mymodel;
  option: string = "user";
  @ViewChild("container", { read: ViewContainerRef }) container;

  constructor(public messageService:MessageService,public dialog: MatDialog,private route:ActivatedRoute,private apiService: HttpService, private router: Router, private session: SessionService, private componentResolver: ComponentFactoryResolver) {
       this.router.routeReuseStrategy.shouldReuseRoute = function() {
        return true;
    };
    this.messageService.selectedMessageId.subscribe(result=>{
      if(result){
        this.selectedIndex=result;
      }
    })
   }

  ngOnInit() {
    //get the users
    this.session.updateMenu("messages");
    this.getAllUsers();
    this.fetchMessages(this.session.shareObj['user']);
    this.getChannels();

  }

  // showData(ssss, indexSele) {
  //   this.selectedIndex = indexSele;
  //   this.selectedMessage = ssss;
  //   if (this.userMapItems && this.userMapItems.messageUserCounter && this.userMapItems.messageUserCounter[this.selectedMessage.id] != 0) {
  //     this.apiService.getData("/Message/clearCounter/messageuser/" + this.session.shareObj['user'] + "/" + this.selectedMessage.id).subscribe(
  //       result => {
  //         this.userMapItems.messageUserCounter[this.selectedMessage.id] = 0;
  //       }, err => {
  //         console.log("error ", err);
  //       }
  //     );
  //   }
  //   // /showData asif
  //   this.router.navigate(['user/', ssss.id],{relativeTo:this.route});
  //   //this.selectedUser=ssss;
  // }
  // searchUser() {
  //   if (this.option == 'user') {

  //     this.searchResult = [];
  //     if (this.searchInput) {
  //       this.apiService.getData("/User/search/" + this.searchInput).subscribe(
  //         result => {
  //           if (result) {
  //             result.forEach(element => {
  //               if (this.userMapItems && this.userMapItems.messageUser) {
  //                 if (this.userMapItems.messageUser.indexOf(element.id) == -1) {
  //                   this.searchResult.push(element);
  //                 }
  //               } else {
  //                 this.searchResult.push(element);
  //               }
  //             });
  //           }
  //         }
  //       )
  //     }
  //   } else {
  //     this.searchResultChannel = [];
  //     if (this.searchInput) {

  //       this.apiService.getData("/MessageChannel/search/" + this.searchInput).subscribe(
  //         result => {
  //           if (result) {
  //             result.forEach(element => {
  //               if (this.userMapItems && this.userMapItems.messageChannel) {
  //                 if (this.userMapItems.messageChannel.indexOf(element.id) == -1) {
  //                   this.searchResultChannel.push(element);
  //                 }
  //               } else {
  //                 this.searchResultChannel.push(element);
  //               }
  //             });
  //           }
  //         }
  //       )
  //     }
  //   }
  // }
  // updateRadio(type) {
  //   this.option = type;
  // }
  showChannelData(ssss, inn) {
    this.selectedIndex = inn;
    this.selectedMessage = undefined;
    this.channelId = ssss;
  }

  getChannels() {


    this.fetchUserMap();



  }

  fetchUserMap() {
    console.log("======");
    this.apiService.getData("/Message/UserMapItems/fetch/" + this.session.shareObj['user']).subscribe(
      result => {
        if (result) {
          if (!this.userMapItems) {
            this.userMapItems = new UserMapItems();
            this.userMapItems.id = this.session.shareObj['user'];
          }
          this.userMapItems = result;
        }
      }, err => {
        console.log(err);
      }
    )

  }


  getAllUsers() {

    this.apiService.getData("/User/wall/" + this.session.shareObj['wall'] + "/" + this.session.shareObj['user']).subscribe((data) => {
      if (data) {
        this.users = data;
        if (this.users != null && this.users.length > 0) {
          this.users.forEach(u => {
            let ii = { 'value': u.id, 'label': u.name, 'user': u }
            this.items.push(ii);
          });

        }
      }


    });
  }
  sendMessage() {
    var data = [];
    if (this.selectedItem != undefined) {
      this.selectedItem.forEach(item => {
        data.push(item.value);
      });
    }

    this.message.messagedBy = this.session.shareObj['user'];
    this.message.tenantId = this.session.shareObj['wall'];
    this.message.status = 1;
    this.apiService.postData("/Message/", this.message).subscribe((data) => {
      // this.messages.unshift(data);
      this.fetchMessages(this.session.shareObj['user']);
    });;

  }
  fetchMessages(userId) {
    console.log("ok here is culprit ooooooooo");
    this.apiService.getData("/Message/" + this.session.shareObj['user'] + "/" + this.session.shareObj['wall']).subscribe((data) => {
      console.log(data);
      if (data) {
        this.messages = new Array<Messages>();
        if (data.message) {
          for (var key in data.message) {
            let messageItem = data.message[key];
            if (messageItem.messageToUser.photoId == null) {
              messageItem.messageToUser.photoPath = "/assets/static/user.png";
            } else {
              messageItem.messageToUser.photoPath = '/assets/uploads/' + messageItem.messageToUser.photo.createdBy + '/' + messageItem.messageToUser.photo.albumId + '/' + messageItem.messageToUser.photo.details[0].name;
            }
            this.messages.push(messageItem);
          }
        }

      }

    });
  }


  searchUser(){
    const dialogRef = this.dialog.open(SearchOptionComponent, { height: '350px',
  width: '600px',
      data: { searchType: 'user'}
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      this.addMe(result)
    });
  }
  searchGroup(){
        const dialogRef = this.dialog.open(SearchOptionComponent, { height: '350px',
  width: '600px',
      data: { searchType: 'group'}
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      this.addChannelToMe(result)
    });
  }

  getPhotoPath(photo) {

    photo = this.session.updatePhotoIcon(photo, 'photo');
    return photo;
  }
  getPhotoPathIcon(p){
    
   return this.session.showPhoto(p);
  }
  addMe(user) {
    if (this.userMapItems && this.userMapItems.messageUser) {
      if (this.userMapItems.messageUser.indexOf(user.id) != -1) {
        this.router.navigate(['home/messages/user',user.id]);
        return;
      }
    }
    this.apiService.getData("/Message/addUser/" + this.session.shareObj['user'] + "/" + user.id + "/messsage/user").subscribe(
      result => {
        if (!this.userMapItems) {
          this.userMapItems = new UserMapItems();
          this.userMapItems.id = this.session.shareObj['user'];
        }
        this.userMapItems.messageUserObj.push(result);
        this.userMapItems.messageUser.push(result.id);
      }
    );
  }
  addChannelToMe(channelId) {
    if(!channelId)
      return;
    if (this.userMapItems && this.userMapItems.messageChannel && this.userMapItems.messageChannel.indexOf(channelId.id) != -1) {
      this.router.navigate(['home/messages/channel/',channelId.id]);
      return;
    }
    this.apiService.postData("/MessageChannel/add/" + channelId.id + "/" + this.session.shareObj['user'], null).subscribe(
      result => {
        if (!this.userMapItems) {
          this.userMapItems = new UserMapItems();
          this.userMapItems.id = this.session.shareObj['user'];
        }
        if (!this.userMapItems.messageChannelObj) {
          this.userMapItems.messageChannelObj = [];
        }
        if (!this.userMapItems.messageChannel) {
          this.userMapItems.messageChannel = [];
        }
        if (this.userMapItems.messageChannel.indexOf(channelId.id) == -1) {
          this.userMapItems.messageChannelObj.push(channelId);
          this.userMapItems.messageChannel.push(channelId.id);
        }

      }
    );
  }

  createMessageGroup(){
        const dialogRef = this.dialog.open(CreateMessageGroupComponent, { height: '70%',
  width: '60%'
      //data: { type: type, createUpdate: createUpdate }
    });
    dialogRef.afterClosed().subscribe(result => {

    });
  }
}
