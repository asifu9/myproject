import { Component, OnInit,ViewChild } from '@angular/core';
import { AdminService } from "../admin.service";
import { Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { ReportMeta } from "../../shared/shared/models";

@Component({
  selector: 'app-designation',
  templateUrl: './designation.component.html',
  styleUrls: ['./designation.component.scss']
})
export class DesignationComponent implements OnInit {
    meta: ReportMeta[] = [];
  constructor(private adminService:AdminService,private router:Router,public session:SessionService) { 

      this.meta.push(new ReportMeta("name", "designationName"));
    this.meta.push(new ReportMeta("actions", undefined));

    this.adminService.adminSelectedItem='designation';
    if (this.router.url.split("/")[this.router.url.split("/").length - 1] === 'designation') {
      this.adminService.showOrgDesignationButton=true;
    }else{
      this.adminService.showOrgDesignationButton=false;
    }
  

  }
hideButtons(){
      this.adminService.showOrgDesignationButton=false;
}
  ngOnInit() {
    this.adminService.adminSelectedItem='designation';
    this.adminService.fetchDesignationList();
  }

  hideMe(){
    this.adminService.showOrgDesignationButton=false;
  }

    hideMenu(){
        this.adminService.showOrgDesignationButton=false;
  }
    showMenu(){
        this.adminService.showOrgDesignationButton=true;
  }
}
