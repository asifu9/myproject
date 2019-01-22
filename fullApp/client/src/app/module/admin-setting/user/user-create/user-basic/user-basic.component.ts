import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpService } from "../../../../shared/shared/httpService";
import { Util } from "../../../../shared/util";
import { User } from "../../../../shared/shared/models";
import { FormGroup, FormControl } from "@angular/forms";
import { AdminService } from "../../../admin.service";
import { SessionService } from "../../../../shared/shared/SessionService";
import { NotificationServiceService } from "../../../../shared/shared/notification-service.service";

@Component({
  selector: 'app-user-basic',
  templateUrl: './user-basic.component.html',
  styleUrls: ['./user-basic.component.scss']
})
export class UserBasicComponent implements OnInit, OnDestroy {

  userForm: FormGroup = this.createNewForm();
  sub: any;
  user: User;
  userId: string;
  selectedYear: string;
  constructor(private notificationServiceService: NotificationServiceService, private rp: ActivatedRoute, private route: Router, private httpService: HttpService, private session: SessionService) { 
      this.route.routeReuseStrategy.shouldReuseRoute = function() {
        return false;
    };
  }
  generateLeaves() {
    this.httpService.getData("/userleaves/" + this.userId + "/" + this.session.shareObj['wall'] + "/" + this.selectedYear + "/generate").subscribe
      (result => {
        console.log(result);
      })
  }
  ngOnInit() {
       this.route.routeReuseStrategy.shouldReuseRoute = function() {
        return false;
    };
    let data = this.route.url.split("/");
    if (data.length > 0) {
      this.userId = data[data.length - 1];
      this.notificationServiceService.showProgressBar();
      this.httpService.getData("/User/" + data[data.length - 1]).subscribe(res => {
        this.notificationServiceService.hideProgressBar();
        this.user = res;
        this.createNewForm();
      },error=>{
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showErrorDialog(error);
      });


    }

  }

  ngOnDestroy() {
  }

  createNewForm() {
    if (this.user) {
      this.userForm = new FormGroup({
        name: new FormControl(this.user.name),
        dob: new FormControl(Util.convertDate(this.user.dob)),
        doj: new FormControl(Util.convertDate(this.user.doj)),
        emailId: new FormControl(this.user.emailId),
        primaryContact: new FormControl(this.user.primaryContact),
        secondaryContact: new FormControl(this.user.secondaryContact),
      });
    } else {
      this.userForm = new FormGroup({
        name: new FormControl(),
        dob: new FormControl(),
        doj: new FormControl(),
        emailId: new FormControl(),
        primaryContact: new FormControl(),
        secondaryContact: new FormControl(),
      });
    }
    return this.userForm;
  }
  changedMe() {
    console.log(this.userForm.value);
  }
  createUpdate() {

    this.user = Object.assign(this.user, ...this.userForm.value);
    console.log("before update of user is ");
    console.log(this.userForm.value.dob);
    var d = new Date(this.user.dob);
    this.user.dob = Util.convertDateToMili(this.userForm.value.dob);
    this.user.doj = Util.convertDateToMili(this.userForm.value.doj);
    console.log(d.getTime());
    console.log(this.user);
    this.notificationServiceService.showProgressBar();
    this.httpService.putData("/User/", this.user).subscribe(
      data => {
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showSaveDailog("user", "update",false);
        //this. user=data;
      },error=>
      {
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showErrorDialog(error);
      }
    )
  }
}
