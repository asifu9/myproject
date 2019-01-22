import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { Util } from "../../shared/util";
import { Form, ReportMeta } from "../../shared/shared/models";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { forkJoin as observableForkJoin } from 'rxjs';
@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss']
})
export class ProjectListComponent implements OnInit {

  constructor(private notificationService: NotificationServiceService, private httpService: HttpService, private session: SessionService) { }
  data: any[] = [];
  meta: ReportMeta[] = [];
  formMeta: Form;
  lovData: any = {};
  filter: any;
  ngOnInit() {
    this.fetchLovIfExist();
  }
  fetchLovIfExist() {
    this.notificationService.showProgressBar("", "");
    let tasks$ = [];
    tasks$.push(this.httpService.postData("/customform/data/name/lov", { "lovName": " priority" }));
    //tasks$.push(this.httpService.postData("/customform/data/name/lov/", { "lovName": "customerPriority" }));

    observableForkJoin(...tasks$).subscribe(results => {
      this.meta = [];
      this.meta.push(new ReportMeta("name", "name"));
      this.meta.push(new ReportMeta("startedOn", "startedOn",undefined,"date"));
      this.meta.push(new ReportMeta("description", "description"));
      this.meta.push(new ReportMeta("leadBy", "leadBy"));
      this.meta.push(new ReportMeta("managedBy", "managedBy"));
      this.meta.push(new ReportMeta("priority", "priority", results[0],"list"));
      this.meta.push(new ReportMeta("Action", undefined));
      this.fetchReportData();

    }, error => {
      this.notificationService.hideProgressBar();
      this.notificationService.showErrorDialog(error[0]);
    }
    );
  }


  fetchReportData() {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData("/customform/data/form/project").subscribe(
      result => {
        this.notificationService.hideProgressBar();
        this.data = result;
      }, error => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );

  }
   deletedRow(id) {
    this.notificationService.showProgressBar("", "");
    this.httpService.delete("/customform/project/" + id.id).subscribe(result => {
      this.notificationService.showSaveDailog("delete", "delete", false);
      this.fetchReportData();
    },
      error => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    )
  }
  getDate(d) {
    let date = new Date(d);
    return Util.displayDateFormat(date);
  }

}
