import { Component, OnInit } from '@angular/core';
import { AdminService } from "../admin.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(public adminService:AdminService) { 
      this.adminService.adminSelectedItem='user';
  }
  ngOnInit() {
    this.adminService.adminSelectedItem='user';
  }

  
}

