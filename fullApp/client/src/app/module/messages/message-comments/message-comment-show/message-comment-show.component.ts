import { Component, OnInit, Input,Output,EventEmitter } from '@angular/core';
import { Messages } from "../../../shared/shared/models";
import { HttpService } from "../../../shared/shared/httpService";

@Component({
  selector: 'app-message-comment-show',
  templateUrl: './message-comment-show.component.html',
  styleUrls: ['./message-comment-show.component.scss']
})
export class MessageCommentShowComponent implements OnInit {

  constructor(private httpService:HttpService) { }
  @Input()
  item:Messages;
  @Input()
  message:Messages;
  @Output() commentremoved = new EventEmitter();
  ngOnInit() {
    console.log(this.item);
    console.log("in init");
    console.log(this.message);
  }

  removeComment(itemToDelete){
    console.log("berfreo deleting main id");
    console.log(this.message);
     this.httpService.delete("/Message/comment/"+this.message.id+"/"+itemToDelete.id).subscribe(
       result=>{
         let index=0;
         for(index=0;index<this.message.comments.length;index++){
           if(itemToDelete.id==this.message.comments[index].id){
             break;
           }
         }
         this.message.comments.splice(index,1);
         console.log("after delete, before emitting");
         console.log(this.message);
          this.commentremoved.emit(this.message);
       },err =>{
         console.log("error"+ err);
       }
     )
  }

}
