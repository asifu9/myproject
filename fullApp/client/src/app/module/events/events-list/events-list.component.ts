import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { Events, ReportConfig, Form, ReportMeta } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { Util } from "../../shared/util";
import { Router } from "@angular/router";
import { forkJoin as observableForkJoin } from 'rxjs';
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.scss']
})
export class EventsListComponent implements OnInit {

  data: Events[] = [];
  reportConfig: ReportConfig;
  formMeta: Form;
  lovData: any = {};
  meta: ReportMeta[] = [];
  constructor(private notificationService: NotificationServiceService, private httpService: HttpService, private session: SessionService, private router: Router) { }

  ngOnInit() {
    this.fetchData();
  }
  fetchLovIfExist() {
    //      this.notificationService.showProgressBar("", "");
    // let tasks$ = [];
    // tasks$.push(this.httpService.postData("/customform/data/name/lov", { "lovName": " priority" }));
    // //tasks$.push(this.httpService.postData("/customform/data/name/lov/", { "lovName": "customerPriority" }));

    // observableForkJoin(...tasks$).subscribe(results => {

    // });
    // this.httpService.getData("/form/name/project").subscribe(result=>{
    //   if(result){
    //     this.formMeta=result;






    //     if(this.formMeta && this.formMeta.metaData && this.formMeta.metaData){
    //       this.formMeta.metaData.forEach(e => {
    //         if(e.widgetType=='Select'){
    //           this.httpService.postData("/customform/data/name/lov/",{"lovName":e.listApiName}).subscribe(result=>{
    //             this.lovData[e.widgetId]=result;
    //           });
    //         }
    //       });
    //       this.fetchData();
    //     }
    //   }
    // })

  }
  fetchData() {
    this.notificationService.showProgressBar("","");
    this.httpService.getData("/events/" + this.session.shareObj['wall'] + "/" + this.session.shareObj['user']).subscribe(
      result => {
        this.notificationService.hideProgressBar();
        this.data = result;
        this.meta = [];
        this.meta.push(new ReportMeta("name", "name"));
        this.meta.push(new ReportMeta("description", "description"));
        this.meta.push(new ReportMeta("eventStartDate", "eventStartDate",undefined,"date"));
        this.meta.push(new ReportMeta("eventEndDate", "eventEndDate",undefined,"date"));
        this.meta.push(new ReportMeta("Action", undefined));
      },
      error=>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );


  }
  getDisplayValue(i, type) {
    console.log(i + "  : " + type);
    let disVal = "";
    if (this.lovData) {
      if (this.lovData[type]) {
        for (let el of this.lovData[type]) {
          if (el.storedValue == i) {
            disVal = el.displayValue;
            break;
          }
        };
      }
    }
    return disVal;
  }
  getDate(d) {
    let date = new Date(d * 1000);
    return Util.displayDateFormat(date);
  }

  updateEvent(t) {
    this.router.navigate(['home/events/edit/' + t]);
  }

}
