import { Component, OnInit,Input } from '@angular/core';
import { Organization } from "../shared/shared/models";
import { AdminService } from "./admin.service";
import { SessionService } from "../shared/shared/SessionService";

@Component({
  selector: 'app-admin-setting',
  templateUrl: './admin-setting.component.html',
  styleUrls: ['./admin-setting.component.scss']
})
export class AdminSettingComponent implements OnInit {

@Input()
selectedOrg:Organization;

  constructor(private adminService:AdminService,public session:SessionService) { }

  ngOnInit() {
  }

}
