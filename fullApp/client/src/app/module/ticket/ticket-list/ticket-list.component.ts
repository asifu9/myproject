import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { Ticket, ConfigValues, ReportMeta } from "../../shared/shared/models";
import { ConfigValueService } from "../../../ConfigValueService";
import { Router, ActivatedRoute } from "@angular/router";
import { Util } from "../../shared/util";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit {

  ticketList: Ticket[];
  reportMeta: ReportMeta[] = [];
  ticketListAssigned: Ticket[] = [];
  ticketListNew: Ticket[] = [];
  priorityList: ConfigValues[];
  statusList: ConfigValues[];
  status: string = '';
  canResolveTicket: string = '';
  canAssignTicket: string = '';
  path: string;
  constructor(private notificationServiceService: NotificationServiceService,private httpService: HttpService, private activatedRoute: ActivatedRoute, private router: Router, 
  private sessionService: SessionService, public ticketService: ConfigValueService) {
    
    this.path = this.sessionService.shareObj['url'] + '/uploads';
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
    this.status = this.activatedRoute.snapshot.params['type'];
  }
  getDate(d){
    let date=new Date(d);
    return Util.displayDateFormat(date);
  }
  ngOnInit() {
    this.sessionService.updateMenu("tickets");
    if (!this.status) {
      this.status = "mytickets";
    }
    //this.isTicketsNeeded();
    if(this.ticketService.configFetchStatus!="done"){
      this.ticketService.fetchConfigValues().subscribe(result=>{
        this.fetchTickets();
      })
    }else{
    this.fetchTickets();
    }
  }

  fetchTickets() {
    this.reportMeta = [];

    this.reportMeta.push(new ReportMeta("name", "subject"));
    this.reportMeta.push(new ReportMeta("createdOn", "createdOn",undefined,"date"));
    this.reportMeta.push(new ReportMeta("status", "status",this.ticketService.ticketStatus,"list"));
    this.reportMeta.push(new ReportMeta("assignedTo", "assignedToUser"));
    this.reportMeta.push(new ReportMeta("priority", "priority",this.ticketService.ticketPriority,"list"));
    this.reportMeta.push(new ReportMeta("category", "ticketCategoryId",this.ticketService.ticketCategory,"list"));
    this.reportMeta.push(new ReportMeta("actions", undefined));

    this.notificationServiceService.showProgressBar("","");
    if (this.ticketService.configFetchStatus == "none" || this.ticketService.configFetchStatus == 'inprogress') {
      setTimeout(() => {
        this.notificationServiceService.hideProgressBar();
        this.fetchTickets();
      }, 1000);
    } else {
      let url = "";
      console.log("status " + this.status)
      if (this.status == 'mytickets') {
        url = "/Ticket/" + this.sessionService.shareObj['wall'] + "/" + this.sessionService.shareObj['user'];
      } else if (this.status == 'assignedtome') {
        if (this.sessionService.currentRoles.indexOf("canFixTicked")) {
          this.canResolveTicket = "FIXER";
        }
        url = "/Ticket/assigned/" + this.sessionService.shareObj['wall'] + "/" + this.sessionService.shareObj['user'];
      } else if (this.status == 'unassigned') {
        if (this.sessionService.currentRoles.indexOf("canFixTicked")) {
          this.canResolveTicket = "FIXER";
        }
        url = "/Ticket/new/" + this.sessionService.shareObj['wall'] + "/" + this.sessionService.shareObj['user'];
      }
      this.httpService.getData(url).subscribe(
        result => {
          console.log(this.ticketService.tickeStatusMap);
          console.log(result);

          this.ticketList = result;
          this.notificationServiceService.hideProgressBar();
        },
        err =>{
          this.notificationServiceService.hideProgressBar();
        }
      );

    }

  }

  assignToMe(ticketId) {
    console.log("assign me call");
    this.notificationServiceService.showProgressBar("","");
    this.httpService.getData("/Ticket/assign/" + this.sessionService.shareObj['wall'] + "/" + this.sessionService.shareObj['user'] + "/" + ticketId + "/2").subscribe(
      result => {
        this.notificationServiceService.hideProgressBar();
        this.router.navigate(['/tickets/list']);
      },
      error=>{
        this.notificationServiceService.hideProgressBar();
      }
    );
  }
  updateTicket(t) {
    this.router.navigate(['home/tickets/update/ticket/' + t]);
  }
   updateByOwner(t) {
    this.router.navigate(['home/tickets/edit/' + t]);
  }

}
