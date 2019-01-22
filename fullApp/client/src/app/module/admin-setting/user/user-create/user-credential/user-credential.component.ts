import { Component, OnInit } from '@angular/core';
import { AdminService } from "../../../admin.service";
import { FormGroup, FormControl } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { HttpService } from "../../../../shared/shared/httpService";
import { Util } from "../../../../shared/util";
import { SessionService } from "../../../../shared/shared/SessionService";
import { User, RoleModel, Role } from "../../../../shared/shared/models";
import { NotificationServiceService } from "../../../../shared/shared/notification-service.service";

@Component({
  selector: 'app-user-credential',
  templateUrl: './user-credential.component.html',
  styleUrls: ['./user-credential.component.scss']
})
export class UserCredentialComponent implements OnInit {
  roles: RoleModel[] = [];
  mainRoles: Role[];
  selectedAllRoles:any[]=[];
  finalSelection:any[]=[];
  credForm: FormGroup = new FormGroup({
    userName: new FormControl(""),
    password: new FormControl(""),
    confirmPassword: new FormControl(""),
    currentPassword: new FormControl("")
  });
  sub: any;
  data: any;
  user: User;
  userId: string;
  //sorting




  constructor(private notificationServiceService:NotificationServiceService,private session: SessionService, private rp: ActivatedRoute, private route: Router, private httpService: HttpService) { }

  ngOnInit() {
    this.init();
    console.log("init in credentioal");
  }

  init() {
    console.log("ok here in credentioal init");
    let data = this.route.url.split("/");
    this.userId=data[data.length-1];
    if (data.length > 0) {
      console.log("path in photo " + data[data.length - 1]);
      this.userId = data[data.length - 1];
      this.notificationServiceService.showProgressBar();
      this.httpService.getData("/UserCredential/" + data[data.length - 1]).subscribe(
        result => {
          this.notificationServiceService.hideProgressBar();
          this.data=result;
          this.credForm = new FormGroup({
            userName: new FormControl(result.username),
            password: new FormControl(result.password),
            currentPassword: new FormControl(""),
            confirmPassword: new FormControl("")
          });
        },error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      );

      this.httpService.getData("/User/" + data[data.length - 1]).subscribe(
        result => {
          this.user = result;
          //this.notificationServiceService.hideProgressBar();
        });
    }



  }


  createNewForm() {
    if (this.data) {
      this.credForm = new FormGroup({
        userName: new FormControl(this.data.userName),
        password: new FormControl(this.data.password),
        confirmPassword: new FormControl(""),
        currentPassword: new FormControl(""),
        roles:new FormControl(this.data.roles)
      });
    }
  }

  moveToRight(){
    this.credForm.value.roles=this.finalSelection;
  }
  //Thu Apr 19 2018 00:00:00 GMT+0530 (IST)
  createUpdate() {
    //04/19/2018
    //user-credential.component.ts:61 Fri Apr 13 2018 20:06:20 GMT+0530 (IST)
    console.log(this.credForm);
    if (this.credForm.value.confirmPassword != this.credForm.value.password) {
      alert("password and confirm password not matching");
      return;
    }
    if (!this.credForm.value.currentPassword || this.credForm.value.currentPassword == "") {
      alert("Current password need to set");
      return;
    }
    this.data = Object.assign(this.data, ...this.credForm.value);
    this.notificationServiceService.showProgressBar();
    console.log("before update");
    this.httpService.putData("/UserCredential/", this.data).subscribe(
      result => {
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showSaveDailog("credential","update",false);
        this.credForm = new FormGroup({
          userName: new FormControl(this.data.username),
          password: new FormControl(),
          currentPassword: new FormControl(""),
          confirmPassword: new FormControl(""),
          roles:new FormControl(this.data.roles)
        });

      },
      error => {
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showErrorDialog(error);
      }
    )
  }

}
