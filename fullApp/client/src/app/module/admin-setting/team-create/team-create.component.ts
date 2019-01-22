import { Component, OnInit, Input, OnChanges, SimpleChanges, EventEmitter, Output } from '@angular/core';
import { Organization, Designation, Team } from "../../shared/shared/models";
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { ActivatedRoute,Router } from "@angular/router";
import { Location } from '@angular/common';
import { AdminService } from "../admin.service";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";

@Component({
  selector: 'app-team-create',
  templateUrl: './team-create.component.html',
  styleUrls: ['./team-create.component.scss']
})
export class TeamCreateComponent implements OnInit {


  @Input()
  team:Team;
  status:string='';
  buttonValue:string='';
  teamForm: FormGroup;
  header:string='';

  constructor(private translation:TranslationService,private notificationServiceService:NotificationServiceService,private rp: ActivatedRoute,private route:Router, private location: Location, 
  private sessionService: SessionService,private adminService:AdminService,private httpService:HttpService) { 
        rp.params.subscribe(p =>{ 
      if(p && p.id){
          this.httpService.getData("/team/"+p.id).subscribe(
          res=>{
            this.team=res;
                this.teamForm = new FormGroup({
                name: new FormControl(res.name)
              });
              this.translation.getAsyn('update').subscribe(result=>{
                this.buttonValue=result;
              })
            
            this.status='edit';
             this.translation.getAsyn('updateTeam').subscribe(result=>{
                  this.header=result+ this.team.name;
              })
        
          },
          error=>{
            this.notificationServiceService.hideProgressBar();
            this.notificationServiceService.showErrorDialog(error);
          }
        )
      }else{
          
          this.status='new';
          this.translation.getAsyn('create').subscribe(result=>{
                this.buttonValue=result;
              });
          this.createNewForm();
          this.translation.getAsyn('createNewTeam').subscribe(result=>{
                 this.header=result;
              });
         
          this.team=new Team();
      }
    
    });
  }

    createNewForm() {
    this.teamForm = new FormGroup({
      name: new FormControl()
    });
  }

  ngOnInit() {
  }


  createUpdate() {
    if (this.status == 'new' && this.team) {
      this.team = new Team();
      this.team.tenantId = this.sessionService.shareObj['wall'];
      this.team = Object.assign(this.team, ...this.teamForm.value);
     this.adminService.createNewTeam(this.team).subscribe(
       data=>{
         //this.route.navigate(['/admin/team'], { });
         this.notificationServiceService.showSaveDailog("team","create",true);
         this.adminService.showTeamButton=true;
        }
     )
    } else if (this.status == 'edit' && this.team) {
      //its an update
      let obj = new Team();
      obj.tenantId = this.sessionService.shareObj['wall'];
      obj.name = this.teamForm.value.name;
      this.team = Object.assign(this.team, ...this.teamForm.value);
      this.adminService.updateTeam(this.team).subscribe(
        data => {
          this.adminService.fetchTeam();
          this.notificationServiceService.showSaveDailog("team","update",true);
          this.adminService.showTeamButton=true;
        }
      );
    }


    console.log(this.team);
  }

  cancel() {
    this.team = null;
    this.teamForm = null;
    this.adminService.showTeamButton=true;
    this.route.navigate(['/admin/team'], {});
  }
}
