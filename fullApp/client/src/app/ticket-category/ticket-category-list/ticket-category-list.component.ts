import { Component, OnInit } from '@angular/core';
import { TicketCategory, ReportMeta } from "../../module/shared/shared/models";
import { HttpService } from "../../module/shared/shared/httpService";
import { SessionService } from "../../module/shared/shared/SessionService";
import { NotificationServiceService } from "../../module/shared/shared/notification-service.service";

@Component({
  selector: 'app-ticket-category-list',
  templateUrl: './ticket-category-list.component.html',
  styleUrls: ['./ticket-category-list.component.scss']
})
export class TicketCategoryListComponent implements OnInit {
  meta: ReportMeta[] = [];
ticketCategories:TicketCategory[];
  constructor(private httpService:HttpService,private notificationService:NotificationServiceService,private httpSession:HttpService,private sessionService:SessionService) { }


  ngOnInit() {
       this.meta=[];
     this.meta.push(new ReportMeta("Name", "name"));
     this.meta.push(new ReportMeta("createdOn", "createdOn"));
    this.meta.push(new ReportMeta("Action", undefined));
      this.fetchData();
  }

    fetchData(){
      this.notificationService.showProgressBar("","");
    this.httpSession.getData("/TicketCategory/"+this.sessionService.shareObj['wall']+"/"+true).subscribe(
      result=>{
        this.notificationService.hideProgressBar();
        this.ticketCategories=result;
    },error=>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
    }
    )
  }

   deletedRow(id)
{
  this.notificationService.showProgressBar("","");
  this.httpService.delete("/TicketCategory/"+id.id).subscribe(result=>{
      this.notificationService.showSaveDailog("delete","delete",false);
      this.fetchData();
    },
    error=>{
      this.notificationService.hideProgressBar();
      this.notificationService.showErrorDialog(error);
    }
  )
}

}
