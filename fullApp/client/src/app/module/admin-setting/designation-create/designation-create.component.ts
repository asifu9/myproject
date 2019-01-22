import { Component, OnInit, Input, OnChanges, SimpleChanges, EventEmitter, Output } from '@angular/core';
import { Organization, Designation } from "../../shared/shared/models";
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { ActivatedRoute,Router } from "@angular/router";
import { Location } from '@angular/common';
import { AdminService } from "../admin.service";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";
@Component({
  selector: 'app-designation-create',
  templateUrl: './designation-create.component.html',
  styleUrls: ['./designation-create.component.scss']
})
export class DesignationCreateComponent implements OnInit {

  @Input()
  designation:Designation;
  status:string='';
  buttonValue:string='';
  desigForm: FormGroup;
  header:string='';

  constructor(private translation:TranslationService,private notificationServiceService:NotificationServiceService,private rp: ActivatedRoute,private route:Router, private location: Location, 
  private sessionService: SessionService,private adminService:AdminService,private httpService:HttpService) { 
        rp.params.subscribe(p =>{ 
      if(p && p.id){
          this.httpService.getData("/designation/"+p.id).subscribe(
          res=>{
            this.designation=res;
                this.desigForm = new FormGroup({
                designationName: new FormControl(res.designationName)
              });
            this.buttonValue='update';
            this.status='edit';
            this.translation.getAsyn('updateDesignation').subscribe(result=>{
             this.header= result + this.designation.designationName;
            }) 
          }
        )
      }else{
          
          this.status='new';
          this.buttonValue='Create';
          this.createNewForm();
          this.translation.getAsyn('createDesignation').subscribe(result=>{
            this.header=result;
          })
          this.designation=new Designation();
      }
    
    });
  }

    createNewForm() {
    this.desigForm = new FormGroup({
      designationName: new FormControl()
    });
  }

  ngOnInit() {
  }


  createUpdate() {
    if (this.status == 'new' && this.designation) {
      this.designation = new Designation();
      this.designation.tenantId = this.sessionService.shareObj['wall'];
      this.designation = Object.assign(this.designation, ...this.desigForm.value);
     this.adminService.createNewDesg(this.designation).subscribe(
       data=>{
         this.notificationServiceService.showSaveDailog("designation","create",true);
        // this.route.navigate(['/admin/designation'], { });
         this.adminService.showOrgDesignationButton=true;
        }
     )
    } else if (this.status == 'edit' && this.designation) {
      //its an update
      let obj = new Designation();
      obj.tenantId = this.sessionService.shareObj['wall'];
      obj.designationName = this.desigForm.value.designationName;
      this.designation = Object.assign(this.designation, ...this.desigForm.value);
      console.log("----edit save----");
      console.log(this.designation);
      this.adminService.updateDesignation(this.designation).subscribe(
        data => {
          this.adminService.fetchDesignationList();
          this.notificationServiceService.showSaveDailog("designation","update",true);
          //this.route.navigate(['/admin/designation'], { });
          this.adminService.showOrgDesignationButton=true;
        }
      );
    }


    console.log(this.designation);
  }

  cancel() {
    this.designation = null;
    this.desigForm = null;
    this.adminService.showOrgDesignationButton=true;
    this.route.navigate(['/admin/designation'], {});
  }
}
