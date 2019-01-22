import { Component, OnInit } from '@angular/core';
import { AdminService } from "../admin.service";
import { Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { RoleModel, Role, ReportMeta } from "../../shared/shared/models";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss']
})
export class RolesComponent implements OnInit {
  meta: ReportMeta[] = [];
  roles:Role[]=[];
  constructor(private notificationService:NotificationServiceService,private adminService:AdminService,private httpService:HttpService,private router:Router,public session:SessionService) { 
      this.meta=[];
     this.meta.push(new ReportMeta("name", "name"));
     this.meta.push(new ReportMeta("type", "type"));
    this.meta.push(new ReportMeta("Action", undefined));
  

  }
hideButtons(){

  this.adminService.showDepartmentButton=false;
}
  ngOnInit() {
    this.fetchData();
  }

  fetchData(){
        this.notificationService.showProgressBar("","");
    this.httpService.getData("/roles/wall/"+this.session.shareObj['wall']).subscribe(
      result=>{

        this.roles=result;
        this.notificationService.hideProgressBar();
      },error=>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    )
  }

 deletedRow(id)
{
  this.notificationService.showProgressBar("","");
  this.httpService.delete("/roles/"+id.id).subscribe(result=>{
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
