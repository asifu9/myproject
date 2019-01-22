import { Component, OnInit } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { FormControl } from "@angular/forms";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { UserSelectComponent } from "../../shared/shared/user-select/user-select.component";
import { User,MessageChannel } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { Inject } from "@angular/core";
import {MatChipsModule} from '@angular/material/chips';
@Component({
  selector: 'app-create-message-group',
  templateUrl: './create-message-group.component.html',
  styleUrls: ['./create-message-group.component.css']
})
export class CreateMessageGroupComponent implements OnInit {

messageGroup:FormGroup;
messageChannel:MessageChannel=new MessageChannel();
memberUser:User[];
allUsers:User[];
  constructor(public dialog: MatDialog,private httpService: HttpService,
    private sessionService: SessionService,private notificationService:NotificationServiceService,
    public dialogRef: MatDialogRef<CreateMessageGroupComponent>,
    @Inject(MAT_DIALOG_DATA) public type: any) { }

  ngOnInit() {
    this.initForm()
    if(this.type && this.type.channel){
      this.messageChannel=this.type.channel;
    }
    console.log(this.messageChannel);
    this.fetchData();
  }

  fetchData(){
        this.httpService.getData('/User/wall/' + this.sessionService.shareObj['wall']).subscribe(
      data => {
        this.allUsers = data;
        this.initForm();
      },
      error => {
        this.notificationService.showErrorDialog(error);
      }
    );
  }

  getImage(d){
    return this.sessionService.showPhoto(d);
  }


  initForm(){
      console.log(this.messageChannel);
      this.messageGroup=new FormGroup({
      name:new FormControl(this.messageChannel.name),
      description:new FormControl(this.messageChannel.description)
    });
    this.populateItems(this.messageChannel.members);
  }


    selectUsers(){
      console.log(this.messageChannel)
        const dialogRef = this.dialog.open(UserSelectComponent, { height: '70%',
  width: '60%',
      data: { members: this.messageChannel.members}
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      this.messageChannel.members=result;
      this.populateItems(result);
    });
  }

  onChangeupload(data){
    this.fetchData();
  }

  populateItems(result){
     if(result){
        this.memberUser=[];
        for(let obj of this.allUsers){
          if(this.messageChannel.members.indexOf(obj.id)!==-1){
            this.memberUser.push(obj);
          }
        }
      }
  }

  createUpdate(){

      this.messageChannel = Object.assign(this.messageChannel,...this.messageGroup.value);
      let type="";
      this.messageChannel.tenantId=this.sessionService.shareObj['wall'];
      if(!this.messageChannel.id){
        this.messageChannel.createdBy=this.sessionService.shareObj['user'];
        //its new one
        type="create"
      }else{
        this.messageChannel.updatedBy=this.sessionService.shareObj['user'];
        //its update one
        type="update";
      }
      this.notificationService.showProgressBar();
      this.httpService.postData("/MessageChannel/",this.messageChannel).subscribe(
        result=>{
          this.notificationService.hideProgressBar();
          this.notificationService.showSaveDailog("messageChannel",type,false);
        },
        error=>{
          this.notificationService.hideProgressBar();
          this.notificationService.showErrorDialog(error);
        }
      )
  }

  cancel(){
    this.dialogRef.close();
  }
}
