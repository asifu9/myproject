import { Component, OnInit,Input,ViewChild ,ElementRef} from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { TicketCategory, Ticket, PriorityList, ConfigValues } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { FormGroup, FormControl } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { ConfigValueService } from "../../../ConfigValueService";
import { HttpEventType } from "@angular/common/http";
import {MatSelectModule} from '@angular/material/select';

import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";
declare var jQuery: any;

@Component({
  selector: 'app-ticket-create',
  templateUrl: './ticket-create.component.html',
  styleUrls: ['./ticket-create.component.scss']
})
export class TicketCreateComponent implements OnInit {

  @ViewChild('fileInput') inputEl: ElementRef;
  ticketCategories:TicketCategory[]=new Array<TicketCategory>();
  loading:boolean=false;
  ticketId:string;
  readOnly:boolean=false;
  tempPriority:string;
  ticket:Ticket=new Ticket();
  ticketForm:FormGroup;
  status:string='new';
  buttonValue:string='';
  statusList:ConfigValues[];
  header:string;
  tempPriorityDisValue:string;
  tempCategoryDisValue:string;
  tempCategoryId:string;
  isOpen:boolean;
  isOpenCategory:boolean;
  constructor(private notificationServiceService:NotificationServiceService,private httpService:HttpService,
  private activatedRoute:ActivatedRoute,private translation:TranslationService,
  private sessionService:SessionService,private router:Router,private ticketService:ConfigValueService) { 
    this.ticketId=this.activatedRoute.snapshot.params['id'];
  }
 toggleSelect() {
    this.isOpen = !this.isOpen;
  }

  toggleCategory(){
    this.isOpenCategory=!this.isOpenCategory;
  }
   initData() {
 
      console.log("ticketCategories ok now start");
      console.log(this.ticketService.ticketPriority);
      console.log(this.ticketService.ticketCategory);
      this.notificationServiceService.showProgressBar("","");
      if(this.ticketId){
        this.translation.getAsyn("updateTicket").subscribe(result=>{
          this.header=result;
        })
        this.fetchExistingTicket();
      }else{
        this.translation.getAsyn("createTicket").subscribe(result=>{
          this.header=result;
        })
        this.newTicket();
        this.notificationServiceService.hideProgressBar();
      }
      
    
  
  }

  ngOnInit() {
    this.sessionService.updateMenu("tickets");
    jQuery('.ui.dropdown').dropdown();
    this.notificationServiceService.showProgressBar();
    this.initForm();
        if(this.ticketService.configFetchStatus!="done"){
      this.ticketService.fetchConfigValues().subscribe(result=>{
        this.initData();
      })
    }else{
    this.initData();
    }
   if(!this.ticket.priority ||this.ticket.priority===""){
     this.translation.getAsyn("selectOne").subscribe(result=>{
       this.tempPriorityDisValue=result;
     })
   }
   if(!this.ticket.ticketCategoryId || this.ticket.ticketCategoryId===""){
      this.translation.getAsyn("selectOne").subscribe(result=>{
       this.tempCategoryDisValue=result;
     })
   }
   console.log(this.ticketService.ticketCategory);
   console.log(this.ticketService.ticketPriority);
  }
  initForm(){
      this.ticketForm=new FormGroup({
      subject:new FormControl(''),
      description:new FormControl(''),
      ticketCategoryId:new FormControl(''),
      priority:new FormControl(2),
      notifyToManager:new FormControl(1)
    });
  }
fetchExistingTicket(){
     this.httpService.getData("/Ticket/"+this.ticketId).subscribe(
        result=>{
          this.ticket=result;
          this.status='edit';
          this.readOnly=true;
          this.newTicket();
          this.notificationServiceService.hideProgressBar();
        },
        error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      )
}
clickedItem(event){
  console.log(event);
  this.isOpen=false;
  this.tempPriority=event;
  this.ticket.priority=event;
  this.tempPriorityDisValue=this.ticketService.ticketPriorityMap[event];
}

clickedCategoryItem(event){
  console.log(event);
    this.isOpenCategory=false;
 
  this.ticket.ticketCategoryId=event;
  this.tempCategoryDisValue=this.ticketService.ticketCategoryMap[event];
}


  newTicket(){
    //disabled: true
    if(!this.ticket.priority){
      this.ticket.priority="3";
      this.ticket.ticketCategoryId="1";
    }else{
      this.tempPriorityDisValue = this.ticketService.ticketPriorityMap[this.ticket.priority];
    }
    console.log(this.ticket.priority);
    console.log(this.ticket.priority);
    this.ticketForm=new FormGroup({
      subject:new FormControl(this.ticket.subject),
      description:new FormControl(this.ticket.description),
      ticketCategoryId:new FormControl(this.ticket.ticketCategoryId),
      priority:new FormControl(this.ticket.priority),
      notifyToManager:new FormControl(this.ticket.notifyToManager)
    });
    if('new'===this.status){
      this.translation.getAsyn("save").subscribe(result=>{
        this.buttonValue=result;
      })
    }else{
      this.readOnly=true;
       this.translation.getAsyn("update").subscribe(result=>{
        this.buttonValue=result;
      })
      
    }
    this.notificationServiceService.hideProgressBar();

  }



  createUpdate(){

    if('new'==this.status){
      this.ticket = Object.assign(this.ticket,...this.ticketForm.value);
      this.ticket.createdBy=this.sessionService.shareObj['user'];
      this.ticket.tenantId=this.sessionService.shareObj['wall'];
      this.ticket.status='1';
      console.log("gooint ot save");
      console.log(this.ticket);
      this.notificationServiceService.showProgressBar();
      this.httpService.postData('/Ticket/',this.ticket).subscribe(
        event=>{
          if(event){
            let eee:any=event;
            this.notificationServiceService.showSaveDailog("ticket","create",false);
            this.router.navigate(['home/tickets/edit/',eee.id]);
          }
          
        },error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      )
    }else{
      this.notificationServiceService.showProgressBar();
       this.ticket = Object.assign(this.ticket,...this.ticketForm.value);
      this.ticket.updatedBy=this.sessionService.shareObj['user'];
      this.ticket.tenantId=this.sessionService.shareObj['wall'];
      this.httpService.putData('/Ticket/',this.ticket).subscribe(
        event=>{
          if(event){
            let eee:any=event;
            this.notificationServiceService.hideProgressBar();
            this.notificationServiceService.showSaveDailog("ticket","update",true);

          }
        },
        error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      )

    }
  }

   uploadImages(file) {
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
    }
    let userId = this.sessionService.shareObj['user'];

//ticket/photos/{ticketId}/{userId}/{tenantId}
this.notificationServiceService.showProgressBar();
    let url =  '/Upload/ticket/photos/'+this.ticketId+"/" + userId + '/' + this.sessionService.shareObj['wall'];
    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');
    this.httpService.postDataWithOptions(url,formData,headers).subscribe(
      data => { },
      err => { 
        this.notificationServiceService.hideProgressBar();
      this.notificationServiceService.showErrorDialog(err);},
      () => { this.router.navigate(['/tickets/edit/',this.ticketId]);}
      );
  }

}
