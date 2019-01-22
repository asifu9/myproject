import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Organization } from "../../shared/shared/models";
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { Router } from '@angular/router';
import { AdminService } from "../admin.service";
@Component({
  selector: 'app-organization',
  templateUrl: './organization.component.html',
  styleUrls: ['./organization.component.scss']
})

export class OrganizationComponent implements OnInit, AfterViewInit {
  
  status: string = '';
  showForm: boolean = false;
  selectedOrg: Organization;
  
  constructor(private adminService: AdminService,private router:Router,public session:SessionService) {
    this.adminService.fetchOrgList();
    if (this.router.url.split("/")[this.router.url.split("/").length - 1] === 'organization') {
       this.adminService.isOrgExapnded=true;
      this.adminService.showOrgCreateButton=true;
    }else{
      this.adminService.isOrgExapnded=false;
      this.adminService.showOrgCreateButton=false;
    }
  }

toggleSize(){
  
  if (this.router.url.split("/")[this.router.url.split("/").length - 1] === 'organization') {
      if(this.adminService.isOrgExapnded){
        this.adminService.showOrgCreateButton=true;
      }else{
        this.adminService.showOrgCreateButton=true;
      }
      this.adminService.isOrgExapnded=!this.adminService.isOrgExapnded;

  }else{
    this.adminService.showOrgCreateButton=false;
    this.adminService.isOrgExapnded=false;
  }
}
setExpandSize(){
  this.adminService.isOrgExapnded=false;
  this.adminService.showOrgCreateButton=false;
}

  newOrgCreate() {
    this.status = '';
    this.selectedOrg = new Organization();
    this.selectedOrg = null;
    this.showForm = true;
    this.status = 'new';
    this.adminService.showOrgCreateButton=false;
  }

  createChild(org) {
    this.status = '';
    this.status = 'child';
    this.selectedOrg = null;
    this.selectedOrg = org;
    this.showForm = true;
    this.adminService.showOrgCreateButton=false;
  }
  updateOrg(org) {
    this.status = '';
    this.status = 'edit';
    this.selectedOrg = null;
    this.selectedOrg = org;
    this.showForm = true;
    this.adminService.showOrgCreateButton=false;
  }

  ngOnInit() {

  }
  ngAfterViewInit() {
  }

  hideMe(){
        this.adminService.isOrgExapnded=false;
        this.adminService.showOrgCreateButton=false;
  }
    showMe(){
        this.adminService.isOrgExapnded=true;
        this.adminService.showOrgCreateButton=true;
  }
}
