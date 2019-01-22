import { Component, OnInit, Input } from '@angular/core';
import { Role } from "../../shared/shared/models";
import { FormGroup, FormControl } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { AdminService } from "../admin.service";
import { HttpService } from "../../shared/shared/httpService";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";

@Component({
  selector: 'app-role-create',
  templateUrl: './role-create.component.html',
  styleUrls: ['./role-create.component.scss']
})
export class RoleCreateComponent implements OnInit {

 

  @Input()
  role:Role;
  status:string='';
  buttonValue:string='';
  roleForm: FormGroup;
  header:string='';

  constructor(private translation:TranslationService,private notificationServiceService:NotificationServiceService,private rp: ActivatedRoute,private route:Router, 
  private sessionService: SessionService,private adminService:AdminService,private httpService:HttpService) { 
        rp.params.subscribe(p =>{ 
      if(p && p.id){
        this.notificationServiceService.showProgressBar("","");
          this.httpService.getData("/roles/"+p.id).subscribe(
          res=>{
            this.notificationServiceService.hideProgressBar();
            this.role=res;
                this.roleForm = new FormGroup({
                name: new FormControl(res.name),
                type: new FormControl(res.type)
              });
              this.translation.getAsyn("update").subscribe(result=>{
                this.buttonValue=result;
              })
            
            this.status='edit';
              this.translation.getAsyn("updateRole").subscribe(result=>{
                this.header=result + this.role.name;
              })
            
          },error=>
          {
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
           this.translation.getAsyn("createNewRole").subscribe(result=>{
                this.header=result;
              })
            
          this.role=new Role();
      }
    
    });
  }

    createNewForm() {
    this.roleForm = new FormGroup({
      name: new FormControl(),
      type:new FormControl()
    });
  }

  ngOnInit() {
  }


  createUpdate() {
    this.notificationServiceService.showProgressBar("","");
    if (this.status == 'new' && this.role) {
      this.role = new Role();
      this.role.tenantId = this.sessionService.shareObj['wall'];
      this.role = Object.assign(this.role, ...this.roleForm.value);
     this.httpService.postData("/roles/",this.role).subscribe(
       data=>{
         //this.route.navigate(['/admin/role'], { });
         this.notificationServiceService.hideProgressBar();
         this.notificationServiceService.showSaveDailog("role","create",true);
        },
        error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
     )
    } else if (this.status == 'edit' && this.role) {
      //its an update
      let obj = new Role();
      obj.tenantId = this.sessionService.shareObj['wall'];
      obj.name = this.roleForm.value.name;
      this.role = Object.assign(this.role, ...this.roleForm.value);
      console.log("----edit save----");
      console.log(this.role);
      this.httpService.putData("/roles/",this.role).subscribe(
        data => {
          //this.route.navigate(['/admin/role'], { });
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showSaveDailog("role","update",true);
        },
        error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      );
    }


    console.log(this.role);
  }

  cancel() {
    this.role = null;
    this.role = null;
    this.route.navigate(['/admin/role'], {});
  }


}
