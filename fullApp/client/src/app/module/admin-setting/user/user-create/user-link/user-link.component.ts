
import {forkJoin as observableForkJoin} from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { AdminService } from "../../../admin.service";
import { Department, User, UserLinkInfo, Team, Designation } from "../../../../shared/shared/models";
import { HttpService } from "../../../../shared/shared/httpService";
import { SessionService } from "../../../../shared/shared/SessionService";
import { Router, RouteReuseStrategy } from "@angular/router";
import { Observable } from 'rxjs';
import { HttpEventType } from "@angular/common/http";

@Component({
  selector: 'app-user-link',
  templateUrl: './user-link.component.html',
  styleUrls: ['./user-link.component.scss']
})
export class UserLinkComponent implements OnInit {
  selectedDepartment: string;
  selectedDesignation: string;
  selectedTeam: string;
  selectedOrganization: string;
  selectedManager: string;
  showEditDesignation: boolean = false;
  showEditDepartment: boolean = false;
  showEditTeam: boolean = false;
  showEditOrganization: boolean = false;
  showEditManager: boolean = false;
  managerName: string;
  managerUserList: User[] = new Array<User>();
  currentUserId: string;
  userLinkInfo: UserLinkInfo;
  departList: any;
  teamList: any;
  desgList: any;
  userList: any;
  user: User;
  userId:string;
  constructor( private httpService: HttpService, private sessionService: SessionService, private router: Router) {
    console.log("in user link stle ok");
    this.loadData();

  }

  loadData() {
    let data = this.router.url.split("/");
    this.userId=data[data.length-1];
    let tasks$ = [];
    console.log("ok now lets start all");
    tasks$.push(this.httpService.getData('/department/tenant/' + this.sessionService.shareObj['wall']));
    tasks$.push(this.httpService.getData('/team/tenant/' + this.sessionService.shareObj['wall']));
    tasks$.push(this.httpService.getData('/designation/tenant/' + this.sessionService.shareObj['wall']));
    tasks$.push(this.httpService.getData('/User/wall/' + this.sessionService.shareObj['wall']));
    observableForkJoin(...tasks$).subscribe(results => {
      console.log(results);
      console.log("ok now lets start all final");
      this.departList = results[0];
      this.teamList = results[1];
      this.desgList = results[2];
      this.userList = results[3];
      this.httpService.getData("/UserLinkInfo/" + this.userId).subscribe(
        result => {
          this.userLinkInfo = result;
          if (this.userLinkInfo) {
            if (this.userLinkInfo.departmentId) {
              this.showEditDepartment = true;
            }
            if (this.userLinkInfo.designationId) {
              this.showEditDesignation = true;
            }
            if (this.userLinkInfo.teamId) {
              this.showEditTeam = true;
            }
            if (this.userLinkInfo.managerId) {
              this.showEditManager = true;
            }
          }

        }
      )
    });
  }

  checkObject() {
    if (!this.userLinkInfo) {
      this.userLinkInfo = new UserLinkInfo();
      this.userLinkInfo.id = this.userId;
      this.userLinkInfo.tenantId = this.sessionService.shareObj['wall'];
    }
  }
  onDepartmentSelected(e) {
    console.log(e);
    this.checkObject();
    this.userLinkInfo.departmentId = e;
    
    this.httpService.postData("/UserLinkInfo/", this.userLinkInfo).subscribe(event => {
      if(event.type==HttpEventType.Response){
        let result:any=event.body;
      this.showEditDepartment = true;
      this.userLinkInfo = result;
      }
    })

  }
  onDesignationSelected(e) {
    console.log(e);
    // this.adminService.updateUserDetails(this.adminService.selectedUser.id, 'designation', e).subscribe(result => {
    //   this.showEditDesignation = true;
    //   this.updateDesignationLabel();
    // });
    this.checkObject();
    this.userLinkInfo.designationId = e;
    this.httpService.postData("/UserLinkInfo/", this.userLinkInfo).subscribe(event => {
      if(event.type==HttpEventType.Response){
        let result:any=event.body;
      this.showEditDesignation = true;
      this.userLinkInfo = result;
      }
      // this.updateDesignationLabel();
    })

  }


  onManagerSelected(e) {
    console.log(e);
    // this.adminService.updateUserDetails(this.adminService.selectedUser.id, 'manager', e).subscribe(result => {
    //   this.showEditManager = true;
    //   this.updateManagerLabel();
    // });

    this.checkObject();
    this.userLinkInfo.managerId = e;
    this.httpService.postData("/UserLinkInfo/", this.userLinkInfo).subscribe(event => {
      if(event.type==HttpEventType.Response){
      let result:any=event.body;
      this.showEditManager = true;
      this.userLinkInfo = result;
      }
      //this.updateManagerLabel();
    })

  }
  onTeamSelected(e) {
    console.log(e);
    // this.adminService.updateUserDetails(this.adminService.selectedUser.id, 'team', e).subscribe(result => {
    //   this.showEditTeam = true;
    //   this.updateTeamLabel();
    // });

    this.checkObject();
    this.userLinkInfo.teamId = e;
    this.httpService.postData("/UserLinkInfo/", this.userLinkInfo).subscribe(event => {
      if(event.type==HttpEventType.Response){
        let result:any=event.body;
      this.showEditTeam = true;
      this.userLinkInfo = result;
      }
      //this.updateTeamLabel();
    })

  }
  ngOnInit() {
    this.init();
  }

