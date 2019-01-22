import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpService } from "../../../../shared/shared/httpService";
import { User } from "../../../../shared/shared/models";
import { AdminService } from "../../../admin.service";
import { NotificationServiceService } from "../../../../shared/shared/notification-service.service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit, AfterViewChecked {
  constructor(private ns:NotificationServiceService,private rp: ActivatedRoute, private route: Router, private httpService: HttpService, public adminService: AdminService) { }
  selectedItem: string = '';
  userId:string='';
  
  ngOnInit() {
    //this.getUserId();
    let data = this.route.url.split("/");
    this.selectedItem = data[data.length - 1];
    this.userId=data[data.length-1];
    if (data.length > 0) {
      if ('basic' === data[data.length - 1]) {
        if (!this.adminService.selectedUser) {
          this.adminService.getUserById(data[data.length - 2]);
          this.selectedItem = data[data.length - 1];
        }
      }
    }
  }

  ngAfterViewChecked() {
    if (!this.adminService.selectedUser) {
      this.getUserId();

    }
  }

  getUserId() {
    this.rp.params.subscribe(p => {
      if (p && p.id) {
        this.ns.showProgressBar();
        this.httpService.getData("/User/" + p.id).subscribe(
          data => {
            this.ns.hideProgressBar();

            this.adminService.selectedUser = data;
          },error=>{
            this.ns.hideProgressBar();
            this.ns.showErrorDialog(error);
          });
      }

    });
  }
}
