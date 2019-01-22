import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { FormGroup, FormControl } from "@angular/forms";
import { LeaveType } from "../../shared/shared/models";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";

@Component({
  selector: 'app-leave-type-create',
  templateUrl: './leave-type-create.component.html',
  styleUrls: ['./leave-type-create.component.scss']
})
export class LeaveTypeCreateComponent implements OnInit {


  

  @Input()
  leaveType:LeaveType;
  status:string='';
  buttonValue:string='';
  leavetypeForm: FormGroup;
  header:string='';

  constructor(private translate:TranslationService,private notificationServiceService:NotificationServiceService,private rp: ActivatedRoute,private route:Router, 
  private sessionService: SessionService,private httpService:HttpService) { 
    this.translate.getAsyn("save").subscribe(
      result=> { this.buttonValue=result;}
    )
        rp.params.subscribe(p =>{ 
      if(p && p.id){
          this.notificationServiceService.showProgressBar();
          this.httpService.getData("/leavetype/"+p.id).subscribe(
          res=>{
            this.notificationServiceService.hideProgressBar();
            this.leaveType=res;
                this.leavetypeForm = new FormGroup({
                name: new FormControl(res.name),
                description: new FormControl(res.description),
                count: new FormControl(res.count),
                carryFarward: new FormControl(res.carryFarward),
                carryFarwardLimit: new FormControl(res.carryFarwardLimit),
                assingType: new FormControl(res.assingType),
                gender: new FormControl(res.gender)
              });
             this.translate.getAsyn("update").subscribe(
              result=> { this.buttonValue=result;}
            )
            this.status='edit';
             this.translate.getAsyn("updateLeaveType").subscribe(
      result=> { 
        this.header=result + this.leaveType.name;
      }
    )
            
          },error=>{
            this.notificationServiceService.hideProgressBar();
            this.notificationServiceService.showErrorDialog(error);
          }
        )
      }else{
          
          this.status='new';
          this.buttonValue='Create';
          this.createNewForm();
          this.header='Create New Leave Type'
          this.leaveType=new LeaveType();
      }
    
    });
  }

    createNewForm() {
      
      this.leavetypeForm = new FormGroup({
                name: new FormControl(),
                description: new FormControl(),
                count: new FormControl(),
                carryFarward: new FormControl(),
                carryFarwardLimit: new FormControl(),
                assingType: new FormControl(),
                gender: new FormControl()
      });
  }

  ngOnInit() {
  }
validateCreateForm(){
  if(this.leavetypeForm.value.carryFarwardLimit>this.leavetypeForm.value.count){
    alert("error");
    return false;
  }
  return true;
}
validateUpdateForm(){
  if(this.leavetypeForm.value.carryFarwardLimit>this.leavetypeForm.value.count){
    alert("error update");
    return false;
  }
  return true;
}

  createUpdate() {
    this.notificationServiceService.showProgressBar();
    if (this.status == 'new' && this.leaveType) {

      if(!this.validateCreateForm()){
        this.notificationServiceService.hideProgressBar();
        return;
      }
      this.leaveType = new LeaveType();
      this.leaveType.companyId = this.sessionService.shareObj['wall'];
      this.leaveType = Object.assign(this.leaveType, ...this.leavetypeForm.value);
     this.httpService.postData("/leavetype/",this.leaveType).subscribe(
       data=>{
         this.notificationServiceService.hideProgressBar();
         this.notificationServiceService.showSaveDailog("leavetype","create",true);
         //this.route.navigate(['/admin/leavetype'], { });
        },
        error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
     )
    } else if (this.status == 'edit' && this.leaveType) {
      //its an update
       if(!this.validateUpdateForm()){
        return;
      }
      let obj = new LeaveType();
      obj.companyId = this.sessionService.shareObj['wall'];
      obj.name = this.leavetypeForm.value.name;
      this.leaveType = Object.assign(this.leaveType, ...this.leavetypeForm.value);
      this.httpService.putData("/leavetype/",this.leaveType).subscribe(
        data => {
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showSaveDailog("leavetype","update",true);
        },
        error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      );
    }


    console.log(this.leaveType);
  }

  cancel() {
    this.leaveType = null;
    this.leaveType = null;
    this.route.navigate(['/admin/leavetype'], {});
  }


}
