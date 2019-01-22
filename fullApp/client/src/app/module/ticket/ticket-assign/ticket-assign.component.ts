import { Component, OnInit,ViewChild,ElementRef } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { HttpService } from "../../shared/shared/httpService";
import { Ticket, TicketUpdate, MapUserAction } from "../../shared/shared/models";
import { ConfigValueService } from "../../../ConfigValueService";
import { SessionService } from "../../shared/shared/SessionService";

@Component({
  selector: 'app-ticket-assign',
  templateUrl: './ticket-assign.component.html',
  styleUrls: ['./ticket-assign.component.scss']
})
export class TicketAssignComponent implements OnInit {
  ticketId:string;
  ticket:Ticket;
  searchUser:string='';
  savingMessage:string='Uploading...';
   @ViewChild('fileInput') inputEl: ElementRef;
  ticketCategoruyUsers:MapUserAction[]=[];
  loading:boolean=false;
  path:string="";
  ticketUpdate:TicketUpdate=new TicketUpdate();
  constructor(private sessionService:SessionService,private activatedRouter:ActivatedRoute,private httpService:HttpService,private ticketService:ConfigValueService) { 
    this.ticketId=this.activatedRouter.snapshot.params['ticketId'];
    this.path=this.sessionService.shareObj['url'];
  }

  ngOnInit() {
    this.sessionService.updateMenu("tickets");
    this.fetchTicketDetails();
  }

  fetchTicketDetails(){
    
    this.httpService.getData("/Ticket/"+this.ticketId).subscribe(result=>{
        this.ticket=result;
        console.log(this.ticket);
        if(this.ticket && this.ticket.updates){
          this.ticket.updates.forEach(element => {
            if(element.updatedByUser && element.updatedByUser.photoPath){
                element.updatedByUser.photoPath=this.path+"/"+element.updatedByUser.photoPath;
            }
          });
        }

    })
  }

  createEntry(){
      if(!this.ticketUpdate.description){
        return;
      }
      console.log("ssss " + this.ticket.status);
      this.ticketUpdate.updatedBy=this.sessionService.shareObj['user'];
      this.ticketUpdate.tenantId=this.sessionService.shareObj['wall'];
      if(this.ticket.updates){
        this.ticket.updates.push(this.ticketUpdate);
      }else{
        this.ticket.updates=new Array<TicketUpdate>();
        this.ticket.updates.push(this.ticketUpdate);
      }
      this.httpService.putData("/Ticket/",this.ticket).subscribe(result=>{
          this.ticketUpdate=new TicketUpdate();
          this.fetchTicketDetails();
      })
  }
getUpdateTime(t){
  let date=new Date(t);
  return " "+date.getDate() + " " + date.getMonth() + " "+date.getFullYear();
  
 // return "asif";
  }

  showCategoryData(){
    console.log(this.searchUser);
    if(this.searchUser.trim()==''){
      this.httpService.getData("/mapuseraction/action/"+this.sessionService.shareObj['wall']+"/TicketCategory/"+this.ticket.ticketCategoryId).
    subscribe(result=>{
      this.ticketCategoruyUsers=result;
    });
  
    }else{
      console.log("nned to implment");
    }
  }

  getClassType(id){
    if(this.ticket.assignedTo==id){
        return "large blue check square icon";
    }else{
      return "large check square icon";
    }
  }
  assignToOther(userId){
    if(userId!=this.ticket.assignedTo){
      this.httpService.getData("/Ticket/assign/" + this.sessionService.shareObj['wall'] + "/" + userId+"/"+this.ticketId+"/2").subscribe(
        result => {
          this.fetchTicketDetails();
        }
      );
    }
  }
  getUpdatedName(name){
    if(name.length > 20){
      return name.substring(0,20)+"...";
    }else{
      return name;
    }
  }
   uploadImages(file) {
    
    if(!this.inputEl){
      return;
    }
    this.loading = true;
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    let formData = new FormData();
    if (fileCount > 0) { // a file was selected
      /*  for (let i = 0; i < fileCount; i++) {
            formData.append('file[]', inputEl.files.item(i));

        }*/
      for (let i = 0; i < fileCount; i++) {
        formData.append('file', inputEl.files.item(i), inputEl.files.item(i).name);
      }
    }else{
      this.loading=false;
      return;
    }
    let userId = this.sessionService.shareObj['user'];

//ticket/photos/{ticketId}/{userId}/{tenantId}
    let url =  '/Upload/ticket/photos/'+this.ticketId+"/" + userId + '/' + this.sessionService.shareObj['wall'];
    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');
    this.httpService.postDataWithOptions(url,formData,headers).subscribe(
      data => { this.fetchTicketDetails();},
      err => { console.log(err) },
      () => { this.loading=false;}
      );
  }
}
