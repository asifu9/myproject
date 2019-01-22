import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { Router } from "@angular/router";
import { UserLeaves, LeaveApply, ReportMeta } from "../../shared/shared/models";
import { Util } from "../../shared/util";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-leave-details',
  templateUrl: './leave-details.component.html',
  styleUrls: ['./leave-details.component.scss']
})
export class LeaveDetailsComponent implements OnInit {

  constructor(private notificationService:NotificationServiceService, private httpService: HttpService, private session: SessionService, private router: Router) { }
  userLeaves: UserLeaves;
  userLeavesMeta: ReportMeta[] = [];
  appliedLeavesMeta: ReportMeta[] = [];
  createLeave: LeaveApply;
  appliedLeaves: LeaveApply[];
  ngOnInit() {
    this.session.updateMenu("leaves");
    this.popuplateMeta();
    this.fetchData();
    this.fetchAppliedLeaves();
  }

  fetchAppliedLeaves() {
    this.httpService.getData("/leaveapply/user/" + this.session.shareObj['user'] + "/" + Util.getFinancialYear(this.session)).subscribe(
      result => {
      if (result) {
        this.appliedLeaves = result;
      }
    },error=>{
       this.notificationService.showErrorDialog(error.error);
    });
  }
  editMe(id) {
    this.createLeave = id;
  }
  fetchData() {
    this.httpService.getData("/userleaves/" + this.session.shareObj['user'] + "/" + Util.getFinancialYear(this.session)).subscribe(
      result => {
      if (result) {
        this.userLeaves = result;
      }
    },error=>{
      alert(error);
    });
  }
  popuplateMeta() {
    this.userLeavesMeta.push(new ReportMeta("leaveName", "leaveName"));
    this.userLeavesMeta.push(new ReportMeta("count", "count"));
    this.userLeavesMeta.push(new ReportMeta("takenLeaves", "takenLeaves"));
    this.userLeavesMeta.push(new ReportMeta("accumulatedLeaves", "accumulatedLeaves"));
    this.userLeavesMeta.push(new ReportMeta("actions", undefined));


    this.appliedLeavesMeta.push(new ReportMeta("leaveName", "leaveName"));
    this.appliedLeavesMeta.push(new ReportMeta("description", "description"));
    this.appliedLeavesMeta.push(new ReportMeta("days", "days"));
    this.appliedLeavesMeta.push(new ReportMeta("startDate", "startDate",undefined,"date"));
    this.appliedLeavesMeta.push(new ReportMeta("endDate", "endDate",undefined,"date"));
    this.appliedLeavesMeta.push(new ReportMeta("status", "status"));
    this.appliedLeavesMeta.push(new ReportMeta("actions", undefined));

  }
  editRow(obj) {
    this.createLeave = obj;
  }
  showCreate() {
    this.createLeave = new LeaveApply();
  }
  cancelCreate() {
    this.createLeave = undefined;
  }

  getDate(d) {
    let date = new Date(d * 1000);
    return Util.displayDateFormat(date);
  }

  deletedRow(obj) {
    this.httpService.delete("/leaveapply/" + obj.id).subscribe(result => {
      this.fetchAppliedLeaves();
    })
  }

}
