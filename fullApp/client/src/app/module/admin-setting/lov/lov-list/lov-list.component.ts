import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../../shared/shared/httpService";
import { SessionService } from "../../../shared/shared/SessionService";
import { ReportMeta } from "../../../shared/shared/models";
import { NotificationServiceService } from "../../../shared/shared/notification-service.service";

@Component({
  selector: 'app-lov-list',
  templateUrl: './lov-list.component.html',
  styleUrls: ['./lov-list.component.scss']
})
export class LovListComponent implements OnInit {

  constructor(private httpService: HttpService, private session: SessionService, private notificationService: NotificationServiceService) { }
  data: any[] = [];
  meta: ReportMeta[] = [];
  reportConfig: any;
  filter: any;
  ngOnInit() {
    this.fetchReportData();
    /**
     * createdBy: "3e12753c-d249-45ae-a1df-f42bc367f793"
createdOn: 1540142255
displayValue: "Low"
id: "c8f7cf55-5fe2-4461-9e03-d36fce42eb11"
index: "1"
lovName: " priority"
storedValue: "1"
tenantId: "3c3a472e-974d-4681-9509-09c6ba413c10"
     */
    this.meta.push(new ReportMeta("index", "index"));
    this.meta.push(new ReportMeta("lovName", "lovName"));
    this.meta.push(new ReportMeta("storedValue", "storedValue"));
    this.meta.push(new ReportMeta("displayValue", "displayValue"));
    this.meta.push(new ReportMeta("Action", undefined));
  }
  fetchReportData() {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData("/reportconfig/" + this.session.shareObj['wall'] + "/lov").subscribe(
      result => {
        this.reportConfig = result;
        this.notificationService.hideProgressBar();
      },
      error => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);

      }
    );
    this.httpService.getData("/customform/data/form/lov").subscribe(
      result => {
        this.data = result;
        this.notificationService.hideProgressBar();
      }, error => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );

  }


  deletedRow(id) {
    this.notificationService.showProgressBar("", "");
    this.httpService.delete("/customform/lov/" + id.id).subscribe(result => {
      this.notificationService.showSaveDailog("delete", "delete", false);
      this.fetchReportData();
    },
      error => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    )
  }

}
