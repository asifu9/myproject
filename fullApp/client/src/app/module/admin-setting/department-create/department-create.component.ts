import { Component, OnInit ,Input} from '@angular/core';
import { FormGroup, FormControl } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Department } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { AdminService } from "../admin.service";
import { HttpService } from "../../shared/shared/httpService";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";


@Component({
  selector: 'app-department-create',
  templateUrl: './department-create.component.html',
  styleUrls: ['./department-create.component.scss']
})
export class DepartmentCreateComponent implements OnInit {


  @Input()
  department:Department;
  status:string='';
  buttonValue:string='';
  departForm: FormGroup;
  header:string='';

  constructor(private translation:TranslationService,private notificationServiceService:NotificationServiceService,private rp: ActivatedRoute,private route:Router, 
  private sessionService: SessionService,private adminService:AdminService,private httpService:HttpService) { 

        rp.params.subscribe(p =>{ 
         
      if(p && p.id){
         this.notificationServiceService.showProgressBar();
          this.httpService.getData("/department/"+p.id).subscribe(
          res=>{
            this.notificationServiceService.hideProgressBar();
            this.department=res;
                this.departForm = new FormGroup({
                name: new FormControl(res.name)
              });
              this.translation.getAsyn("update").subscribe(result=>{
                this.buttonValue=result;
              })
            this.status='edit';
             this.translation.getAsyn("updateDepartment").subscribe(result=>{
                this.header=result + this.department.name;
              })
          },
          error=>{
            this.notificationServiceService.hideProgressBar();
            this.notificationServiceService.showErrorDialog(error);
          }
        )
      }else{
          
          this.status='new';
           this.translation.getAsyn("create").subscribe(result=>{
                this.buttonValue=result;
              })
          this.createNewForm();
           this.translation.getAsyn("createNewDepartment").subscribe(result=>{
                this.header=result;
              })
          
          this.department=new Department();
      }
    
    });
  }

    createNewForm() {
    this.departForm = new FormGroup({
      name: new FormControl()
    });
  }

  ngOnInit() {
  }


  createUpdate() {

    if (this.status == 'new' && this.department) {
      this.department = new Department();
      this.department.tenantId = this.sessionService.shareObj['wall'];
      this.department = Object.assign(this.department, ...this.departForm.value);
     this.adminService.createNewDepartment(this.department).subscribe(
       result=>{
         this.notificationServiceService.showSaveDailog("department","create",true);
        // this.route.navigate(['/admin/department'], { });
         this.adminService.showDepartmentButton=true;
        }
     )
    } else if (this.status == 'edit' && this.department) {
      //its an update
      let obj = new Department();
      obj.tenantId = this.sessionService.shareObj['wall'];
      obj.name = this.departForm.value.name;
      this.department = Object.assign(this.department, ...this.departForm.value);
     // console.log("----edit save----");
      console.log(this.department);
      this.adminService.updateDeparment(this.department).subscribe(
        data => {
          this.adminService.fetchDepartment();
          this.notificationServiceService.showSaveDailog("department","update",true);
         // this.route.navigate(['/admin/department'], { });
          this.adminService.showDepartmentButton=true;
        }
      );
    }


    console.log(this.department);
  }

  cancel() {
    this.department = null;
    this.departForm = null;
    this.adminService.showDepartmentButton=true;
    this.route.navigate(['/admin/department'], {});
  }

}
