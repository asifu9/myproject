import { Injectable } from '@angular/core';
import { Organization, Designation, Department, User, Team } from "../shared/shared/models";
import { HttpService } from "../shared/shared/httpService";
import { SessionService } from "../shared/shared/SessionService";
import { NotificationServiceService } from "../shared/shared/notification-service.service";

@Injectable()
export class AdminService {

  orgList: Organization[] = new Array<Organization>();
  desgList: Designation[] = new Array<Designation>();
  departList: Department[] = new Array<Department>();
  teamList: Team[] = new Array<Team>();
  userList: User[] = new Array<User>();
  isOrgExapnded: boolean = true;
  showOrgCreateButton: boolean = true;
  showOrgDesignationButton: boolean = true;
  showEditOrganization: boolean = true;
  showTeamButton: boolean = true;
  showDepartmentButton: boolean = true;
  isDesgExapnded: boolean = true;
  selectedUser: User;
  selectedItem: string = 'basic';
  adminSelectedItem: string = 'organization';


  constructor(private httpService: HttpService, private sessionService: SessionService, private notificationService: NotificationServiceService) { }

  fetchOrgList() {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData('/Organization/tenant/' + this.sessionService.shareObj['wall']).subscribe(
      data => {
        this.orgList = data;
        this.notificationService.hideProgressBar();
      },
      error =>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );
  }

  getUserById(id) {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData('/User/' + id).subscribe(
      data => {
        this.selectedUser = data;
        this.notificationService.hideProgressBar();
      },
      error =>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );
  }
  getUserByIdSync(id) {
    return this.httpService.getData('/User/' + id);
  }
  fetchUsers() {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData('/User/wall/' + this.sessionService.shareObj['wall']).subscribe(
      data => {
        this.userList = data;
        this.notificationService.hideProgressBar();
      },
      error =>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );
  }


  fetchDepartment() {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData('/department/tenant/' + this.sessionService.shareObj['wall']).subscribe(
      data => {
        this.departList = data;
        this.notificationService.hideProgressBar();
      },
      error =>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );
  }
  fetchTeam() {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData('/team/tenant/' + this.sessionService.shareObj['wall']).subscribe(
      data => {
        this.teamList = data;
        this.notificationService.hideProgressBar();
      },
      error =>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );
  }


  fetchDesignationList() {
    this.notificationService.showProgressBar("", "");
    this.httpService.getData('/designation/tenant/' + this.sessionService.shareObj['wall']).subscribe(
      data => {
        this.desgList = data;
        this.notificationService.hideProgressBar();
      },
      error =>{
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    );
  }

  fetchDesignationListAsyn() {
    return this.httpService.getData('/designation/tenant/' + this.sessionService.shareObj['wall']);
  }

  fetchOrgListAsyn() {
    return this.httpService.getData('/Organization/tenant/' + this.sessionService.shareObj['wall']);

  }
  fetchUserAsync() {
    return this.httpService.getData('/User/wall/' + this.sessionService.shareObj['wall']);
  }
  fetchTeamAsync() {
    return this.httpService.getData('/team/tenant/' + this.sessionService.shareObj['wall']);
  }
  fetchDepartmentAsync() {
    return this.httpService.getData('/department/tenant/' + this.sessionService.shareObj['wall']);
  }
  createNewOrg(org: Organization) {
    return this.httpService.postData('/Organization/', org);
  }
  createNewTeam(org: Team) {
    return this.httpService.postData('/team/', org);
  }
  createNewUser(user: User) {
    return this.httpService.postData('/User/create/', user);
  }
  updateUser(user: User) {
    return this.httpService.putData('/User/', user);
  }
  updateUserDetails(userId, type, value) {
    return this.httpService.postData(`/User/update/${userId}/${type}/${value}`, null);
  }
  createNewDesg(desg: Designation) {
    return this.httpService.postData('/designation/', desg);
  }
  updateOrg(org: Organization) {
    return this.httpService.putData('/Organization/', org);
  }
  updateDesignation(desg: Designation) {
    return this.httpService.putData('/designation/', desg);
  }
  updateTeam(desg: Team) {
    return this.httpService.putData('/team/', desg);
  }
  createNewDepartment(department: Department) {
    return this.httpService.postData('/department/', department);
  }
  updateDeparment(department: Department) {
    return this.httpService.putData('/department/', department);
  }

}
