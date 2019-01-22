import { Component, OnInit, Input, OnChanges, SimpleChanges, EventEmitter, Output } from '@angular/core';
import { Organization } from "../../shared/shared/models";
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { ActivatedRoute, Router } from "@angular/router";
import { Location } from '@angular/common';
import { AdminService } from "../admin.service";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'app-organization-create',
  templateUrl: './organization-create.component.html',
  styleUrls: ['./organization-create.component.scss']
})
export class OrganizationCreateComponent implements OnInit {

  buttonValue: string = '';
  @Input()
  showForm: boolean = false;
  header: string = '';
  @Input()
  status: string;
  orgForm: FormGroup;
  @Input()
  selectedOrg: Organization;


  constructor(private notificationService: NotificationServiceService, private rp: ActivatedRoute, private route: Router, private location: Location, private httpService: HttpService,
    private sessionService: SessionService, private adminService: AdminService) {
    rp.params.subscribe(p => {
      if (this.route.url) {
        if (this.route.url.split("/")[this.route.url.split("/").length - 2] === 'child') {
          this.status = 'child';
          this.buttonValue = 'Create';

        } else {
          this.status = 'edit';
          this.buttonValue = 'Update';
        }
      }
      if (p && p.id) {
        this.notificationService.showProgressBar("", "");
        this.httpService.getData("/Organization/" + p.id).subscribe(
          res => {
            this.notificationService.hideProgressBar();
            this.selectedOrg = res;
            if (this.status == 'child') {
              this.header = 'Creating a new child for Org: ' + this.selectedOrg.orgName;
              this.createNewForm();
              this.adminService.isOrgExapnded = false;
            } else {
              this.header = 'Updating organization : ' + this.selectedOrg.orgName;
              this.adminService.isOrgExapnded = false;
              this.orgForm = new FormGroup({
                orgName: new FormControl(res.orgName),
                locationId: new FormControl(res.locationId)
              });
            }

            this.showForm = true;
          },
          error => {
            this.notificationService.hideProgressBar();
            this.notificationService.showErrorDialog(error);
          }
        )
      } else {
        this.header = 'Creating a new Organization';
        this.status = 'new';
        this.buttonValue = 'Create';
        this.showForm = true;
        this.createNewForm();
        this.adminService.isOrgExapnded = false;
        this.selectedOrg = new Organization();
      }

    });

  }

  ngOnInit() {
  }


  childCreate(obj) {
    this.orgForm = new FormGroup({
      orgName: new FormControl(),
      locationId: new FormControl()
    });
  }
  editForm(obj) {
    console.log("in edit ok cool")

  }
  createNewForm() {
    this.orgForm = new FormGroup({
      orgName: new FormControl(),
      locationId: new FormControl()
    });
  }

  createUpdate() {
    this.notificationService.showProgressBar("", "");
    if (this.status == 'new' && this.selectedOrg) {
      this.selectedOrg = new Organization();
      this.selectedOrg.tenantId = this.sessionService.shareObj['wall'];
      this.selectedOrg = Object.assign(this.selectedOrg, ...this.orgForm.value);
      this.adminService.createNewOrg(this.selectedOrg).subscribe(
        data => {
          this.notificationService.hideProgressBar();
          this.adminService.isOrgExapnded = true;
          this.route.navigate(['/admin/setting/organization'], {});
        }, error => {
          this.notificationService.hideProgressBar();
          this.notificationService.showErrorDialog(error);
        }
      )
    } else if (this.status == 'edit' && this.selectedOrg) {
      //its an update
      let obj = new Organization();
      obj.tenantId = this.sessionService.shareObj['wall'];
      obj.parentOrgId = this.selectedOrg.id;
      obj.orgName = this.orgForm.value.orgName;
      obj.locationId = this.orgForm.value.locationId;
      this.selectedOrg = Object.assign(this.selectedOrg, ...this.orgForm.value);
      console.log("----edit save----");
      console.log(this.selectedOrg);
      this.adminService.updateOrg(this.selectedOrg).subscribe(
        data => {
          this.notificationService.hideProgressBar();
          this.adminService.fetchOrgList();
          this.adminService.isOrgExapnded = true;
          this.route.navigate(['/admin/setting/organization'], {});
        },
        error => {
          this.notificationService.hideProgressBar();
          this.notificationService.showErrorDialog(error);
        }
      );
    }
    else if (this.status == 'child') {
      let objs: Organization = new Organization();
      objs.parentOrgId = this.selectedOrg.id;
      objs.tenantId = this.selectedOrg.tenantId;
      objs = Object.assign(objs, ...this.orgForm.value);
        
      this.adminService.createNewOrg(objs).subscribe(
        data => {
            this.notificationService.hideProgressBar();
          this.adminService.isOrgExapnded = true;
        },error=>{
            this.notificationService.hideProgressBar();
          this.notificationService.showErrorDialog(error);
        }
      );

    }

    console.log(this.selectedOrg);
  }

  cancel() {
    this.selectedOrg = null;
    this.showForm = false;
    this.adminService.isOrgExapnded = true;
    this.adminService.showOrgCreateButton = true;
    this.route.navigate(['/admin/setting/organization'], {});
  }

}
