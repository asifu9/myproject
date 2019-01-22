import { Component, OnInit } from '@angular/core';
import { AdminService } from "../../../admin.service";
import { ActivatedRoute, Router } from "@angular/router";
import { HttpService } from "../../../../shared/shared/httpService";
import { Activities, AcitivityMap, User, RoleModel, Role, ReportMeta } from "../../../../shared/shared/models";
import { SessionService } from "../../../../shared/shared/SessionService";
import { NotificationServiceService } from "../../../../shared/shared/notification-service.service";


@Component({
  selector: 'app-user-activities',
  templateUrl: './user-activities.component.html',
  styleUrls: ['./user-activities.component.scss']
})
export class UserActivitiesComponent implements OnInit {
  roles: any[] = [];
  allRoles: Role[];
  displayRoles: Role[];
  meta: ReportMeta[] = [];
  metaSecond: ReportMeta[] = [];
  map = {};
  data: any;
  user: User;
  userId: string;
  selectedAllRoles: any[] = [];
  finalSelection: any[] = [];
  constructor(private ns: NotificationServiceService, private rp: ActivatedRoute, private session: SessionService, private route: Router, private httpService: HttpService) { }

  ngOnInit() {
    this.init();
  }

  init() {
    let data = this.route.url.split("/");
    this.userId = data[data.length - 1];
    this.ns.showProgressBar();
    this.httpService.getData("/User/" + data[data.length - 1]).subscribe(
      result => {
        this.user = result;
      });
    this.fetchAllRoles();

  }

  updateRolesInDB() {
    if (this.roles && this.roles.length > 0) {
      let roleString = [];
      this.roles.forEach(i => {
        roleString.push(i.name);
      })
      this.ns.showProgressBar();
      this.httpService.postData("/UserCredential/update/roles/" + this.userId, roleString).subscribe(
        result => {
          this.ns.hideProgressBar();
          this.ns.showSaveDailog("activies", "update", false);

        }
      )
    }
  }

  fetchAllRoles() {
    this.httpService.getData("/roles/wall/" + this.session.shareObj['wall']).subscribe
      (result => {
        this.allRoles = result;
        if (result) {
          result.forEach(element => {
            this.map[element.roleId] = element;
          });
        }
        this.fetchRoles();

      }, error => {
        this.ns.hideProgressBar();
        this.ns.showErrorDialog(error);
      });
  }

  fetchRoles() {
    let data = this.route.url.split("/");
    this.userId = data[data.length - 1]
    this.httpService.getData("/UserCredential/" + data[data.length - 1]).subscribe(
      result => {
        this.ns.hideProgressBar();
        this.data = result;
        this.tempRecords();
        this.meta = [];

        this.meta.push(new ReportMeta("name", "name"));
        this.meta.push(new ReportMeta("", "roleId", undefined, "checkbox"));

        this.metaSecond = []
        this.metaSecond.push(new ReportMeta("", "roleId", undefined, "checkbox"));
        this.metaSecond.push(new ReportMeta("name", "name"));
      }, error => {
        this.ns.hideProgressBar();
        this.ns.showErrorDialog(error);
      }
    );
  }

  tempRecords(){
    this.roles=[];
            if (this.data.roles) {
          this.data.roles.forEach(element => {
            this.roles.push(new Role(element, element));
          });
          this.updateMainRoles();
        }
  }


  checkUserRoles(data) {
    console.log(data);
    if (this.roles && this.roles.length > 0) {
      let rol = this.roles.filter(function (e) {
        return e.name === data;
      })
      if (rol && rol.length == 0) {
        this.roles.push(new Role(data, data));
        this.updateMainRoles();
      }
    } else {
      this.roles = [];
      this.roles.push(new Role(data, data));
    }
    //this.finalSelection.push(...data);
  }

  removedRoles(data) {
    this.roles = this.roles.filter(function (i) {
      return i.name !== data;
    });
    this.updateMainRoles();
  }

  updateMainRoles() {
    this.displayRoles = JSON.parse(JSON.stringify(this.allRoles));
    if (this.roles && this.roles.length > 0) {
      this.roles.forEach(e => {
        this.displayRoles = this.displayRoles.filter(function (obj) {
          return obj.name !== e.name;
        });
      });
    }
    console.log(this.allRoles);
    console.log(this.displayRoles);
    console.log(this.roles);
  }

  getKeys(map) {
    return Array.from(map.keys());
  }


  updatedList(d) {
    console.log("update list");
    console.log(d);
    if (d && this.data && this.data.roles) {
      for (let obj of d) {
        if (this.data.roles.indexOf(obj) === -1) {
          this.data.roles.push(obj);
          this.roles.push(new Role(obj, obj));
        } 
      }
    }

    this.tempRecords();
  }

  updatedReverseList(item) {
    console.log("data is here "+item);
     if (item && this.data && this.data.roles) {
      
        if (this.data.roles.indexOf(item) !== -1) {
          this.data.roles.splice(this.data.roles.indexOf(item),1);
        } 
        this.tempRecords();
      
    }
    // if(this.members){
    //   if(this.members.indexOf(data)!==-1){
    //     this.members.splice(this.members.indexOf(data),1);
    //   }
    // }
    // this.updatedList(this.members);
  }

}