  init() {
    let data = this.router.url.split("/");
    this.userId=data[data.length-1];
    if (data.length > 0) {

      this.httpService.getData("/User/" + data[data.length - 1])
        .subscribe(result => {
          this.user = result;
        })


    } else {
      console.log("not data in link");
    }
  }
  editDepartment() {
    this.showEditDepartment = false;
  }
  editOrganization() {
    this.showEditOrganization = false;
  }
  editDesignation() {
    this.showEditDesignation = false;
  }
  editTeam() {
    this.showEditTeam = false;
  }
  editManager() {
    this.showEditManager = false;
  }
  // updateDepartmentLabel() {
  //   this.adminService.fetchDepartmentAsync().subscribe(data => {
  //     this.adminService.departList = data;
  //     if (data && data.length) {
  //       let isFound = false;
  //       let element: any;
  //       for (let i = 0; i < this.adminService.departList.length; i++) {
  //         element = this.adminService.departList[i];
  //         if (this.adminService.selectedUser.departmentId) {
  //           if (element && element.id == this.adminService.selectedUser.departmentId) {
  //             this.selectedDepartment = element.name;
  //             isFound = true;
  //           }
  //         }
  //       }
  //       if (isFound) {
  //         this.showEditDepartment = true;
  //       }
  //     }
  //   });
  // }

  //   updateDesignationLabel() {
  //   this.adminService.fetchDesignationListAsyn().subscribe(data => {
  //     this.adminService.desgList = data;
  //     if (data && data.length) {
  //       let isFound = false;
  //       let element: any;
  //       for (let i = 0; i < this.adminService.desgList.length; i++) {
  //         element = this.adminService.desgList[i];
  //         if (this.adminService.selectedUser.designationId) {
  //           if (element && element.id == this.adminService.selectedUser.designationId) {
  //             this.selectedDesignation = element.designationName;
  //             isFound = true;
  //           }
  //         }
  //       }
  //       if (isFound) {
  //         this.showEditDesignation = true;
  //       }
  //     }
  //   });
  // }

  //     updateTeamLabel() {
  //   this.adminService.fetchTeamAsync().subscribe(data => {
  //     this.adminService.teamList = data;
  //     if (data && data.length) {
  //       let isFound = false;
  //       let element: any;
  //       for (let i = 0; i < this.adminService.teamList.length; i++) {
  //         element = this.adminService.teamList[i];
  //         if (this.adminService.selectedUser.teamId) {
  //           if (element && element.id == this.adminService.selectedUser.teamId) {
  //             this.selectedTeam = element.name;
  //             isFound = true;
  //           }
  //         }
  //       }
  //       if (isFound) {
  //         this.showEditTeam = true;
  //       }
  //     }
  //   });
  // }
  // updateOrganizationLabel() {
  //   this.adminService.fetchOrgListAsyn().subscribe(data => {
  //     this.adminService.orgList = data;
  //     if (data && data.length) {
  //       let isFound = false;
  //       let element: any;
  //       for (let i = 0; i < this.adminService.orgList.length; i++) {
  //         element = this.adminService.orgList[i];
  //         if (this.adminService.selectedUser.organizationId) {
  //           if (element && element.id == this.adminService.selectedUser.organizationId) {
  //             this.selectedOrganization = element.orgName;
  //             isFound = true;
  //           }
  //         }
  //       }
  //       if (isFound) {
  //         this.showEditOrganization = true;
  //       }
  //     }
  //   });
  // }

  //   updateManagerLabel() {
  //   if(this.adminService.userList && this.adminService.userList.length>0){
  //     this.adminService.userList.forEach(i=>{
  //       if(!(this.currentUserId==i.id)){
  //         this.managerUserList.push(i);
  //       }
  //     })

  //   }else{
  //     this.adminService.fetchUserAsync().subscribe(result=>{
  //         result.forEach(i=>{
  //       if(!(this.currentUserId==i.id)){
  //         this.managerUserList.push(i);
  //       }
  //     })
  //     })
  //   }
  // this.adminService.getUserByIdSync(this.adminService.selectedUser.managerId).subscribe(data => {
  //   if (data) {

  //     let isFound = false;
  //     if(data){
  //       isFound=true;
  //       this.showEditManager=true;
  //       this.managerName=data.name;
  //     }
  //     if (isFound) {
  //       this.showEditOrganization = true;
  //     }
  //   }
  // });
  //}

}
