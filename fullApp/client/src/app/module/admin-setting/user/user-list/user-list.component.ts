import { Component, OnInit } from '@angular/core';
import { AdminService } from "../../admin.service";
import { Router } from "@angular/router";
import { SessionService } from "../../../shared/shared/SessionService";
import { ReportMeta } from "../../../shared/shared/models";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  meta: ReportMeta[] = [];
  constructor(private adminService:AdminService,private router:Router,public session:SessionService) { 
    this.adminService.adminSelectedItem='user';

    this.meta=[];
    this.meta.push(new ReportMeta("name", "name"));
    this.meta.push(new ReportMeta("emailId", "emailId"));
    this.meta.push(new ReportMeta("dob", "dob"));
    this.meta.push(new ReportMeta("doj", "doj"));
    this.meta.push(new ReportMeta("primaryContact", "primaryContact"));
    this.meta.push(new ReportMeta("actions", undefined));
  }
  ngOnInit() {

    this.adminService.adminSelectedItem='user';
    this.adminService.fetchUsers();
  }
}
