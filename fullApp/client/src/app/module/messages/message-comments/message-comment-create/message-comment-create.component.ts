import { Component, OnInit, Input,Output,EventEmitter } from '@angular/core';
import { Messages } from "../../../shared/shared/models";
import { SessionService } from "../../../shared/shared/SessionService";
import { HttpService } from "../../../shared/shared/httpService";

@Component({
  selector: 'app-message-comment-create',
  templateUrl: './message-comment-create.component.html',
  styleUrls: ['./message-comment-create.component.scss']
})
export class MessageCommentCreateComponent implements OnInit {

   @Output() valueChange = new EventEmitter();
   @Output() commentAdded = new EventEmitter();
  _showTextArea:boolean;
    get showTextArea(): boolean {
    return this._showTextArea;
  }
  @Input()
  set showTextArea(showTextArea: boolean) {
    this._showTextArea = showTextArea;
  }

  commentMessage:Messages=new Messages();
  textAreaData:string;
  constructor(private sessionService:SessionService,private httpService:HttpService) { }
  @Input()
  message:Messages;
  ngOnInit() {
    this.commentMessage.messagedBy=this.sessionService.shareObj['user'];
  }
  focusIn(){
    this.showTextArea=true;

  }
  focusOut(){
    if(this.textAreaData && this.textAreaData.length>0){
      this.showTextArea=true;
    }else{
      this.showTextArea=false;
       this.valueChange.emit(false);
    }
  }

  getImage(){
    let photo=this.sessionService.defaultPhotoAlbum;
    if(this.sessionService.currentUser && this.sessionService.currentUser.photoPath){
      photo= this.sessionService.shareObj['url']+"/"+this.sessionService.currentUser.photoPath;
    }
    
    console.log("photo . " + photo);
    return photo;
  }
  postMe(){
     this.commentMessage.message=this.textAreaData;
     this.commentMessage.tenantId=this.sessionService.shareObj['wall'];
     this.httpService.postData("/Message/comment/"+this.message.id,this.commentMessage).subscribe(
       res=>{
         console.log(res);
         this.commentAdded.emit(res);
         this.textAreaData="";
         this.showTextArea=false;
         this.valueChange.emit(false);
       }
       
     )
  }


}
