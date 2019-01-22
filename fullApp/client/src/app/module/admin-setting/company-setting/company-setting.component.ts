import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { CompanySetting } from "../../shared/shared/models";
import { FormGroup, FormControl } from "@angular/forms";

@Component({
  selector: 'app-company-setting',
  templateUrl: './company-setting.component.html',
  styleUrls: ['./company-setting.component.scss']
})
export class CompanySettingComponent implements OnInit {

  companySetting:CompanySetting;
  companySettingForm: FormGroup;
  constructor(private httpService:HttpService,private router:Router,public session:SessionService) { }
  ngOnInit() {
    this.fetchData();
  }

  fetchData(){
    this.httpService.getData("/CompanySetting/"+this.session.shareObj['wall']).subscribe(
      result=>{
        if(result){
          this.companySetting=result;
        }else{
          this.companySetting=new CompanySetting();
        }
        this.buildForm();
      }
    )
  }

  buildForm(){
     this.companySettingForm = new FormGroup({
                dateFormat: new FormControl(   this.companySetting.dateFormat),
                leaveStartEndMonths: new FormControl(   this.companySetting.leaveStartEndMonths),
                adminMail: new FormControl(   this.companySetting.adminMail)});

    
  }

createUpdate(){
    this.companySetting.id = this.session.shareObj['wall'];
      this.companySetting = Object.assign(this.companySetting, ...this.companySettingForm.value);
      this.companySetting.lastUpdatedBy = this.session.shareObj['user'];
     this.httpService.postData("/CompanySetting/",this.companySetting).subscribe(
       data=>{
         //this.route.navigate(['/admin/leavetype'], { });
        }
     )
}

}
