import { Component, OnInit, Input } from '@angular/core';
import { LeaveApply, LeaveDetails } from "../../shared/shared/models";
import { FormGroup, FormControl } from "@angular/forms";
import { Util } from "../../shared/util";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute } from "@angular/router";
import { Location } from '@angular/common'; 
import { MatDialog } from "@angular/material";
import { SaveConfirmationComponent } from "../../shared/shared/save-confirmation/save-confirmation.component";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";
@Component({
  selector: 'app-leave-create',
  templateUrl: './leave-create.component.html',
  styleUrls: ['./leave-create.component.scss']
})
export class LeaveCreateComponent implements OnInit {
  createLeave: LeaveApply;
  id: string;
  leaveTypes: LeaveDetails[];
  createLeaveForm: FormGroup;
  isHalfDay: boolean = false;
  managerId: string;
  header: string = "";
  isFetched = false;
  editEnabled:boolean=true;

  createNewForm() {
    this.header='createNewLeave';
    if (this.createLeave) {
      this.createLeaveForm = new FormGroup({
        leaveName: new FormControl(this.createLeave.leaveName),
        startDate: new FormControl(Util.convertDate(this.createLeave.startDate)),
        endDate: new FormControl(Util.convertDate(this.createLeave.endDate)),
        days: new FormControl(this.createLeave.days),
        description: new FormControl(this.createLeave.description),
        halfDay: new FormControl(this.createLeave.halfDay),
      });
      this.isHalfDay = this.createLeave.halfDay;

    } else {
      this.createLeaveForm = new FormGroup({
        leaveName: new FormControl(),
        startDate: new FormControl(),
        endDate: new FormControl(),
        days: new FormControl(0),
        description: new FormControl(),
        halfDay: new FormControl()
      });
      this.createLeave=new LeaveApply();
    }


  }
  toggle() {
    if (this.createLeaveForm.value.halfDay) {
      this.isHalfDay = true;
      this.createLeaveForm.controls['endDate'].setValue(0);
      this.createLeaveForm.controls['days'].setValue(0.5);
    } else {
      this.isHalfDay = false;
    }
  }
  changeMe(val) {
    if (this.createLeaveForm.value.startDate && this.createLeaveForm.value.endDate) {
      let start = Util.convertDateToMili(this.createLeaveForm.value.startDate);
      let end = Util.convertDateToMili(this.createLeaveForm.value.endDate);
      let diff = end - start;
      let result = diff / (60 * 60 * 24);
      this.createLeaveForm.controls['days'].setValue(result + 1);
    }

  }

  constructor(private translationService:TranslationService, private notificationServiceService:NotificationServiceService,private session: SessionService, private httpService: HttpService,private locaion:Location, private activatedRoute: ActivatedRoute) {
    this.id = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit() {
    this.session.updateMenu("leaves");
    this.createNewForm();
    this.fetchData();
    this.fetchUserLeavesData();
    this.getManagerDetails();
  }

  fetchUserLeavesData() {
    this.httpService.getData("/userleaves/" + this.session.shareObj['user'] + "/" + Util.getFinancialYear(this.session)).subscribe(result => {
      if (result) {
        this.leaveTypes = result;
      }
    });
  }

  fetchData() {
    if (this.id) {
      this.httpService.getData("/leaveapply/" + this.id).subscribe(
        result => {
          if (result) {
            let data: any = result;
            this.createLeave = data;
            if(this.createLeave.status==='Approved'){
              this.editEnabled=false;
            }
            this.createNewForm();
          } else {
            this.createNewForm();
          }
        }
      )
    } else {
      this.createNewForm();
    }
  }

  createUpdate() {
    console.log(this.createLeaveForm.value);

    this.createLeave = Object.assign(this.createLeave, this.createLeaveForm.value);
    if (!this.createLeave.status) {
      this.createLeave.status = "Waiting for Approval";
    } else if (this.createLeave.status === 'Rejected' || this.createLeave.status === 'Waiting for Approval') {
      this.createLeave.status = "Waiting for Approval";
    }
    if (this.createLeave.startDate) {
      this.createLeave.startDate = Util.convertDateToMili(this.createLeave.startDate);
    }
    if (this.createLeave.endDate) {
      this.createLeave.endDate = Util.convertDateToMili(this.createLeave.endDate);
    }
    if (!this.createLeave.id) {
      this.createLeave.companyId = this.session.shareObj['wall'];
      this.createLeave.userId = this.session.shareObj['user'];
      this.createLeave.assignedTo = this.managerId;
      if (this.session.currentSetting.leaveStartEndMonths) {
        if ("Jan-Dec" == this.session.currentSetting.leaveStartEndMonths) {
          this.createLeave.financialYear = Util.getFinancialYear(this.session);
        }
      }

      this.httpService.postData("/leaveapply/", this.createLeave).subscribe(
        result => {
            this.notificationServiceService.showSaveDailog("leave","create",true);
        }
      )
    } else {
      this.httpService.putData("/leaveapply/", this.createLeave).subscribe(
        result => {
          this.notificationServiceService.showSaveDailog("leave","updated",true);
        }
      )
    }

    console.log(this.createLeave);
  }

  getManagerDetails() {
    this.httpService.getData("/UserLinkInfo/" + this.session.shareObj['user']).subscribe(
      result => {
        if (result) {
          this.managerId = result.managerId;
        }
      }
    )
  }

  cancel(){
    this.locaion.back();
  }

}
