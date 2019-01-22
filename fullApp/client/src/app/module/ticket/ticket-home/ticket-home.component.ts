import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { ActivatedRoute, Router } from "@angular/router";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-ticket-home',
  templateUrl: './ticket-home.component.html',
  styleUrls: ['./ticket-home.component.scss']
})
export class TicketHomeComponent implements OnInit {
  canResolveTicket:string;
  constructor( private httpService:HttpService,private router:Router,private activatedRouter:ActivatedRoute,private sessionService:SessionService) { 
    let dd=this.router.url.split("/");
    this.status=dd[dd.length-1];
   console.log(this.router.url);
  }
  status:string;
  ngOnInit() {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
        return false;
    };
    this.sessionService.updateMenu("tickets");
    this.isTicketsNeeded();
  }

  isTicketsNeeded(){
    this.httpService.getData("/Ticket/categories/"+this.sessionService.shareObj['wall'] + "/" + this.sessionService.shareObj['user']).subscribe(
      result=>{
        console.log(":result is ");
        console.log(result);
        if(result && result.length>0){
          this.canResolveTicket='FIXER';
        }

      },
      error=>{

      }
    );
  }

    getClass(item){

    if(item==this.status){
      return "ui primary button";
    }else{
      return "";
    }
  }

    showMyTickets(stu){
    this.status=stu;
   // this.fetchTickets();
  }


}
