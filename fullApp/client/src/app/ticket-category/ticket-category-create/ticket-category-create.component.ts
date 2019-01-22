import { Component, OnInit,Input } from '@angular/core';
import { FormGroup, FormControl } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { TicketCategory } from "../../module/shared/shared/models";
import { SessionService } from "../../module/shared/shared/SessionService";
import { HttpService } from "../../module/shared/shared/httpService";
import { NotificationServiceService } from "../../module/shared/shared/notification-service.service";
import { TranslationService } from "../../module/shared/shared/translate.service";

@Component({
  selector: 'app-ticket-category-create',
  templateUrl: './ticket-category-create.component.html',
  styleUrls: ['./ticket-category-create.component.scss']
})
export class TicketCategoryCreateComponent implements OnInit {

buttonValue:string='';
ticketCategoryForm: FormGroup;
status:string='new';
id:string;
@Input()
ticketCategory:TicketCategory;
  constructor(private translation:TranslationService,private notificationServiceService:NotificationServiceService,
  private sessionService:SessionService,public httpService:HttpService,private router:Router,
  private activatedRoute:ActivatedRoute) {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.createNewForm();
    if(this.id){
      this.updateTicketCategory(this.id);
    }else{
      this.createNewForm();
    }
    
   }

  ngOnInit() {
  }
  updateTicketCategory(id){
    this.notificationServiceService.showProgressBar();
    this.httpService.getData("/TicketCategory/"+id).subscribe(res=>{
      this.notificationServiceService.hideProgressBar();
       this.ticketCategory=res;
       this.createNewForm();
    },error=>{
      this.notificationServiceService.hideProgressBar();
      this.notificationServiceService.showErrorDialog(error);
    });
  }

      createNewForm() {
        if(this.ticketCategory){
            this.ticketCategoryForm = new FormGroup({
            name: new FormControl(this.ticketCategory.name)
          });
          this.translation.getAsyn("update").subscribe(result=>{
            this.buttonValue=result;
          })
          
          this.status='edit';
        }else{
            this.ticketCategoryForm = new FormGroup({
            name: new FormControl()
          });
          this.translation.getAsyn("save").subscribe(result=>{
            this.buttonValue=result;
          })
          this.status='new';
        }
  
  }


  createUpdate() {

    if (this.status == 'new' && !this.ticketCategory) {
      this.ticketCategory = new TicketCategory();
      this.notificationServiceService.showProgressBar();
      this.ticketCategory = Object.assign(this.ticketCategory, ...this.ticketCategoryForm.value);
      this.ticketCategory.tenantId = this.sessionService.shareObj['wall'];
      this.ticketCategory.createdBy = this.sessionService.shareObj['user'];
     this.httpService.postData("/TicketCategory/",this.ticketCategory).subscribe(result=>{
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showSaveDailog("ticketCategory","create",true);
     },error=>{
       this.notificationServiceService.hideProgressBar();
       this.notificationServiceService.showErrorDialog(error);
     })
    } else if (this.status == 'edit' && this.ticketCategory) {
      //its an update
      this.ticketCategory.tenantId = this.sessionService.shareObj['wall'];
      this.ticketCategory.createdBy = this.sessionService.shareObj['user'];
      this.ticketCategory.name = this.ticketCategoryForm.value.name;
      this.notificationServiceService.showProgressBar();
      this.httpService.putData("/TicketCategory/",this.ticketCategory).subscribe(
        data => {
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showSaveDailog("ticketCategory","update",true);
        },
        error=>{
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      );
    }


  }

    cancel() {
    this.router.navigate(['/admin/ticketCategory'], {});
  }

}
