import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from "@angular/forms";
import { User } from "../../../shared/shared/models";
import { SessionService } from "../../../shared/shared/SessionService";
import { AdminService } from "../../admin.service";
import { Router } from "@angular/router";
import { NotificationServiceService } from "../../../shared/shared/notification-service.service";

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.scss']
})
export class UserCreateComponent implements OnInit {

userForm:FormGroup;
user:User;
  constructor(private notificationServiceService:NotificationServiceService, private sessionService:SessionService,private adminService:AdminService,private route: Router) {
    this.createNewForm();
   }

  ngOnInit() {
    this.adminService.selectedItem='user';
  }

    createNewForm() {
    this.userForm = new FormGroup({
      name: new FormControl(),
      userName:new FormControl(),
      password:new FormControl()
    });
  }
  createUpdate(){
       this.user = new User();
      this.user.tenantId = this.sessionService.shareObj['wall'];
      this.user = Object.assign(this.user, ...this.userForm.value);
      this.adminService.createNewUser(this.user).subscribe(
        data => {
          this.notificationServiceService.showSaveDailog("user","create",true);
          this.route.navigate(['/admin/user/', this.user.id]);
        }
      )

  }
  cancel(){
      this.route.navigate(['/admin/user']);
  }
}
