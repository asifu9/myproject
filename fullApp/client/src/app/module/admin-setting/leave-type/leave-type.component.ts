import { Component, OnInit } from '@angular/core';
import { LeaveType, ReportMeta } from "../../shared/shared/models";
import { AdminService } from "../admin.service";
import { HttpService } from "../../shared/shared/httpService";
import { Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-leave-type',
  templateUrl: './leave-type.component.html',
  styleUrls: ['./leave-type.component.scss']
})
export class LeaveTypeComponent implements OnInit {

  constructor(private ns:NotificationServiceService,private httpService:HttpService,private router:Router,public session:SessionService) { }
  leaveType:LeaveType[];
  meta: ReportMeta[] = [];
  ngOnInit() {
    this.fetchData();
  }

  fetchData(){
    this.ns.showProgressBar("","");
    this.httpService.getData("/leavetype/company/"+this.session.shareObj['wall']).subscribe(
      result=>{
        this.leaveType=result;
        this.ns.hideProgressBar();
           this.meta = [];
        this.meta.push(new ReportMeta("name", "name"));
        this.meta.push(new ReportMeta("description", "description"));
        this.meta.push(new ReportMeta("gender", "gender"));
        this.meta.push(new ReportMeta("count", "count"));
        this.meta.push(new ReportMeta("carryFarward", "carryFarward"));
        this.meta.push(new ReportMeta("carryFarwardLimit", "carryFarwardLimit"));
        this.meta.push(new ReportMeta("Action", undefined));

      },error=>{
        this.ns.hideProgressBar();
        this.ns.showErrorDialog(error);
      }
    )
  }

   deletedRow(id) {
    this.ns.showProgressBar("", "");
    this.httpService.delete("/leavetype/" + id.id).subscribe(result => {
      this.ns.hideProgressBar();
      this.ns.showSaveDailog("delete", "delete", false);
      this.fetchData();
    },
      error => {
        this.ns.hideProgressBar();
        this.ns.showErrorDialog(error);
      }
    )
  }

}
