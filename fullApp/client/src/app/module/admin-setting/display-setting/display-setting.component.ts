import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { Features, CompanySetting, ReportMeta } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { FormGroup } from "@angular/forms";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { FormControl } from "@angular/forms";

@Component({
  selector: 'app-display-setting',
  templateUrl: './display-setting.component.html',
  styleUrls: ['./display-setting.component.css']
})
export class DisplaySettingComponent implements OnInit {

  features: Features[] = [];
  companySetting: CompanySetting;
  csForm: FormGroup;
  meta: ReportMeta[] = [];
  constructor(private notificationServiceService: NotificationServiceService, private httpService: HttpService,
    private session: SessionService) { }

  ngOnInit() {
    this.fetchDetails();


  }

  fetchDetails() {
    this.notificationServiceService.showProgressBar();
    this.httpService.getData("/features/").subscribe(result => {
      this.features = result;
      this.httpService.getData("/CompanySetting/" + this.session.shareObj['wall']).subscribe(result2 => {
        this.companySetting = result2;
        this.csForm = new FormGroup({
          dateFormat: new FormControl(this.companySetting.dateFormat),
          leaveStartEndMonths: new FormControl(this.companySetting.leaveStartEndMonths),
          currencyFormat: new FormControl(this.companySetting.currencyFormat),
          currencySymbol: new FormControl(this.companySetting.currencySymbol),
          timeZone: new FormControl(this.companySetting.timeZone),
          adminMail: new FormControl(this.companySetting.adminMail),
          featureList: new FormControl(this.companySetting.featureList)
        });
        this.setReportData();
        this.notificationServiceService.hideProgressBar();
      }, error => {
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showErrorDialog(error);
      })
    }, error => {
      this.notificationServiceService.hideProgressBar();
      this.notificationServiceService.showErrorDialog(error);
    })
  }

  setReportData(){
            this.meta = [];
        this.meta.push(new ReportMeta("featureName", "name"));
        this.meta.push(new ReportMeta("description", "description"));
        this.meta.push(new ReportMeta("status", "id", this.companySetting.featureList, "featureToggle"));
        this.meta.push(new ReportMeta("actions", undefined,undefined,"featureToggleAction"));
  }

  updateToggle(iid){
    this.notificationServiceService.showProgressBar();
    this.httpService.getData("/CompanySetting/"+this.session.shareObj['wall']+"/"+iid).subscribe(
      result=>{
        this.notificationServiceService.hideProgressBar();
        this.companySetting = result;
        let temp=JSON.stringify( this.features);
        this.features=[]; 
        this.features = JSON.parse(temp);
        this.setReportData();
        this.notificationServiceService.showSaveDailog("companysetting","update",false);
      },
      error=>{
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showErrorDialog(error);
      }
    )
  }

  createUpdate(){
    this.companySetting = Object.assign(this.companySetting, ...this.csForm.value);
    this.notificationServiceService.showProgressBar();
    this.httpService.putData("/CompanySetting/",this.companySetting).subscribe(
      result=>{
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showSaveDailog("companySettingsave","update",false);
      },error =>{
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showErrorDialog(error);
      }
    )
  }

}
