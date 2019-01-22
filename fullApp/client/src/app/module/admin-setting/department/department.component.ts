import { Component, OnInit } from '@angular/core';
import { AdminService } from "../admin.service";
import { Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { ReportMeta } from "../../shared/shared/models";
import { HttpService } from "../../shared/shared/httpService";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.scss']
})
export class DepartmentComponent implements OnInit {
    meta: ReportMeta[] = [];
  constructor(private adminService:AdminService,private notificationService:NotificationServiceService, 
  private httpService:HttpService,private router:Router,public session:SessionService) { 
    this.adminService.adminSelectedItem='department';
    if (this.router.url.split("/")[this.router.url.split("/").length - 1] === 'department') {
      this.adminService.showDepartmentButton=true;
    }else{
      this.adminService.showDepartmentButton=false;
    }
  

  }
hideButtons(){
  this.adminService.showDepartmentButton=false;
}
  ngOnInit() {
    this.adminService.adminSelectedItem='department';
    this.adminService.fetchDepartment();
    this.meta=[];
     this.meta.push(new ReportMeta("Name", "name"));
    this.meta.push(new ReportMeta("Action", undefined));
  }

  hideMe(){
    this.adminService.showDepartmentButton=false;
  }

    hideMenu(){
        this.adminService.showDepartmentButton=false;
  }
    showMenu(){
        this.adminService.showDepartmentButton=true;
  }

  deletedRow(id)
{
  this.notificationService.showProgressBar("","");
  this.httpService.delete("/department/"+id.id).subscribe(result=>{
      this.notificationService.showSaveDailog("delete","delete",false);
      this.adminService.fetchDepartment();

    },
    error=>{
      this.notificationService.hideProgressBar();
      this.notificationService.showErrorDialog(error);
    }
  )
}
}
